package com.wiproevents.utils.springdata.extensions;

import com.microsoft.azure.spring.data.documentdb.core.DocumentDbOperations;
import com.microsoft.azure.spring.data.documentdb.core.query.Query;
import com.microsoft.azure.spring.data.documentdb.repository.DocumentDbRepository;
import com.microsoft.azure.spring.data.documentdb.repository.support.DocumentDbEntityInformation;
import com.microsoft.azure.spring.data.documentdb.repository.support.SimpleDocumentDbRepository;
import com.wiproevents.entities.IdentifiableEntity;
import com.wiproevents.utils.Helper;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by wangjinggang on 2018/2/5.
 */
public class DocumentDbSpecificationRepositoryImpl<T, ID extends Serializable> extends SimpleDocumentDbRepository<T, ID> implements DocumentDbSpecificationRepository<T, ID> {

    /**
     * The logger with package fullName.
     */
    public static final Logger LOGGER = LogManager.getLogger("com.wiproevents");

    private final ExtDocumentDbOperations documentDbOperations;
    private final DocumentDbEntityInformation<T, ID> entityInformation;

    private PropertyUtilsBean beanUtils = new PropertyUtilsBean();
    private Map<String, DocumentDbRepository<?, ID>> nestedRepositories = new HashMap<>();

    public DocumentDbSpecificationRepositoryImpl(DocumentDbEntityInformation<T, ID> metadata,
                                                 ApplicationContext applicationContext) {
        super(metadata, applicationContext);
        this.documentDbOperations = (ExtDocumentDbOperations) applicationContext.getBean(DocumentDbOperations.class);
        this.entityInformation = metadata;
    }


    @Override
    public SearchResult<T> findAll(DocumentDbSpecification<T> spec, Paging paging) {
        Query q = new Query();
        return documentDbOperations.find(spec.toQuery(q), entityInformation.getJavaType(),
                entityInformation.getCollectionName(), paging);
    }

    @Override
    public SearchResult<T> findAll(DocumentDbSpecification<T> spec, Paging paging, Boolean withPopulatedFields) {
        SearchResult<T> result = this.findAll(spec, paging);
        if (withPopulatedFields) {
            result.getEntities().forEach(this::populateReference);
        }
        return result;
    }

    @Override
    public T findOne(ID id, Boolean withPopulatedFields) {
        T entity = super.findOne(id);
        if (withPopulatedFields && entity != null) {
            populateReference(entity);
        }
        return entity;
    }



    private void populateReference(T entity) {
        for (String path : this.nestedRepositories.keySet()) {
            DocumentDbRepository<?, ID> pathRepository = this.nestedRepositories.get(path);
            // get value from the build
            try {
                Object value = Helper.getPropertyExt(beanUtils, entity, path);

                Object populatedValue = populateReference(path, value, pathRepository);

                setPropertyExt(entity, path, populatedValue);

            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new IllegalStateException("failed to read: " + path + " from class: " + entity.getClass());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void setPropertyExt(Object entity, String path, Object value) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (path.startsWith(".")) {
            path = path.substring(1);
        }
        int t = path.indexOf("[*]");
        if (t >= 0) {
            if (value != null && !(value instanceof List)) {
                throw new IllegalStateException("should be a list");
            }
            List<Object> vList = (List<Object>) value;
            String pre = path.substring(0, t);
            String suf = path.substring(t + "[*]".length());
            List<Object> list = (List<Object>) beanUtils.getProperty(entity, pre);
            if (list != null) {
                if (list.size() != vList.size()) {
                    throw new IllegalStateException("The list size is not the same");
                }
                for (int i = 0; i < list.size(); i++) {
                    setPropertyExt(list.get(i), suf, vList.get(i));
                }
            }
        } else {
            if (value != null && !beanUtils.getPropertyType(entity, path).isAssignableFrom(value.getClass())) {
                Object dest = beanUtils.getProperty(entity, path);
                beanUtils.copyProperties(dest, value);
            } else {
                beanUtils.setProperty(entity, path, value);
            }

        }
    }

    @SuppressWarnings("unchecked")
    private Object populateReference(String path, Object value, DocumentDbRepository<?, ID> pathRepository) {
        if (value == null) {
            return null;
        }
        if (value instanceof List) {
            List<Object> result = new ArrayList<>();
            for (Object v : (List<?>) value) {
                result.add(populateReference(path, v, pathRepository));
            }
            return result;
        }

        if (!(value instanceof IdentifiableEntity)) {
            throw new IllegalStateException(
                    "The class: " + value.getClass() + " of path: " + path + " is not IdentifiableEntity");
        }

        IdentifiableEntity v = (IdentifiableEntity) value;
        ID entityId = (ID) v.getId();

        if (entityId == null) {
            throw new IllegalStateException(
                    "The class: " + value.getClass() + " of path:  " + path + " id cannot be null.");
        }

        Object nestedEntity;

        if (pathRepository instanceof  DocumentDbSpecificationRepository) {
            DocumentDbSpecificationRepository<?, ID> specificationRepository =
                    (DocumentDbSpecificationRepository<?, ID>) pathRepository;

            nestedEntity = specificationRepository.findOne((ID) entityId, true);
        } else {
            nestedEntity = pathRepository.findOne(entityId);
        }
        if (nestedEntity == null) {
            Exception e = new IllegalStateException(
                    "The id " + entityId + " in path: " + path + " cannot find entity.");
            Helper.logException(LOGGER, this.getClass().getName() + "#populateReference", e);
        }

        return nestedEntity;
    }

    @Override
    public long countAll(DocumentDbSpecification<T> spec) {
        Query q = new Query();
        return documentDbOperations.count(spec.toQuery(q), entityInformation.getJavaType(),
                entityInformation.getCollectionName());
    }


    @Override
    public void addNestedRepository(String path, DocumentDbRepository<?, ID> repository) {
        this.nestedRepositories.put(path, repository);
    }

    @Override
    public Map<String, DocumentDbRepository<?, ID>> getNestedRepositories() {
        return this.nestedRepositories;
    }
}
