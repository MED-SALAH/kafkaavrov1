package com.example;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaAvroProducerv1 {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","35.180.127.210:9092");
        properties.setProperty("acks", "all");
        properties.setProperty("retries", "10");

        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        properties.setProperty("schema.registry.url", "http://35.180.127.210:8081");

        KafkaProducer<String, EventHeader> kafkaProducer = new KafkaProducer<String, EventHeader>(properties);
        String topic = "test";

        EventHeader eventHeader = EventHeader.newBuilder()
                .setEventId("000012")
                .setHeaderVersion("v1")
                .setDateTimeRef(123456789000L)
                .setNomenclatureEv("Code Nomenclature de l'événement")
                .setSchemaVersion("version")
                .setServeur("serveur")
                .setCanal(123)
                .setMedia(12).build();

        ProducerRecord<String, EventHeader> producerRecord = new ProducerRecord<String, EventHeader>(
                topic, eventHeader
        );
        System.out.println(producerRecord);
        kafkaProducer.send(producerRecord);
//        kafkaProducer.send(producerRecord, new Callback() {
//            @Override
//            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
//                if (e == null){
//                    System.out.println(recordMetadata);
//                } else {
//                    e.printStackTrace();
//                }
//
//            }
//        });

        kafkaProducer.flush();
        kafkaProducer.close();


    }
}
