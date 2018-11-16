package com.mongoconnect;

import com.mongodb.client.FindIterable;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.bson.Document;

import java.util.Properties;

public class MongoKafkaProducer {

    private static KafkaProducer<String, String> producer;
    private final Properties properties = new Properties();

    public static void main(String[] args) {
        String mongoUrl = "";
        String mongoCollectionName = "";
        String mongoDatabaseName="";
        String kafkaBrokerList = "";
        String topicName = "";

        QueryMongo queryMongo = new QueryMongo(mongoUrl,mongoDatabaseName ,mongoCollectionName);
        new MongoKafkaProducer().sendMessageFromMongoTOKafka(queryMongo.findAllQuery(), kafkaBrokerList, topicName);
    }

    private void sendMessageFromMongoTOKafka(FindIterable<Document> data, String brokerList, String topicName) {
        properties.put("metadata.broker.list", brokerList);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("request.required.acks", "1");
        producer = new KafkaProducer<>(properties);
        for (Document document : data) {
            producer.send(new ProducerRecord<>(topicName, document.toJson()));
        }

        producer.flush();
    }
}
