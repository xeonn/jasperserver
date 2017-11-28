/*
* Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
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
* along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
*/
package com.jaspersoft.jasperserver.remote.connection.datadiscovery;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: DataSet.java 58920 2015-10-30 15:47:09Z ykovalch $
 */
public class DataSet<DataType, MetadataType> {
    private DataType data;
    private MetadataType metadata;

    public DataSet(){}

    public DataSet(DataSet<DataType, MetadataType> source){
        data = source.getData();
        metadata = source.getMetadata();
    }

    public DataType getData() {
        return data;
    }

    public DataSet<DataType, MetadataType> setData(DataType data) {
        this.data = data;
        return this;
    }

    public MetadataType getMetadata() {
        return metadata;
    }

    public DataSet<DataType, MetadataType> setMetadata(MetadataType metadata) {
        this.metadata = metadata;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataSet)) return false;

        DataSet dataSet = (DataSet) o;

        if (data != null ? !data.equals(dataSet.data) : dataSet.data != null) return false;
        if (metadata != null ? !metadata.equals(dataSet.metadata) : dataSet.metadata != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = data != null ? data.hashCode() : 0;
        result = 31 * result + (metadata != null ? metadata.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DataSet{" +
                "data=" + data +
                ", metadata=" + metadata +
                '}';
    }
}
