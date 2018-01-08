package com.derbysoft.content.monitor.dao.mapper;

import com.derbysoft.content.monitor.model.Topic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zhangyang on 2018/1/6.
 */
@Component
public interface TopicMapper {

    @Select("SELECT * FROM topic WHERE id = #{id}")
    Topic uniqueById(@Param("id") long id);

    @Select("SELECT * FROM topic")
    List<Topic> selectAll();

    @Insert("INSERT INTO topic(name, active, processor, configs, pollInterval) VALUES (#{topic.name}, #{topic.active}, #{topic.processor}, #{topic.configs}, #{topic.pollInterval})")
    @Options(useGeneratedKeys = true, keyProperty = "topic.id")
    void insert(@Param("topic") Topic topic);
}
