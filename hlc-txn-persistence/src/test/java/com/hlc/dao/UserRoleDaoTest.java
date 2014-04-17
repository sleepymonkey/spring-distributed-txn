package com.hlc.dao;


import com.hlc.entity.UserRoleEntity;
import com.hlc.setup.UserRoleEntityTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


/**
 * @author  rsmith
 * @created Feb 05, 2014
 */
@ContextConfiguration(locations = { "classpath:hlc-persistence-context.xml" })
public class UserRoleDaoTest extends AbstractJUnit4SpringContextTests {

    private ApplicationContext appCtx;
    private UserRoleDao dao;
    private JdbcObjectDao jdbcDao;
    private UserRoleEntityTestUtil util;
    
    private static final Logger log = LoggerFactory.getLogger(UserRoleDaoTest.class);
    
    
    @Before
    public void setup(){        
        appCtx = super.applicationContext;
        jdbcDao = (JdbcObjectDao) appCtx.getBean("jdbcObjDao");

        util = new UserRoleEntityTestUtil(appCtx);
        util.intializeDbTable();
    }

    
    //@Test  // disable the hibernate test for now
    public void testSaveAndRetrieve() {
        
        UserRoleEntity origEntity = util.generateDefaultEntity();
        UserRoleEntity savedUser = dao.saveUserRole(origEntity);
        
        UserRoleEntity dbEntity = dao.retrieveUserRoleEntityById(savedUser.getId());
        
        util.assertComparisons(origEntity, dbEntity);
    }

    @Test
    public void testSaveAndRetrieveJdbc() {
        String sql = "insert into user_role values (?, ?, ?, now(), now())";
        Object[] params = new Object[] {0, 1, "admin"}; //, new DateTime()};

        jdbcDao.insert(sql, params);

    }
}