package com.hlc.service.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.hlc.service.HlcMqListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: rsmith
 * Date: 2/10/14 5:43 PM
 */
public class HlcMqListenerImpl
    implements HlcMqListener, MessageListener
{

    private static final Logger log = LoggerFactory.getLogger(HlcMqListenerImpl.class);

    /**
     * Method implements JMS onMessage and acts as the entry
     * point for messages consumed by Springs DefaultMessageListenerContainer.
     * When DefaultMessageListenerContainer picks a message from the queue it
     * invokes this method with the message payload.
     */
    public void onMessage(Message message)
    {
        log.debug("Received message from queue [" + message + "]");
        /* The message must be of type TextMessage */
        if (message instanceof TextMessage)
        {
            try
            {
                String msgText = ((TextMessage) message).getText();
                log.debug("received message!!!  " + msgText);

                /* call message sender to put message onto second queue */
//                messageSender_i.sendMessage(msgText);

            }
            catch (JMSException jmsEx_p)
            {
                String errMsg = "An error occurred extracting message";
                log.error(errMsg, jmsEx_p);
            }
        }
        else
        {
            String errMsg = "Message is not of expected type TextMessage";
            log.error(errMsg);
            throw new RuntimeException(errMsg);
        }
    }

//    /**
//     * Sets the message sender.
//     *
//     * @param messageSender_p the new message sender
//     */
//    public void setTestMessageSender(TestMessageSender messageSender_p)
//    {
//        this.messageSender_i = messageSender_p;
//    }
//    66:  }


}
