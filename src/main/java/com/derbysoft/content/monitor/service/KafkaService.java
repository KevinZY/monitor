package com.derbysoft.content.monitor.service;

import com.derbysoft.content.monitor.model.Topic;

import java.util.List;

/**
 * Created by zhangyang on 2018/1/6.
 */
public interface KafkaService {
    Topic topic(long id);

    List<Topic> topics();

    long add(Topic topic);

    void update(Topic topic);

    Topic delete(Topic topic);

    Topic delete(long id);
}
