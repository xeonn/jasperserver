package com.jaspersoft.jasperserver.search.common;

import java.util.Collection;

/**
 * @author Paul Lysak
 * @version $Revision$
 */
public class ItemsExistException extends RuntimeException {

    private Collection<String> items;

    public ItemsExistException(Collection<String> items) {
        super();
        this.items = items;
    }

    public Collection<String> getItems() {
        return items;
    }
}
