package com.hlc.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.hlc.dao.UserRoleDao;
import com.hlc.entity.UserRoleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author  rsmith
 * @created Feb 05, 2014
 */

public class UserRoleDaoImpl
    implements UserRoleDao
{
    private static final Logger log = LoggerFactory.getLogger(UserRoleDaoImpl.class);

    @PersistenceContext
    private EntityManager entityMgr;


    @Override
    @Transactional(readOnly = false)
    public UserRoleEntity saveUserRole(UserRoleEntity entity) {
        log.info("saving user role entity");
        return entityMgr.merge(entity);
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public UserRoleEntity retrieveUserRoleEntityById(Long id) {
    	log.info("start of retrieveUserRoleEntityById. id is: {}", id);

        Class klass = UserRoleEntity.class;
        Query query = entityMgr.createQuery("select ur from UserRoleEntity ur where ur.id = :id", klass);
        query.setParameter("id", id);
        
        return (UserRoleEntity) query.getSingleResult();
    }
    
    
    
}
