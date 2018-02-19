package com.wiproevents.services.springdata;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.microsoft.azure.spring.data.documentdb.repository.DocumentDbRepository;
import com.wiproevents.entities.AuditableEntity;
import com.wiproevents.entities.AuditableUserEntity;
import com.wiproevents.entities.IdentifiableEntity;
import com.wiproevents.exceptions.AttendeeException;
import com.wiproevents.exceptions.ConfigurationException;
import com.wiproevents.exceptions.EntityNotFoundException;
import com.wiproevents.utils.Helper;
import com.wiproevents.utils.springdata.extensions.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

import static lombok.AccessLevel.PROTECTED;

/**
 * This is a base class for services that provides basic CRUD capabilities.
 *
 * @param <T> the identifiable entity
 * @param <S> the search criteria
 */
@NoArgsConstructor(access = PROTECTED)
public abstract class BaseService<T extends IdentifiableEntity, S> {
    /**
     * The default sort column by id.
     */
    public static final String ID = "id";

    /**
     * The specification executor. Should be non-null after injection.
     */
    @Autowired
    @Getter(value = PROTECTED)
    private DocumentDbSpecificationRepository<T, String> repository;

    @Autowired
    private ApplicationContext appContext;

    private PropertyUtilsBean beanUtils = new PropertyUtilsBean();


    private Map<String, DocumentDbSpecificationRepository> repositories = new HashMap<>();

    /**
     * Check if all required fields are initialized properly.
     *
     * @throws ConfigurationException if any required field is not initialized properly.
     */
    @PostConstruct
    protected void checkConfiguration() {
        Helper.checkConfigNotNull(repository, "repository");
        repositories = appContext.getBeansOfType(DocumentDbSpecificationRepository.class);
    }

    /**
     * This method is used to retrieve an entity.
     *
     * @param id the id of the entity to retrieve
     * @return the match entity
     * @throws IllegalArgumentException if id is not positive
     * @throws EntityNotFoundException if the entity does not exist
     * @throws AttendeeException if any other error occurred during operation
     */
    public T get(String id) throws AttendeeException {
        T entity = ensureEntityExist(id);
        handlePopulate(entity);
        return entity;
    }

    /**
     * This method is used to create an entity.
     *
     * @param entity the entity to create
     * @return the created entity
     * @throws IllegalArgumentException if entity is null or not valid
     * @throws AttendeeException if any other error occurred during operation
     */
    @Transactional
    public T create(T entity) throws AttendeeException {
        Helper.checkNull(entity, "entity");

        // assign id
        if (entity.getId() != null) {
            throw new IllegalArgumentException("You cannot assign the id on create.");
        }

        entity.setId(UUID.randomUUID().toString());

        handleNestedValidation(entity);

        handleNestedCreate(entity);

        T obj = createOrUpdateEntity(entity, null, repository);

        // retrieve with populations
        return this.get(obj.getId());
    }

    @SuppressWarnings(value = "unchecked")
    private <ST extends IdentifiableEntity> ST createOrUpdateEntity(ST entity, ST oldEntity,
                                                          DocumentDbSpecificationRepository<ST, String> repository)
            throws AttendeeException {

        Helper.checkNull(entity, "entity");

        boolean isNew = oldEntity == null;

        if (isNew && entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
        }

        if (repository == null) {
            repository = getRepositoryByClass(entity.getClass());
        }
        if (entity instanceof AuditableEntity) {
            AuditableEntity auditableEntity = (AuditableEntity) entity;
            Date now = new Date();
            if (isNew) {
                auditableEntity.setCreatedOn(new Date());
            } else {
                auditableEntity.setCreatedOn(((AuditableEntity) oldEntity).getCreatedOn());
            }
            auditableEntity.setUpdatedOn(now);
            if (entity instanceof AuditableUserEntity) {
                AuditableUserEntity auditableUserEntity = (AuditableUserEntity) entity;
                if (Helper.getAuthUser() != null) {
                    if (isNew) {
                        auditableUserEntity.setCreatedBy(Helper.getAuthUser().getId());
                    } else {
                        auditableUserEntity.setCreatedBy(((AuditableUserEntity) oldEntity).getCreatedBy());
                    }
                    auditableUserEntity.setUpdatedBy(Helper.getAuthUser().getId());
                }
            }
        }

        Class<?> clazz = entity.getClass();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Reference reverseReferenceAnnotation = field.getAnnotation(Reference.class);

                // handle cascade reference
                if (reverseReferenceAnnotation == null || !reverseReferenceAnnotation.cascade()) {
                    continue;
                }

                boolean originalAccess = field.isAccessible();
                field.setAccessible(true);
                Object value;
                Object oldValue = null;
                try {
                    value = field.get(entity);
                    if (oldEntity != null) {
                        oldValue = field.get(oldEntity);
                    }
                } catch (IllegalAccessException e) {
                    throw new AttendeeException("cannot get the field: "
                            + field.getName() + " from class: " + clazz.getName());
                }
                field.setAccessible(originalAccess);
                if (value == null) {
                    continue;
                }
                if (value instanceof List) {
                    Map<String, IdentifiableEntity> oldEntityMappings = new HashMap<>();
                    if (oldValue != null) {
                        for (IdentifiableEntity subEntity: (List<IdentifiableEntity>) oldValue) {
                            oldEntityMappings.put(subEntity.getId(), subEntity);
                        }
                    }
                    for (IdentifiableEntity subEntity: (List<IdentifiableEntity>) value) {
                        if (subEntity.getId() == null) {
                            String idPath = reverseReferenceAnnotation.assignIdTo();
                            if (!idPath.isEmpty()) {
                                try {
                                    beanUtils.setProperty(subEntity, idPath, entity.getId());
                                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                    throw new AttendeeException("Failed to assign id to path: " + idPath);
                                }
                            }
                            createOrUpdateEntity(subEntity, null, null);
                            continue;
                        }
                        IdentifiableEntity oldSubEntity = oldEntityMappings.get(subEntity.getId());
                        String idPath = reverseReferenceAnnotation.assignIdTo();
                        if (!idPath.isEmpty()) {
                            try {
                                beanUtils.setProperty(subEntity, idPath, entity.getId());
                            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                throw new AttendeeException("Failed to assign id to path: " + idPath);
                            }
                        }
                        createOrUpdateEntity(subEntity, oldSubEntity, null);
                    }
                } else {
                    String idPath = reverseReferenceAnnotation.assignIdTo();
                    if (!idPath.isEmpty()) {
                        try {
                            beanUtils.setProperty(value, idPath, entity.getId());
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                           throw new AttendeeException("Failed to assign id to path: " + idPath);
                        }
                    }
                    createOrUpdateEntity((IdentifiableEntity) value, (IdentifiableEntity) oldValue, null);
                }
            }

            clazz = clazz.getSuperclass();
        }

        // save the entity at last
        return repository.save(entity);
    }

    @SuppressWarnings("unchecked")
    private <ST extends IdentifiableEntity> DocumentDbSpecificationRepository<ST, String>
        getRepositoryByClass(Class<? extends IdentifiableEntity> clazz) throws AttendeeException {
        String name = clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1);
        DocumentDbSpecificationRepository repo = repositories.get(name + "Repository");
        if (repo == null) {
            throw new AttendeeException("Cannot find repo of type: " + name);
        }
        return repo;
    }


    /**
     * This method is used to delete an entity.
     *
     * @param id the id of the entity to delete
     * @throws IllegalArgumentException if id is not positive
     * @throws EntityNotFoundException if the entity does not exist
     * @throws AttendeeException if any other error occurred during operation
     */
    @Transactional
    public void delete(String id) throws AttendeeException {
        Helper.checkNullOrEmpty(id, "id");
        ensureEntityExist(id);
        repository.delete(id);
    }


    /**
     * This method is used to update an entity.
     *
     * @param id the id of the entity to update
     * @param entity the entity to update
     * @return the updated entity
     * @throws IllegalArgumentException if id is not positive or entity is null or id of entity is not positive
     * or id of  entity not match id or entity is invalid
     * @throws EntityNotFoundException if the entity does not exist
     * @throws AttendeeException if any other error occurred during operation
     */
    @Transactional
    public T update(String id, T entity) throws AttendeeException {
        T existing = checkUpdate(id, entity);
        handleNestedValidation(entity);
        handleNestedUpdate(entity, existing);

        createOrUpdateEntity(entity, existing, repository);

        // for population
        return get(entity.getId());
    }

    /**
     * Check id and entity for update method.
     *
     * @param id the id of the entity to update
     * @param entity the entity to update
     * @return the existing entity
     * @throws IllegalArgumentException if id is not positive or entity is null or id of entity is not positive
     * or id of  entity not match id
     * @throws EntityNotFoundException if the entity does not exist
     */
    protected T checkUpdate(String id, T entity) throws EntityNotFoundException {
        Helper.checkUpdate(id, entity);
        return ensureEntityExist(id);
    }


    /**
     * This method is used to search for entities by criteria and paging params.
     *
     * @param criteria the search criteria
     * @param paging the paging data
     * @return the search result
     * @throws IllegalArgumentException if pageSize is not positive or pageNumber is negative
     * @throws AttendeeException if any other error occurred during operation
     */
    public SearchResult<T> search(S criteria, Paging paging) throws AttendeeException {
        SearchResult<T> result = repository.findAll(getSpecification(criteria), paging, true);
        for (T item : result.getEntities()) {
            handlePopulate(item);
        }
        return result;
    }

    /**
     * This method is used to get total count for search results of entities with criteria.
     *
     * @param criteria the search criteria
     * @return the total count of search result
     * @throws IllegalArgumentException if pageSize is not positive or pageNumber is negative
     * @throws AttendeeException if any other error occurred during operation
     */
    public long count(S criteria) throws AttendeeException {
        return repository.countAll(getSpecification(criteria));
    }


    /**
     * This method is used to get the Specification<T>.
     *
     * @param criteria the criteria
     * @return the specification
     * @throws IllegalArgumentException if pageSize is not positive or pageNumber is negative
     * @throws AttendeeException if any other error occurred during operation
     */
    protected abstract DocumentDbSpecification<T> getSpecification(S criteria) throws AttendeeException;



    protected void handleNestedUpdate(T entity, T oldEntity) throws AttendeeException {

    }

    protected void handleNestedCreate(T entity) throws AttendeeException {

    }

    protected  void handlePopulate(T entity) throws AttendeeException {
        populateEntity(entity);
    }

    @SuppressWarnings("unchecked")
    private IdentifiableEntity populateEntity(IdentifiableEntity entity) throws AttendeeException {
        Class<?> clazz = entity.getClass();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Reference referenceAnnotation = field.getAnnotation(Reference.class);
                if (referenceAnnotation == null) {
                    continue;
                }

                Object value;
                try {
                    boolean original = field.isAccessible();
                    field.setAccessible(true);
                    value = field.get(entity);
                    field.setAccessible(original);
                } catch (IllegalAccessException e) {
                    throw new AttendeeException("failed to get the field: " + field.getName(), e);
                }

                if (value instanceof List) {
                    List<IdentifiableEntity> populatedList = new ArrayList<>();
                    for (IdentifiableEntity subEntity: (List<IdentifiableEntity>) value) {
                        // read the item from the repo
                        DocumentDbSpecificationRepository<IdentifiableEntity, String> theRepository
                                = getRepositoryByClass(subEntity.getClass());

                        IdentifiableEntity populatedSubEntity = theRepository.findOne(subEntity.getId(), true);
                        if (populatedSubEntity != null) {
                            populateEntity(populatedSubEntity);
                            populatedList.add(populatedSubEntity);
                        }
                    }
                    try {
                        boolean originalAccessible = field.isAccessible();
                        field.setAccessible(true);
                        field.set(entity, populatedList);
                        field.setAccessible(originalAccessible);
                    } catch (IllegalAccessException e) {
                        throw new AttendeeException("failed to set the field: " + field.getName(), e);
                    }
                } else {
                    IdentifiableEntity subEntity = (IdentifiableEntity) value;

                    // read the item from the repo
                    DocumentDbSpecificationRepository<IdentifiableEntity, String> theRepository
                            = getRepositoryByClass(subEntity.getClass());

                    IdentifiableEntity populatedSubEntity = theRepository.findOne(subEntity.getId(), true);
                    if (populatedSubEntity != null) {
                        populateEntity(populatedSubEntity);
                    }
                    try {
                        boolean originalAccessible = field.isAccessible();
                        field.setAccessible(true);
                        field.set(entity, populatedSubEntity);
                        field.setAccessible(originalAccessible);
                    } catch (IllegalAccessException e) {
                        throw new AttendeeException("failed to set the field: " + field.getName(), e);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return entity;
    }

    protected  void handleNestedValidation(T entity) throws AttendeeException {
        Map<String, DocumentDbRepository<?, String>> repositories = repository.getNestedRepositories();
        for (String path : repositories.keySet()) {
            DocumentDbRepository<?, String> pathRepository = repositories.get(path);
            // get value from the build
            try {
                Object value = Helper.getPropertyExt(beanUtils, entity, path);

                validateReference(path, value, pathRepository);

            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new AttendeeException("failed to read: " + path + " from class: " + entity.getClass());
            }
        }
    }

    private void validateReference(String path, Object value, DocumentDbRepository<?, String> pathRepository) throws AttendeeException {
        if (value == null) {
            return;
        }
        if (value instanceof List) {
            for (Object v : (List<?>) value) {
                validateReference(path, v, pathRepository);
            }
            return;
        }

        if (!(value instanceof IdentifiableEntity)) {
            throw new AttendeeException(
                    "The class: " + value.getClass() + " of path: " + path + " is not IdentifiableEntity");
        }

        IdentifiableEntity v = (IdentifiableEntity) value;
        String entityId = v.getId();
        Helper.checkNullOrEmpty(entityId, "The id in path: " + path);


        // check existence
        Object nestedEntity = pathRepository.findOne(entityId);
        if (nestedEntity == null) {
            throw new IllegalArgumentException(
                    "The id " + entityId + " in path: " + path + " cannot find entity.");
        }
    }

    @SuppressWarnings("unchecked")
    private void assignIds(IdentifiableEntity entity, boolean isRoot) {
        if (!isRoot && entity.getClass().getAnnotation(Document.class) != null) {
            // only assign ids for embedded type.
            return;
        }

        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
        }
        // check the sub fields
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (IdentifiableEntity.class.isAssignableFrom(field.getType())) {
                try {
                    assignIds((IdentifiableEntity) field.get(entity), false);
                } catch (IllegalAccessException e) {
                    // ignore
                    e.printStackTrace();
                }
            } else if (List.class.isAssignableFrom(field.getType())) {
                ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
                Class<?> actualType = (Class<?>) stringListType.getActualTypeArguments()[0];
                if (actualType.isAssignableFrom(IdentifiableEntity.class)) {
                    try {
                        List<IdentifiableEntity> list = (List<IdentifiableEntity>) field.get(entity);
                        list.forEach(item -> assignIds(item, false));
                    } catch (IllegalAccessException e) {
                        // ignore
                    }
                }
            }
        }
    }
    /**
     * Check whether an identifiable entity with a given id exists.
     *
     * @param id the id of entity
     * @return the match entity
     * @throws IllegalArgumentException if id is not positive
     * @throws EntityNotFoundException if the match entity can not be found in DB
     */
    private T ensureEntityExist(String id) throws EntityNotFoundException {
        Helper.checkNullOrEmpty(id, "id");
        T entity = repository.findOne(id, true);
        if (entity == null) {
            throw new EntityNotFoundException(String.format("Entity with ID=%s can not be found", id));
        }
        return entity;
    }
}

