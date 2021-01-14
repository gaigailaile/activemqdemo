package com.gai.top;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopJmsReceiver {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, "tcp://127.0.0.1:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("my-topic");
        MessageConsumer consumer = session.createConsumer(topic);
        while (true) {
            TextMessage message = (TextMessage) consumer.receive();
            if (null != message) {
                System.out.println("收到消息：" + message.getText());
                session.commit();
            } else
                break;
        }
        session.close();
        connection.close();
    }
}
