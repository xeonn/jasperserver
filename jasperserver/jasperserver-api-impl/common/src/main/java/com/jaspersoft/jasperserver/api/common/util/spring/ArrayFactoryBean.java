package com.jaspersoft.jasperserver.api.common.util.spring;

import org.springframework.beans.factory.config.AbstractFactoryBean;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;

/**
 * <p></p>
 *
 * @author Zakhar.Tomchenco
 * @version $Id$
 */
public class ArrayFactoryBean extends AbstractFactoryBean {
    private String typeClass = "java.lang.String";
    private List sourceList = Collections.EMPTY_LIST;
    private Object[] array = new String[0];

    @Override
    public Class<?> getObjectType() {
        return array.getClass();
    }

    @Override
    protected Object createInstance() throws Exception {
        return sourceList.toArray(array);
    }

    public String getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(String typeClass) {
        try {
            this.array = (Object[])Array.newInstance(Class.forName(typeClass), 0);
            this.typeClass = typeClass;
        } catch (Exception e) {
            throw new IllegalStateException("Cannot load class "+ typeClass);
        }
    }

    public List getSourceList() {
        return sourceList;
    }

    public void setSourceList(List sourceList) {
        this.sourceList = sourceList;
    }
}
