package com.derbysoft.content.monitor.kafka;

import com.derbysoft.content.common.AbstractQueueMessageProcessor;
import com.derbysoft.content.monitor.kafka.processor.ScriptMessageProcessor;
import com.derbysoft.content.monitor.model.Topic;
import com.derbysoft.content.monitor.service.KafkaService;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Queues.newLinkedBlockingQueue;

/**
 * Created by zhangyang on 2018/1/3.
 */

public class KafkaMessageReceiver implements Runnable {
    private long pollInterval = 100;
    private Topic topic;
    private BlockingQueue<Map<TopicPartition, OffsetAndMetadata>> commitQueue = newLinkedBlockingQueue();
    private ConcurrentMap<TopicPartition, Map<AbstractQueueMessageProcessor, Thread>> messageProcessorTasks = Maps.newConcurrentMap();
    private KafkaService kafkaService;
    private Map<String, Object> consumerConfig;

    public static KafkaMessageReceiver of(Topic topic, KafkaService kafkaService, long pollInterval) throws IllegalArgumentException, JsonSyntaxException {
        checkArgument(!Objects.isNull(topic), "null object topic");
        checkArgument(StringUtils.isNotBlank(topic.getName()), "empty topic name");
        checkArgument(!Objects.isNull(kafkaService), "null object kafkaService");
        String configs = topic.getConfigs();
        Map configMap = new Gson().fromJson(configs, Map.class);
        KafkaMessageReceiver messageReceiver = new KafkaMessageReceiver();
        messageReceiver.setTopic(topic);
        messageReceiver.setKafkaService(kafkaService);
        messageReceiver.setPollInterval(pollInterval);
        messageReceiver.setConsumerConfig(configMap);
        return messageReceiver;
    }

    @Override
    public void run() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerConfig);
        consumer.subscribe(Collections.singletonList(topic.getName()));
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Map<TopicPartition, OffsetAndMetadata> metadataMap = commitQueue.poll();
                if (MapUtils.isNotEmpty(metadataMap)) {
                    consumer.commitAsync(metadataMap, null);
                }
                ConsumerRecords<String, String> records = consumer.poll(pollInterval);
                for (ConsumerRecord<String, String> record : records) {
                    String topic = record.topic();
                    int partition = record.partition();
                    TopicPartition topicPartition = new TopicPartition(topic, partition);
                    Map<AbstractQueueMessageProcessor, Thread> processorThreadMap = messageProcessorTasks.get(topicPartition);
                    if (MapUtils.isEmpty(processorThreadMap)) {
                        //TODO: reflect to establish processor
                        ScriptMessageProcessor processor = new ScriptMessageProcessor(commitQueue);
                        Thread thread = new Thread(processor);
                        thread.setName("Thread-for " + topicPartition.toString());
                        thread.start();
                        processorThreadMap = Collections.singletonMap(processor, thread);
                        messageProcessorTasks.put(topicPartition, processorThreadMap);
                    }
                    processorThreadMap.forEach((processor, thread) -> processor.addMessageToQueue(record));
                }
            }
        } catch (Exception e) {
            consumer.close();
        }
    }

    public Long getPollInterval() {
        return pollInterval;
    }


    public void setPollInterval(long pollInterval) {
        this.pollInterval = pollInterval;
    }

    public KafkaService getKafkaService() {
        return kafkaService;
    }

    public void setKafkaService(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Map<String, Object> getConsumerConfig() {
        return consumerConfig;
    }

    public void setConsumerConfig(Map<String, Object> consumerConfig) {
        this.consumerConfig = consumerConfig;
    }
}
