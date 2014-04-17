package com.hlc.service.impl;

import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * User: rsmith
 * Date: 2/10/14 6:14 PM
 */

public class HlcMessageSender {

    private JmsTemplate jmsTemplate;
    private Queue testQueue;
    private static final Logger log = LoggerFactory.getLogger(HlcMessageSender.class);


    @Autowired
    public HlcMessageSender(JmsTemplate tpl, Queue queue) {
        this.jmsTemplate = tpl;
        this.testQueue = queue;
    }


    public void sendMessage(String message_p)
    {
        log.debug("About to put message on queue. Queue[" + testQueue.toString() + "] Message[" + message_p + "]");
        jmsTemplate.convertAndSend(testQueue, message_p);
    }

    // the worst way to receive msgs.  this will cause the thread to block until a msg is presented or timeout reached
    public String getMessage() {
        String ret = null;
        try {
            Object o = jmsTemplate.receiveAndConvert(testQueue.getQueueName());
            log.info("just received msg {} from jms queue", o);
            ret = o.toString();
        } catch (Exception e) {
            log.error("error retrieving msg from queue: {}", e.getMessage());
        }

        return ret;
    }

}
