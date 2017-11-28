package com.jaspersoft.jasperserver.search.service;

/**
 * Created by stas on 4/23/14.
 */
public interface ItemProcessor<T, U> {
    U call(T t);
}
