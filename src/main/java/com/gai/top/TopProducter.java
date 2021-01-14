package com.gai.top;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopProducter {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, "tcp://127.0.0.1:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("my-topic");
        MessageProducer producer = session.createProducer(topic);
        for (int i = 1; i <= 5; i++) {
            sendMsg(session, producer, i);
        }
        session.commit();
        connection.close();
    }
    /**
     * 在指定的会话上，通过指定的消息生产者发出一条消息
     *
     * @param session
     *            消息会话
     * @param producer
     *            消息生产者
     */
    public static void sendMsg(Session session, MessageProducer producer, int i) throws JMSException {
        // 创建一条文本消息
        TextMessage message = session.createTextMessage("my name is topic " + i);
        // 通过消息生产者发出消息
        producer.send(message);
    }
}
