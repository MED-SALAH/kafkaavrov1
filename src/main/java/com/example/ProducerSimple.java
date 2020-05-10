package com.example;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerSimple {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "35.180.127.210:9092");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, 0);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://35.180.127.210:8081");
//        properties.setProperty("bootstrap.servers","35.180.127.210:9092");
//        properties.setProperty("acks", "all");
//        properties.setProperty("retries", "10");
//
//        properties.setProperty("key.serializer", StringSerializer.class.getName());
//        properties.setProperty("value.serializer", StringSerializer.class.getName());
//        properties.setProperty("schema.registry.url", "http://35.180.127.210:8081");

        String message = "bonjour salah bigapps";
        String topic = "test2";

        try (final KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties)) {
            for (int i = 0; i < 100; i++) {
                ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic,Integer.toString(i), message);
                producer.send(producerRecord);
                System.out.println(producerRecord.partition());
                Thread.sleep(1000L);
            }
            producer.flush();
            System.out.println("Sucessssss ");
        } catch (final SerializationException e) {
            e.printStackTrace();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }
}
