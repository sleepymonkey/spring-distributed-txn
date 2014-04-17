package com.hlc.soap;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * User: rsmith
 * Date: 3/21/14 12:23 PM
 */
public class RpcSoapTxnClient {

    private static final Logger log = LoggerFactory.getLogger(RpcSoapTxnClient.class);
    protected GenericXmlApplicationContext appCtx;

    @Before
    public void setUp() throws Exception {
        appCtx =  new GenericXmlApplicationContext();
        appCtx.load("hlc-client-context.xml");
        appCtx.refresh();
    }


    @Test
    public void testBasicSoapCall() throws Exception {
        TestTxnSoapService client = appCtx.getBean("testClientLocal", TestTxnSoapService.class);

        com.arjuna.mw.wst11.UserTransaction ut = com.arjuna.mw.wst11.UserTransactionFactory.userTransaction();
        try
        {
            ut.begin();
            //ut.begin(30);

            long millis = System.currentTimeMillis();

            String resp = client.getHelloWorldAsString();

            long newMillis = System.currentTimeMillis();
            System.out.println("soap resp: " + resp + "\ntook " + (newMillis-millis) + " millis");
            ut.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            ut.rollback();
        }
    }

}


