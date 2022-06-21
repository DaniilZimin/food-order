package ru.zimins.foodorder.model.common;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractLongPersistable implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public AbstractLongPersistable() {
    }

    public Long getId() {
        return this.id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    @Transient
    public boolean isNew() {
        return null == this.getId();
    }
}