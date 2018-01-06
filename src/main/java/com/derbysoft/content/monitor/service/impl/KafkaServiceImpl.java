package com.derbysoft.content.monitor.service.impl;

import com.derbysoft.content.monitor.dao.TopicDao;
import com.derbysoft.content.monitor.model.Topic;
import com.derbysoft.content.monitor.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangyang on 2018/1/6.
 */
@Service
public class KafkaServiceImpl implements KafkaService{
    @Autowired
    TopicDao topicDao;

    @Override
    public Topic topic(long id) {
        return topicDao.uniqueById(id);
    }

    @Override
    public List<Topic> topics() {
        return topicDao.all();
    }
}
