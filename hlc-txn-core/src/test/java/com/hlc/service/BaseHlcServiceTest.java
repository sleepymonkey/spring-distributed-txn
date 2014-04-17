package com.hlc.service;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.support.GenericXmlApplicationContext;


public abstract class BaseHlcServiceTest {

    protected GenericXmlApplicationContext appCtx;

    protected final Logger log = LoggerFactory.getLogger(getClass());


    public BaseHlcServiceTest() {
        super();
    }


    /**
     * return a list of all spring context files needed for your concrete class test setup.
     *
     * please note the ordering of the string array is VERY important.  context files which come last in
     * the list returned will override beans with the same name defined in earlier listed context files.
     */
    public abstract String[] getSpringContextFiles();

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        log.info("inside {} test setup", getClass());

        appCtx =  new GenericXmlApplicationContext();
        appCtx.load(getSpringContextFiles());

        overrideTestSpecificSpringBeans();
        appCtx.refresh();

        // now let subclass set themselves up
        beforeTestSetup();
    }


    /**
     * override the bean factory with specific implementations if necessary
     */
    protected void overrideTestSpecificSpringBeans() {
        // no op
    }

    /**
     * helper method to simplify overriding a spring bean definition.
     *
     * @param beanName
     * @param klass
     */
    protected void overrideSpringBean(String beanName, Class<?> klass) {
        BeanDefinition def = appCtx.getBeanDefinition(beanName);
        def.setBeanClassName(klass.getName());

        appCtx.registerBeanDefinition(beanName, def);
    }

    /**
     * Setup your services, daos, etc prior to running a test.  This method will be
     * invoked as if you placed a @Before annotation
     */
    public void beforeTestSetup() throws Exception {
        // no op
    }


}