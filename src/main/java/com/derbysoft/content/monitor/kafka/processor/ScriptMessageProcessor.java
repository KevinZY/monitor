package com.derbysoft.content.monitor.kafka.processor;

import com.derbysoft.content.common.AbstractQueueMessageProcessor;
import com.google.common.math.IntMath;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangyang on 2018/1/4.
 */

public class ScriptMessageProcessor extends AbstractQueueMessageProcessor<ConsumerRecord<String, String>> {
    private BlockingQueue<Map<TopicPartition, OffsetAndMetadata>> commitQueue;
    private LocalDateTime lastCommitTime = LocalDateTime.now();
    private int commitLength = 20;
    private Duration commitDuration = Duration.ofSeconds(2);
    private int completeTask = 0;
    private ConsumerRecord<String, String> lastUncommitRecord;

    public ScriptMessageProcessor(BlockingQueue<Map<TopicPartition, OffsetAndMetadata>> commitQueue) {
        this.commitQueue = commitQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ConsumerRecord<String, String> record = this.messageQueue.poll(100, TimeUnit.MILLISECONDS);
                process(record);
                commitToQueue();
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void process(ConsumerRecord<String, String> message) {
        if (Objects.isNull(message)) return;
        this.completeTask++;
        lastUncommitRecord = message;
        System.out.println(message.value());
    }

    private void commitToQueue() throws InterruptedException {
        if (Objects.isNull(lastUncommitRecord)) return;
        if (Objects.isNull(commitQueue)) return;
        boolean isOverTask = IntMath.mod(this.completeTask, commitLength) == 0;
        LocalDateTime now = LocalDateTime.now();
        boolean isOverTime = now.isAfter(lastCommitTime.plus(commitDuration));
        boolean needCommit = isOverTask || isOverTime;
        if (needCommit) {
            lastCommitTime = now;
            String topic = lastUncommitRecord.topic();
            long offset = lastUncommitRecord.offset();
            int partition = lastUncommitRecord.partition();
            Map<TopicPartition, OffsetAndMetadata> metadataMap = Collections.singletonMap(
                    new TopicPartition(topic, partition),
                    new OffsetAndMetadata(offset + 1L)
            );
            commitQueue.put(metadataMap);
        }
    }

    public BlockingQueue<Map<TopicPartition, OffsetAndMetadata>> getCommitQueue() {
        return commitQueue;
    }

    public void setCommitQueue(BlockingQueue<Map<TopicPartition, OffsetAndMetadata>> commitQueue) {
        this.commitQueue = commitQueue;
    }
}
