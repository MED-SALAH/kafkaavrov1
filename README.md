Commande line Producer kafka Avro :
kafka-avro-console-producer --broker-list 35.180.127.210:9092 --topic test2 --property value.schema='{"type":"record","name":"myrecord","fields":[{"name":"f1","type":"string"}]}'

Message to Send :
{"f1": "value1"}
{"f1": "value2"}
{"f1": "value3"}

Commande line Consummer kafka Avro :

kafka-avro-console-consumer --topic test2 --bootstrap-server 35.180.127.210:2181 --from-beginning