package com.hlc.service.impl;

import java.util.Properties;

/**
 * User: rsmith
 * Date: 3/7/14 4:24 PM
 */
public class SystemPropLoader {

    public void setSystemProperties(Properties props) {
        System.getProperties().putAll(props);
    }
}
