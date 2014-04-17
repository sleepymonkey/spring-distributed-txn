package com.hlc.service.impl;


import com.hlc.dao.JdbcObjectDao;
import com.hlc.entity.JdbcObject;
import com.hlc.service.HlcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author  
 * @created 
 */

public class HlcServiceImpl implements HlcService
{
    private static final Logger log = LoggerFactory.getLogger(HlcServiceImpl.class);

    private JdbcObjectDao jdbcObjectDao;
    private HlcMessageSender mqSender;


    public HlcServiceImpl() {}


    public void setJdbcObjectDao(JdbcObjectDao dao) {
        this.jdbcObjectDao = dao;
    }

    public void setMessageSender(HlcMessageSender sender) {
        this.mqSender = sender;
    }


    /**
     * manipulate data in the user_role table.  the lower level dao will raise a RuntimeException
     * if the userId has a value of 1013.  bit of a hack but this is just a proof of concept...
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void storeAndUpdateRecords(long userId, String roleName1, String roleName2) {
        String sql = "insert into user_role values (?, ?, ?, now(), now())";
        Object[] params = new Object[] {0, userId, roleName1};
//        jdbcObjectDao.insert(sql, params);

        sql = "select * from user_role where user_id = ?";
        params = new Object[] {userId};
//        JdbcObject obj = jdbcObjectDao.retrieve(sql, params);
//        log.info("object returned from service retrieve: {}", obj);

        sql = "update user_role set role_name = ? where user_id = ?";
        params = new Object[] {roleName2, userId};
//        jdbcObjectDao.update(sql, params);
    }


    /**
     * send a JMS message and potentially insert/update data in user_role db.
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void dbAndMsgTransaction(long userId, String jmsMessage) {
//        mqSender.sendMessage(jmsMessage);

        storeAndUpdateRecords(userId, "test role", "another role");
    }


    /**
     * always successfully inserts data into 'some_other_role' db.
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void doSuccessfulSave() {
        log.info("inside do successful save method...");
        String sql = "insert into some_other_role values (?, ?, ?, now(), now())";
        Object[] params = new Object[] {0, 2308, "from success save"};
//        jdbcObjectDao.insert(sql, params);
    }
}
