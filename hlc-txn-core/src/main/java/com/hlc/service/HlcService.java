package com.hlc.service;


/**
 * @author  
 * @created 
 */

public interface HlcService {

    void storeAndUpdateRecords(long userId, String firstRole, String secondRole);

    void dbAndMsgTransaction(long userId, String jmsMessage);

    void doSuccessfulSave();

}
