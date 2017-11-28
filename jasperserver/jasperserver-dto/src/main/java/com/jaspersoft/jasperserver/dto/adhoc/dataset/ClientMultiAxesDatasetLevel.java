package com.jaspersoft.jasperserver.dto.adhoc.dataset;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vasyl Spachynskyi
 * @version $Id: ClientMultiAxesDatasetLevel.java 64626 2016-09-26 13:25:24Z vzavadsk $
 * @since 08.04.2016
 */
public abstract class ClientMultiAxesDatasetLevel {

    private List<String> members = new ArrayList<String>();

    public ClientMultiAxesDatasetLevel() {
    }

    public ClientMultiAxesDatasetLevel(ClientMultiAxesDatasetLevel clientMultiAxesDatasetLevel) {
        this.members = new ArrayList<String>(clientMultiAxesDatasetLevel.getMembers());
    }

    @XmlElementWrapper(name = "members")
    @XmlElement(name = "member")
    public List<String> getMembers() {
        return members;
    }

    public ClientMultiAxesDatasetLevel setMembers(List<String> members) {
        this.members = members;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientMultiAxesDatasetLevel that = (ClientMultiAxesDatasetLevel) o;

        return !(members != null ? !members.equals(that.members) : that.members != null);
    }

    @Override
    public int hashCode() {
        return members != null ? members.hashCode() : 0;
    }

}
