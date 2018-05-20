package com.fly.springboot.repository;

import com.fly.springboot.entity.File;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author fly
 * @date 2018/5/19 20:01
 * @description
 **/
public interface FileRepository extends MongoRepository<File, String> {
    /**
     * 根据id获取文件
     * @param id
     * @return
     */
    File findFileById(String id);
}
