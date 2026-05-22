package com.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TodoEventConsumer {

    // 审计消费者：快速记录操作日志
    @KafkaListener(topics = "todo-events", groupId = "audit-group")
    public void onAuditEvent(ConsumerRecord<String, String> record) {
        System.out.printf("[审计组] 收到操作记录: %s%n", record.value());
        // 实际可写入数据库审计表
    }

    // 通知消费者：模拟发送通知（延迟3秒）
    @KafkaListener(topics = "todo-events", groupId = "notify-group")
    public void onNotifyEvent(ConsumerRecord<String, String> record) {
        System.out.printf("[通知组] 开始发送通知: %s%n", record.value());
        try {
            Thread.sleep(3000);   // 模拟耗时，例如推送、发短信
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.printf("[通知组] 通知发送完毕: %s%n", record.value());
    }
}