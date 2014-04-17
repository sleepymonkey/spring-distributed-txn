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
public class NonRpcSoapTxnClient {

    private static final Logger log = LoggerFactory.getLogger(NonRpcSoapTxnClient.class);
    protected GenericXmlApplicationContext appCtx;

    @Before
    public void setUp() throws Exception {
        appCtx =  new GenericXmlApplicationContext();
        appCtx.load("hlc-persistence-context.xml", "hlc-core-context.xml", "hlc-webapp-context.xml");
        appCtx.refresh();
    }



    @Test
    public void testBasicCall() throws Exception {
        TxnInitiatorService client = appCtx.getBean("initiatorClient", TxnInitiatorService.class);

        long millis = System.currentTimeMillis();
        client.localTxnCall();
        log.info("total duration: {}", (System.currentTimeMillis() - millis));
    }


}


