package com.jaspersoft.jasperserver.api.metadata.common.service.impl.hibernate;

import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * This interface is a hook for HibernateRepositoryService to attach a code to resource save / update / delete events.
 * It is used instead of a standard HibernateListener events.
 * The issues with the standard listener are:
 * 1) for some reason it includes unmodified objects into the scope, which significantly hits the performance on larger number of objects
 * 2) when the hook code needs to create objects (like in case of themes logic), it often causes cyclic dependencies
 *
 * User: Andrew Sokolnikov
 * Date: 10/28/12
 */
public interface HibernateSaveUpdateDeleteListener {
    public void beforeSave(Object entity, HibernateTemplate hibernateTemplate);
    public void afterSave(Object entity, HibernateTemplate hibernateTemplate);

    public void beforeUpdate(Object entity, HibernateTemplate hibernateTemplate);
    public void afterUpdate(Object entity, HibernateTemplate hibernateTemplate);

    public void beforeSaveOrUpdate(Object entity, HibernateTemplate hibernateTemplate);
    public void afterSaveOrUpdate(Object entity, HibernateTemplate hibernateTemplate);

    public void beforeDelete(Object entity, HibernateTemplate hibernateTemplate);
    public void afterDelete(Object entity, HibernateTemplate hibernateTemplate);
}