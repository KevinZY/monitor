package com.derbysoft.content.common;

/**
 * Created by zhangyang on 2018/1/4.
 */
public interface Message<T> {
    T getMessage();

    void setMessage(T msg);
}
