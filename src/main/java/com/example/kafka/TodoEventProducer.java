package com.example.kafka;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class TodoEventProducer {
    private final KafkaTemplate<String,String> kafkaTemplate;

    public TodoEventProducer(KafkaTemplate<String,String> kafkaTemplate){
            this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTodoEvent(String topic, String message){
            kafkaTemplate.send(topic,message);
            System.out.println(">>> kafka消息已发送"+message);
    }
}
