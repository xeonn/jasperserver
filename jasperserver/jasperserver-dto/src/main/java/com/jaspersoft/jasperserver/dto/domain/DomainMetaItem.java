package com.jaspersoft.jasperserver.dto.domain;

/**
 * @author Paul Lysak
 */
public class DomainMetaItem extends AbstractDomainMetaEntity {
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof DomainMetaItem)) {
            return false;
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public String toString() {
        return "DomainMetaItem{" +
                "id='" + getId() + '\'' +
                ", label='" + getLabel() + '\'' +
                ", properties=" + getProperties() +
                '}';
    }
}
