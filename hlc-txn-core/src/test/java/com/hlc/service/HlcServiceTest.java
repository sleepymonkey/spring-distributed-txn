package com.hlc.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.hlc.dao.JdbcObjectDao;
import com.hlc.dao.impl.JdbcObjectDaoImpl;
import com.hlc.entity.JdbcObject;
import com.hlc.service.impl.HlcMessageSender;
import com.hlc.setup.UserRoleEntityTestUtil;
import org.junit.Test;

/**
 * User: rsmith
 * Date: 2/7/14 3:47 PM
 */
public class HlcServiceTest
    extends BaseHlcServiceTest
{
    private JdbcObjectDao dao;
    private HlcService service;
    private HlcMessageSender jmsClient;


    @Override
    public String[] getSpringContextFiles() {
        return new String[] {"hlc-persistence-context.xml", "hlc-core-context.xml"};
    }

    @Override
    public void beforeTestSetup() {
        dao = super.appCtx.getBean("jdbcObjDao", JdbcObjectDao.class);
        service = super.appCtx.getBean("hlcService", HlcService.class);
        jmsClient = super.appCtx.getBean(HlcMessageSender.class);

        UserRoleEntityTestUtil util = new UserRoleEntityTestUtil(appCtx);
        util.intializeDbTable();
    }

    @Override
    protected void overrideTestSpecificSpringBeans() {
        super.overrideSpringBean("jdbcObjDao", ExceptionDao.class);  // generate exception when user id is 3
    }


    @Test
    public void testSuccessfulCommit() {
        service.storeAndUpdateRecords(1, "admin", "engineer");
        JdbcObject obj = dao.retrieve("select * from user_role where user_id = ?", new Object[] {1});
        assertEquals("engineer", obj.getAttribute("role_name"));
    }

    @Test
    public void testSuccessfulCommit2() {
        service.doSuccessfulSave();
        JdbcObject obj = dao.retrieve("select * from some_other_role where user_id = ?", new Object[] {2308});
        assertEquals("from success save", obj.getAttribute("role_name"));
    }

    @Test
    public void testSuccessfulRollback() {
        try {
            service.storeAndUpdateRecords(3, "admin", "engineer");
            fail("exception should have been thrown");
        } catch (Exception e) { /* no op */ }

        JdbcObject obj = dao.retrieve("select * from user_role where user_id = ?", new Object[] {3});
        assertEquals(null, obj);
    }

    @Test
    public void testDbAndJmsTxn() {
        int testUserId = 2;
        String testMsg = "you know it";
        service.dbAndMsgTransaction(testUserId, testMsg);

        JdbcObject obj = dao.retrieve("select * from user_role where user_id = ?", new Object[] {testUserId});
        assertEquals(testUserId, obj.getAttribute("user_id"));

        String msg = jmsClient.getMessage();
        assertEquals(testMsg, msg);
    }

    @Test
    public void testDbAndJmsTxnRollback() {
        int testUserId = 3;  // mock override will raise exception with user id 3
        String testMsg = "not quite";

        try {
            service.dbAndMsgTransaction(testUserId, testMsg);
        } catch (Exception e) {}

        JdbcObject obj = dao.retrieve("select * from user_role where user_id = ?", new Object[] {testUserId});
        assertEquals(null, obj);

        String msg = jmsClient.getMessage();  // jms template configured to timeout after 1 second...
        assertEquals(null, msg);
    }


    // dummy class to generate an exception when user id is 3
    private static class ExceptionDao extends JdbcObjectDaoImpl
    {
        public void update(String sql, Object[] params) {
            for (Object param : params) {
                if (param instanceof Number) {
                    long val = (long) param;
                    if (val == 3) {
                        throw new RuntimeException("test exception");
                    }
                }
            }

            // otherwise, call the regular dao update method
            super.update(sql, params);
        }
    }
}
