package com.derbysoft.content.monitor.service.impl;

import com.derbysoft.content.monitor.dao.TopicDao;
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
    final TopicDao topicDao;

    @Autowired
    public KafkaServiceImpl(TopicDao topicDao) {
        this.topicDao = topicDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Topic topic(long id) {
        return topicDao.uniqueById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Topic> topics() {
        return topicDao.all();
    }

    @Override
    public long add(Topic topic) {
        return topicDao.add(topic);
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
