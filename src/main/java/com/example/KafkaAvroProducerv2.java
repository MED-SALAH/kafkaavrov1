package com.example;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaAvroProducerv2 {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","35.180.127.210:9092");
        properties.setProperty("acks", "all");
        properties.setProperty("retries", "10");

        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        properties.setProperty("schema.registry.url", "http://35.180.127.210:8081");

        try (final KafkaProducer<String, EventHeader> producer = new KafkaProducer<>(properties)) {
            while (true) {
                EventHeader eventHeader = new EventHeader("000012",123456789000L,"Code Nomenclature de l'événement",1,2,"version","v1","serveur");
//                EventHeader eventHeader = EventHeader.newBuilder()
//                        .setEventId("000012")
//                        .setDateTimeRef(1345234020)
//                        .setNomenclatureEv("Code Nomenclature de l'événement")
//                        .setCanal(123)
//                        .setMedia(12)
//                        .setSchemaVersion("version")
//                        .setHeaderVersion("v1")
//                        .setServeur("serveur").build();

                final ProducerRecord<String, EventHeader> record = new ProducerRecord<>("test",eventHeader.getEventId(),eventHeader);
                producer.send(record);
                Thread.sleep(1000L);
            }
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }
}
