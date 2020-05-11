package com.example;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaAvroProducerv2 {
    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "35.180.127.210:9092");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, 0);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        properties.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://35.180.127.210:8081");

        try (final KafkaProducer<String, EventHeader> producer = new KafkaProducer<>(properties)) {
            for (int i = 0; i < 1000; i++){
                EventHeader eventHeader = new EventHeader(Integer.toString(i),123456789000L,"Code Nomenclature de l'événement",1,2,"version","v1","serveur");
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
                System.out.println(record);
                Thread.sleep(100L);
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
