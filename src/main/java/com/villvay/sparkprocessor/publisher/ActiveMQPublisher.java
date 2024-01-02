package com.villvay.sparkprocessor.publisher;

import com.villvay.sparkprocessor.util.PropertiesLoader;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.spark.sql.Dataset;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Properties;

public class ActiveMQPublisher {

    public static void publish(Dataset<String> jsonStrings){

        Properties properties = PropertiesLoader.loadProperties();
        String brokerUrl = properties.getProperty("pim.item.sink.url");
        String queueName = properties.getProperty("pim.item.sink.queue");

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

        jsonStrings.foreachPartition(partition -> {
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);

            MessageProducer producer = session.createProducer(destination);

            while (partition.hasNext()) {
                String jsonString = String.valueOf(partition.next());
                TextMessage message = session.createTextMessage(jsonString);
                producer.send(message);
            }

            producer.close();
            session.close();
            connection.close();
        });
    }
}
