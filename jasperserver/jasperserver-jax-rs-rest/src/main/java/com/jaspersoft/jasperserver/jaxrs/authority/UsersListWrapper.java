package com.jaspersoft.jasperserver.jaxrs.authority;

import com.jaspersoft.jasperserver.api.metadata.user.domain.User;
import com.jaspersoft.jasperserver.api.metadata.user.domain.client.UserImpl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Zakhar.Tomchenco
 */
@XmlRootElement(name = "users")
public class UsersListWrapper {
    private List<UserImpl> usersList;

    public UsersListWrapper(){}

    public UsersListWrapper(List<User> users){
        usersList = new ArrayList<UserImpl>(users.size());
        for (User r : users){
            usersList.add((UserImpl)r);
        }
    }

    @XmlElement(name = "user")
    public List<UserImpl> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<UserImpl> usersList) {
        this.usersList = usersList;
    }
}
