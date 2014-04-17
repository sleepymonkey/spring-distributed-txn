package com.hlc.dao;

import com.hlc.entity.JdbcObject;

/**
 * User: rsmith
 * Date: 2/7/14 1:28 PM
 */
public interface JdbcObjectDao {

    public void insert(String sql, Object[] params);

    public void update(String sql, Object[] params);

    public JdbcObject retrieve(String sql, Object[] params);
}
