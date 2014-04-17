package com.hlc.soap;

import java.io.File;

import javax.jws.WebService;

import com.hlc.service.HlcService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * User: rsmith
 * Date: 3/17/14 1:47 PM
 */

@WebService(endpointInterface = "com.hlc.soap.TestTxnSoapService")
public class TestTxnSoapServiceImpl implements TestTxnSoapService, ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(TestTxnSoapServiceImpl.class);

    private ApplicationContext applicationContext;
    private HlcService testService;


    @Autowired
    public void setHlcService(HlcService service) {
        this.testService = service;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public String getHelloWorldAsString() {
        System.out.println("inside web service entry point...");

        TestTxnSoapService client = applicationContext.getBean("testClientRemote", TestTxnSoapService.class);
        client.makeWsCallForSuccessfulSave();

        int testUserId = getTestUseridValue();
        System.out.println("now testing txn service with user id " + testUserId);

        this.testService.dbAndMsgTransaction(testUserId, "test message");

        return "hello world";
    }

    @Override
    public String makeWsCallForSuccessfulSave() {
        System.out.println("calling service first with hardcoded user id 2308");
        this.testService.doSuccessfulSave();

        return "success";
    }

//    @Override
//    public String localTxnCall() {
//        TestTxnSoapService client = applicationContext.getBean("testClientLocal", TestTxnSoapService.class);
//
//        com.arjuna.mw.wst11.UserTransaction ut = com.arjuna.mw.wst11.UserTransactionFactory.userTransaction();
//        try
//        {
//            ut.begin();
//
//            client.getHelloWorldAsString();
//
//            ut.commit();
//            return "from local txn call";
//        }
//        catch(Exception e)
//        {
//            log.error("error running through txn operations: ", e);
//            try {ut.rollback();}catch (Exception ex) {log.error("error attempting rollback: ", ex);}
//            throw new RuntimeException("error from local txn call");
//        }
//
//    }

    private int getTestUseridValue() {
        int testId = 5;
        try {
            String contents = FileUtils.readFileToString(new File("/tmp/test-value"));
            testId = NumberUtils.toInt(contents, testId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return testId;
    }


}


//    public static TestTxnSoapService newInstance() throws Exception {
//        URL wsdlLocation = new URL("http://localhost:8080/test/SecondServiceATService/SecondServiceAT?wsdl");
//        //URL wsdlLocation = new URL("http://localhost:8180/wsat-jta-multi_hop/SecondServiceATService/SecondServiceAT?wsdl");
//        QName serviceName = new QName("http://www.jboss.org/narayana/quickstarts/wsat/simple/second", "SecondServiceATService");
//        QName portName = new QName("http://www.jboss.org/narayana/quickstarts/wsat/simple/second", "SecondServiceAT");
//
//        Service service = Service.create(wsdlLocation, serviceName);
//        TestTxnSoapService client = service.getPort(portName, TestTxnSoapService.class);
//
//        /*
//* Add client handler chain so that XTS can add the transaction context to the SOAP messages.
//*
//* This will be automatically added by the TXFramework in the future.
//*/
//        BindingProvider bindingProvider = (BindingProvider) client;
//        List<Handler> handlers = new ArrayList<Handler>();
//        handlers.add(new JaxWSTxOutboundBridgeHandler());
//        handlers.add(new JaxWSHeaderContextProcessor());
//        bindingProvider.getBinding().setHandlerChain(handlers);
//
//        return client;
//    }

