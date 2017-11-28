package com.jaspersoft.jasperserver.remote.services;

import com.jaspersoft.jasperserver.api.metadata.user.domain.Role;
import com.jaspersoft.jasperserver.api.metadata.user.domain.User;
import com.jaspersoft.jasperserver.remote.common.RoleSearchCriteria;
import com.jaspersoft.jasperserver.remote.common.UserSearchCriteria;
import com.jaspersoft.jasperserver.remote.exception.RemoteException;
import com.jaspersoft.jasperserver.ws.authority.WSRole;
import com.jaspersoft.jasperserver.ws.authority.WSRoleSearchCriteria;
import com.jaspersoft.jasperserver.ws.authority.WSUser;
import org.apache.axis.AxisFault;

import java.util.List;

/**
 * @author Volodya Sabadosh (vsabadosh@jaspersoft.com)
 * @version $Id $
 */
public interface UserAndRoleService {

    public List<User> findUsers(UserSearchCriteria criteria) throws RemoteException;

    public User putUser(User user) throws RemoteException;

    public void deleteUser(User user) throws RemoteException;

    public List<Role> findRoles(RoleSearchCriteria criteria) throws RemoteException;

    public Role putRole(Role role) throws RemoteException;

    public Role updateRoleName(Role oldRole, String newName) throws RemoteException;

    public void deleteRole(Role role) throws RemoteException;

}
