package com.hlc.setup;

import static org.junit.Assert.assertEquals;

import javax.sql.DataSource;

import com.hlc.entity.UserRoleEntity;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;


/**
 * @author rsmith
 * @created Feb 05, 2014
 */

public class UserRoleEntityTestUtil {

    private ApplicationContext appCtx;

    public static final long TEST_USER_ID = 2308;
    public static final String TEST_ROLE_NAME = "test-1415937919";
    public static final DateTime TEST_CREATED_DATE = new DateTime().withMillisOfSecond(0);
    public static final DateTime TEST_LAST_MODIFIED_DATE = new DateTime().withMillisOfSecond(0);

    private static final Logger log = LoggerFactory.getLogger(UserRoleEntityTestUtil.class);


    public UserRoleEntityTestUtil(ApplicationContext appCtx) {
        this.appCtx = appCtx;
    }


    public void intializeDbTable() {
        JdbcTemplate jdbcTmpl = new JdbcTemplate((DataSource) appCtx.getBean("dataSource"));
        JdbcTestUtils.deleteFromTables(jdbcTmpl, new String[]{"user_role", "some_other_role"});
    }


    public UserRoleEntity generateDefaultEntity() {
        UserRoleEntity entity = new UserRoleEntity();

        entity.setUserId(TEST_USER_ID);
        entity.setRoleName(TEST_ROLE_NAME);
        entity.setCreatedDate(TEST_CREATED_DATE);
        entity.setLastModifiedDate(TEST_LAST_MODIFIED_DATE);

        return entity;
    }

    public void assertComparisons(UserRoleEntity origEntity, UserRoleEntity dbEntity) {
        assertEquals("test userId", origEntity.getUserId(), dbEntity.getUserId());
        assertEquals("test roleName", origEntity.getRoleName(), dbEntity.getRoleName());
        assertEquals("test createdDate", origEntity.getCreatedDate(), dbEntity.getCreatedDate());
        assertEquals("test lastModifiedDate", origEntity.getLastModifiedDate(), dbEntity.getLastModifiedDate());

    }

    public void assertComparisons(UserRoleEntity dbEntity) {
        assertEquals("test userId", TEST_USER_ID, dbEntity.getUserId());
        assertEquals("test roleName", TEST_ROLE_NAME, dbEntity.getRoleName());
        assertEquals("test createdDate", TEST_CREATED_DATE, dbEntity.getCreatedDate());
        assertEquals("test lastModifiedDate", TEST_LAST_MODIFIED_DATE, dbEntity.getLastModifiedDate());

    }


}