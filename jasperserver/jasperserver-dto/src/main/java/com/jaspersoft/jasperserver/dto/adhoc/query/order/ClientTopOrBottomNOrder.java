/*
 * Copyright (C) 2005 - 2016 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.jasperserver.dto.adhoc.query.order;

import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * @author Andriy Godovanets
 * @version $Id: ClientTopOrBottomNOrder.java 63464 2016-06-02 07:28:24Z agodovan $
 */
public class ClientTopOrBottomNOrder extends ClientPathOrder {
    /**
     * Order limit
     */
    private Integer limit;
    private boolean createOtherBucket = false;
    private boolean limitAllLevels = false;

    public ClientTopOrBottomNOrder() {
    }

    public ClientTopOrBottomNOrder(ClientTopOrBottomNOrder sorting) {
        super(sorting);

        setLimit(sorting.getLimit());
        setCreateOtherBucket(sorting.getCreateOtherBucket());
        setLimitAllLevels(sorting.getLimitAllLevels());
    }

    /**
     *
     * @return Boolean value that specify if additional bucket (with name "other") should be created
     * for aggregation applied for all values that were filtered out by this ordering filter
     */
    public boolean getCreateOtherBucket() {
        return createOtherBucket;
    }

    public ClientTopOrBottomNOrder setCreateOtherBucket(boolean createOtherBucket) {
        this.createOtherBucket = createOtherBucket;
        return this;
    }

    /**
     *
     * @return Number of Top/Bottom items to display
     */
    public Integer getLimit() {
        return limit;
    }

    public ClientTopOrBottomNOrder setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    /**
     *
     * @return Control whether we should limit number of member in each level along with measures
     */
    public boolean getLimitAllLevels() {
        return limitAllLevels;
    }

    public ClientTopOrBottomNOrder setLimitAllLevels(boolean limitAllLevels) {
        this.limitAllLevels = limitAllLevels;
        return this;
    }

    @Override
    public ClientTopOrBottomNOrder setPath(List<String> path) {
        return (ClientTopOrBottomNOrder) super.setPath(path);
    }

    @Override
    public ClientTopOrBottomNOrder setAscending(Boolean isAscending) {
        return (ClientTopOrBottomNOrder) super.setAscending(isAscending);
    }

    public static class ClientTopNOrder extends ClientTopOrBottomNOrder {
        public ClientTopNOrder() {
            setAscending(false);
        }

        @XmlTransient
        @Override
        public Boolean isAscending() {
            return super.isAscending();
        }
    }

    public static class ClientBottomNOrder extends ClientTopOrBottomNOrder {
        public ClientBottomNOrder() {
            setAscending(true);
        }

        @XmlTransient
        @Override
        public Boolean isAscending() {
            return super.isAscending();
        }
    }
}
