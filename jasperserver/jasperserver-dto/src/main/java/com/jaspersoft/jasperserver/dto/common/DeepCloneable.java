package com.jaspersoft.jasperserver.dto.common;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id: DeepCloneable.java 62954 2016-05-01 09:49:23Z ykovalch $
 * @see
 */
public interface DeepCloneable<T> {
    T deepClone();
}
