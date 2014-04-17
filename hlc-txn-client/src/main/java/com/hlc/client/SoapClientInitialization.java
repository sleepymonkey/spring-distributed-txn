package com.hlc.client;

import java.util.Properties;

import org.jboss.jbossts.XTSService;
import org.jboss.jbossts.xts.environment.XTSPropertiesFactory;

/**
 * User: rsmith
 * Date: 4/14/14 10:03 PM
 */
public class SoapClientInitialization {

    public SoapClientInitialization(Properties props) throws Exception {
        XTSPropertiesFactory.setDefaultProperties(props);
        new XTSService().start();
    }
}
