package com.document.plus.incrementer;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class CustomIdGenerator {
    private static final Logger log = LoggerFactory.getLogger(CustomIdGenerator.class);
    private final AtomicLong al = new AtomicLong(1);

    public Long nextId(Object entity) {
        String bizKey = entity.getClass().getName();
        log.info("bizKey:{}", bizKey);
        MetaObject metaObject = SystemMetaObject.forObject(entity);
        String name = (String) metaObject.getValue("name");
        final long id = al.getAndAdd(1);
        log.info("为{}生成主键值->:{}", name, id);
        return id;
    }
}
