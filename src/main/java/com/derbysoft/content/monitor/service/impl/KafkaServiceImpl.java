package com.derbysoft.content.monitor.service.impl;

import com.derbysoft.content.monitor.mapper.TopicMapper;
import com.derbysoft.content.monitor.model.Topic;
import com.derbysoft.content.monitor.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhangyang on 2018/1/6.
 */
@Transactional
@Service
public class KafkaServiceImpl implements KafkaService {
    private final TopicMapper topicMapper;

    @Autowired
    public KafkaServiceImpl(TopicMapper topicMapper) {
        this.topicMapper = topicMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Topic topic(long id) {
        return topicMapper.uniqueById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Topic> topics() {
        return topicMapper.all();
    }

    @Override
    public long add(Topic topic) {
        return topicMapper.add(topic);
    }

    @Override
    public void update(Topic topic) {

    }

    @Override
    public Topic delete(Topic topic) {
        return null;
    }

    @Override
    public Topic delete(long id) {
        return null;
    }
}
