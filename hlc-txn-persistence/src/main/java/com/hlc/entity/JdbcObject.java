package com.hlc.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * User: rsmith
 * Date: 2/7/14 1:31 PM
 */
public class JdbcObject {

    private Map<String, Object> rowAttributes = new HashMap<String, Object>();


    public JdbcObject() {}


    public void setAttribute(String name, Object val) {
        this.rowAttributes.put(name, val);
    }

    public void setRowAttributes(Map<String, Object> mapping) {
        rowAttributes = mapping;
    }

    public Object getAttribute(String name) {
        return this.rowAttributes.get(name);
    }

}
