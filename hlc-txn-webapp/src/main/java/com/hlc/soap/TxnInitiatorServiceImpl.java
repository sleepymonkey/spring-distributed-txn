package com.hlc.soap;

import java.io.File;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

//import com.sun.xml.ws.api.tx.at.Transactional.TransactionFlowType;


/**
 * User: rsmith
 * Date: 3/17/14 1:47 PM
 */

@WebService(endpointInterface = "com.hlc.soap.TxnInitiatorService")
public class TxnInitiatorServiceImpl implements TxnInitiatorService, ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(TxnInitiatorServiceImpl.class);

    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }



    public String localTxnCall() {
        TestTxnSoapService client = applicationContext.getBean("testClientLocal", TestTxnSoapService.class);

        com.arjuna.mw.wst11.UserTransaction ut = com.arjuna.mw.wst11.UserTransactionFactory.userTransaction();
        try
        {
            ut.begin();

            client.getHelloWorldAsString();

            ut.commit();
            return "from local txn call";
        }
        catch(Exception e)
        {
            System.out.println("error running through txn operations: " + e);
            try {ut.rollback();}catch (Exception ex) {log.error("error attempting rollback: ", ex);}

            return "error from local txn call";
        }
    }


}
