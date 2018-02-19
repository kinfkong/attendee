package com.wiproevents.services.springdata;

import com.wiproevents.entities.AuditableEntity;
import com.wiproevents.entities.AuditableUserEntity;
import com.wiproevents.entities.IdentifiableEntity;
import com.wiproevents.exceptions.AttendeeException;
import com.wiproevents.services.AnnotationHandlerInterface;
import com.wiproevents.utils.Helper;
import com.wiproevents.utils.springdata.extensions.*;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by wangjinggang on 2018/2/19.
 */
@Service
public class AnnotationHandler implements AnnotationHandlerInterface {

    @Autowired
    private ApplicationContext appContext;

    private Map<String, DocumentDbSpecificationRepository> repositories = new HashMap<>();

    private PropertyUtilsBean beanUtils = new PropertyUtilsBean();

    @PostConstruct
    public void postConstruct() {
        repositories = appContext.getBeansOfType(DocumentDbSpecificationRepository.class);
    }

    @Override
    public IdentifiableEntity populate(IdentifiableEntity entity) throws AttendeeException {
        return (IdentifiableEntity) populate(null, entity);
    }

    @SuppressWarnings("unchecked")
    private Object populate(Annotation annotation, Object entity) throws AttendeeException {
        if (entity == null) {
            return null;
        }

        if (entity instanceof List) {
            List<Object> result = new ArrayList<>();
            for (Object item : (List<Object>) entity) {
                Object populated = populate(annotation, item);
                if (populated != null) {
                    result.add(populated);
                }
            }
            return result;
        }

        if (annotation instanceof Reference) {
            // read it from repo
            IdentifiableEntity idEntity = (IdentifiableEntity) entity;
            DocumentDbSpecificationRepository<IdentifiableEntity, String> repository
                    = getRepositoryByClass(idEntity.getClass());

            entity = repository.findOne(idEntity.getId());
        }

        // entity not found (ignore it)
        if (entity == null) {
            return null;
        }

        // populate the nested entities
        Class<?> clazz = entity.getClass();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field: fields) {
                Reference referenceAnnotation = field.getAnnotation(Reference.class);
                Embed embedAnnotation = field.getAnnotation(Embed.class);
                if (referenceAnnotation == null && embedAnnotation == null) {
                    continue;
                }
                Annotation subAnnotation = referenceAnnotation;
                if (embedAnnotation != null) {
                    subAnnotation = embedAnnotation;
                }

                Object value;
                try {
                    boolean originalAccessible = field.isAccessible();
                    field.setAccessible(true);
                    value = field.get(entity);

                    // populate the field
                    Object subEntity = populate(subAnnotation, value);
                    field.set(entity, subEntity);

                    field.setAccessible(originalAccessible);
                } catch (IllegalAccessException e) {
                    throw new AttendeeException("cannot get the field: "
                            + field.getName() + " from class: " + clazz.getName());
                }
            }
            clazz = clazz.getSuperclass();
        }

        return entity;
    }

    @SuppressWarnings("unchecked")
    private Object upsert(Annotation annotation, Object entity, Object oldEntity) throws AttendeeException {
        if (entity == null) {
            return null;
        }
        if (entity instanceof List) {
            List<Object> result = new ArrayList<>();
            Map<String, Object> oldItemMappings = new HashMap<>();
            if (oldEntity != null) {
                for (Object subEntity: (List) oldEntity) {
                    if (subEntity instanceof IdentifiableEntity) {
                        oldItemMappings.put(((IdentifiableEntity) subEntity).getId(), subEntity);
                    }

                }
            }
            for (Object item : (List) entity) {
                if (item instanceof IdentifiableEntity && ((IdentifiableEntity) item).getId() != null) {
                    result.add(upsert(annotation, item, oldItemMappings.get(((IdentifiableEntity) item).getId())));
                } else {
                    result.add(upsert(annotation, item, null));
                }
            }

            return result;
        }

        boolean isRoot = annotation == null;
        boolean shouldSave = isRoot;
        if (!shouldSave) {
            if (annotation instanceof Reference) {
                if (((Reference) annotation).cascade()) {
                    shouldSave = true;
                }
            }
        }

        if (shouldSave) {
            IdentifiableEntity idEntity = (IdentifiableEntity) entity;
            DocumentDbSpecificationRepository<IdentifiableEntity, String> repository
                    = getRepositoryByClass(idEntity.getClass());
            if (oldEntity != null && ((IdentifiableEntity) oldEntity).getId() == null) {
                oldEntity = null;
            }
            if (oldEntity == null && idEntity.getId() != null) {
                oldEntity = repository.findOne(idEntity.getId());
                if (oldEntity == null) {
                    throw new IllegalArgumentException("Entity not found of id: "
                            + idEntity.getId() + " of type: " + idEntity.getClass());
                }
            }
            boolean isNew = oldEntity == null;
            if (isNew && idEntity.getId() == null) {
                idEntity.setId(UUID.randomUUID().toString());
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
        } else if (annotation instanceof Reference) {
            // only reference, not cascade, verity the id
            IdentifiableEntity idEntity = (IdentifiableEntity) entity;
            if (idEntity.getId() == null) {
                throw new IllegalArgumentException("id is required for the type: " + idEntity.getClass());
            }
            DocumentDbSpecificationRepository<IdentifiableEntity, String> repository
                    = getRepositoryByClass(idEntity.getClass());
            if (repository.findOne(idEntity.getId()) == null) {
                throw new IllegalArgumentException("Entity not found of id: "
                        + idEntity.getId() + " of type: " + idEntity.getClass());
            }
        }


        // upsert the nested entities
        Class<?> clazz = entity.getClass();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field: fields) {
                Reference referenceAnnotation = field.getAnnotation(Reference.class);
                Embed embedAnnotation = field.getAnnotation(Embed.class);
                AssignId assignIdAnnotation = field.getAnnotation(AssignId.class);
                ForeignKey foreignKeyAnnotation = field.getAnnotation(ForeignKey.class);

                if (referenceAnnotation == null && embedAnnotation == null && foreignKeyAnnotation == null) {
                    continue;
                }

                Annotation subAnnotation = referenceAnnotation;
                if (embedAnnotation != null) {
                    subAnnotation = embedAnnotation;
                }

                Object value;
                Object oldValue = null;
                try {
                    boolean originalAccessible = field.isAccessible();
                    field.setAccessible(true);
                    value = field.get(entity);
                    if (oldEntity != null) {
                        oldValue = field.get(oldEntity);
                    }

                    // assign the ids
                    if (assignIdAnnotation != null) {
                        if (!(entity instanceof IdentifiableEntity)) {
                            throw new AttendeeException("cannot find id in class: " + entity.getClass());
                        }

                        String id;
                        if (assignIdAnnotation.from().isEmpty()) {
                            id = ((IdentifiableEntity) entity).getId();
                        } else {
                            try {
                                id = (String) beanUtils.getProperty(entity, assignIdAnnotation.from());
                            } catch (InvocationTargetException | NoSuchMethodException e) {
                                throw new AttendeeException("cannot get the id", e);
                            }
                        }
                        assignId(assignIdAnnotation, value, id);
                    }

                    if (foreignKeyAnnotation != null) {
                        if (value != null && value instanceof String) {
                            // validate the foreign key
                            DocumentDbSpecificationRepository<IdentifiableEntity, String> foreignRepository
                                    = getRepositoryByClass(foreignKeyAnnotation.clazz());
                            if (foreignRepository.findOne((String) value) == null) {
                                throw new IllegalArgumentException(
                                        "The " + field.getName() + " of " + value + " does not exists.");
                            }
                        }
                    }
                    if (subAnnotation != null) {
                        // embed or reference
                        Object subEntity = upsert(subAnnotation, value, oldValue);
                        field.set(entity, subEntity);
                    }

                    field.setAccessible(originalAccessible);
                } catch (IllegalAccessException e) {
                    throw new AttendeeException("cannot get the field: "
                            + field.getName() + " from class: " + clazz.getName());
                }
            }
            clazz = clazz.getSuperclass();
        }

        if (shouldSave) {
            IdentifiableEntity idEntity = (IdentifiableEntity) entity;
            DocumentDbSpecificationRepository<IdentifiableEntity, String> repository
                    = getRepositoryByClass(idEntity.getClass());
            entity = repository.save(idEntity);
        }


        return entity;
    }

    @Override
    public IdentifiableEntity upsert(IdentifiableEntity entity, IdentifiableEntity oldEntity)
            throws AttendeeException {
       Helper.checkNull(entity, "entity");
       return (IdentifiableEntity) upsert(null, entity, oldEntity);
    }

    @SuppressWarnings("unchecked")
    private void assignId(AssignId annotation, Object value, String idToAssign) throws AttendeeException {
        if (value == null) {
            return;
        }

        if (value instanceof List) {
            for (Object subEntity: (List<Object>) value) {
                assignId(annotation, subEntity, idToAssign);
            }

            return;
        }

        String idPath = annotation.to();
        if (!idPath.isEmpty()) {
            try {
                beanUtils.setProperty(value, idPath, idToAssign);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new AttendeeException("Failed to assign id to path: " + idPath);
            }
        }

    }


    @SuppressWarnings("unchecked")
    private DocumentDbSpecificationRepository<IdentifiableEntity, String> getRepositoryByClass(
            Class<? extends IdentifiableEntity> clazz) throws AttendeeException {

        String name = clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1);

        DocumentDbSpecificationRepository repo = repositories.get(name + "Repository");

        if (repo == null) {
            repo = repositories.get(clazz.getSimpleName() + "Repository");
            if (repo == null) {
                throw new AttendeeException("Cannot find repo of type: " + name);
            }

        }

        return repo;
    }
}
