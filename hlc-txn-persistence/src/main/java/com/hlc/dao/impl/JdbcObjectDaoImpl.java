package com.hlc.dao.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.hlc.dao.JdbcObjectDao;
import com.hlc.entity.JdbcObject;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: rsmith
 * Date: 2/7/14 1:40 PM
 */
public class JdbcObjectDaoImpl
    implements JdbcObjectDao
{
    private DataSource dataSource;


    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
    }


    @Override
    @Transactional
    public void insert(String sql, Object[] params) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, params);
    }

    @Override
    @Transactional
    public void update(String sql, Object[] params) {
        //if (true) throw new RuntimeException("here!");
        for (Object param : params) {
            int val = NumberUtils.toInt(param.toString(), -3);
            if (val == 1013) {
                throw new RuntimeException("test exception for initiating rollback");
            }
        }

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, params);
    }

    @Override
    @Transactional
    public JdbcObject retrieve(String sql, Object[] params) {
        Map<String, Object> row = null;

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        try {
            row = jdbcTemplate.queryForMap(sql, params);
        } catch (Exception e) {
            return null;
        }

        JdbcObject retObj = new JdbcObject();
        retObj.setRowAttributes(row);

        return retObj;
    }
}
