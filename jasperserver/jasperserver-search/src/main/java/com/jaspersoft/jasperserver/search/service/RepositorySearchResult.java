package com.jaspersoft.jasperserver.search.service;

import org.apache.commons.collections.Transformer;

import java.util.Collections;
import java.util.List;

/**
 * Created by stas on 4/22/14.
 */
public interface RepositorySearchResult<T> extends Cloneable {

    boolean isFull();

    int size();

    int getClientLimit();

    int getTotalCount();

    int getClientOffset();

    int getNextOffset();

    int getNextLimit();

    List<T> getItems();

    <U> RepositorySearchResult<U> transform(ItemProcessor<T, U> transformer);
}
