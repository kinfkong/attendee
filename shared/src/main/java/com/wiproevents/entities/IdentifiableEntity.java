package com.wiproevents.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * Base identifiable entity.
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class IdentifiableEntity implements Model {
    /**
     * The id.
     */
    @Id
    private String id;

    /**
     * Override the equals method.
     *
     * @param target the target
     * @return true if two entities have the same ID
     */
    @Override
    public boolean equals(Object target) {
        if (target instanceof IdentifiableEntity) {
            IdentifiableEntity entity = (IdentifiableEntity) target;
            if (entity.getId() == null) {
                return ((IdentifiableEntity) target).getId() == null;
            }
            return entity.getId().equals(this.id);
        }
        return false;
    }

    /**
     * Override the hashCode method.
     *
     * @return the hash code of the entity
     */
    @Override
    public int hashCode() {
        if (id == null) {
            return 0;
        }
        return id.hashCode();
    }
}

