package com.hlc.dao;

import com.hlc.entity.UserRoleEntity;


/**
 * @author  rsmith
 * @created Feb 05, 2014
 */

public interface UserRoleDao {

    UserRoleEntity saveUserRole(UserRoleEntity entity);

    UserRoleEntity retrieveUserRoleEntityById(Long id);

}
