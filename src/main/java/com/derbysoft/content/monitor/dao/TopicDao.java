package com.derbysoft.content.monitor.dao;

import com.derbysoft.content.monitor.model.Topic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by zhangyang on 2018/1/6.
 */
public interface TopicDao {

    @Select("SELECT * FROM topic WHERE id=#{id}")
    Topic uniqueById(@Param("id") long id);

    @Select("SELECT * FROM topic")
    List<Topic> all();

    long add(@Param("topic") Topic topic);

    void update(Topic topic);

    Topic delete(Topic topic);

    Topic delete(long id);
}
