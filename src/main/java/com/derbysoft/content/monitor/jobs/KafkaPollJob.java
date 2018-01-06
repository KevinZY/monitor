package com.derbysoft.content.monitor.jobs;

import com.derbysoft.content.monitor.kafka.KafkaMessageReceiver;
import com.derbysoft.content.monitor.model.Topic;
import com.derbysoft.content.monitor.service.KafkaService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.google.common.collect.Maps.newHashMap;

/**
 * Created by zhangyang on 2018/1/3.
 */

@Component
public class KafkaPollJob {
    private Map<String, KafkaMessageReceiver> messageReceiverTopics = newHashMap();

    private ExecutorService executorService;

    private final KafkaService kafkaService;

    @Autowired
    public KafkaPollJob(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @PostConstruct
    public void onStart() {
        List<Topic> topics = kafkaService.topics();
        if (CollectionUtils.isEmpty(topics)) return;
        executorService = Executors.newFixedThreadPool(topics.size());
        topics.forEach(topic -> {
            KafkaMessageReceiver messageReceiver = KafkaMessageReceiver.of(topic, kafkaService, 100);
            executorService.execute(messageReceiver);
            messageReceiverTopics.put(topic.getName(), messageReceiver);
        });
    }

    @PreDestroy
    public void onDestroy() {
        executorService.shutdown();
    }
}
