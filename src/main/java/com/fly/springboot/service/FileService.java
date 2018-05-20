package com.fly.springboot.service;

import com.fly.springboot.entity.File;

import java.util.List;

/**
 * @author fly
 * @date 2018/5/19 20:03
 * @description
 **/
public interface FileService {
    /**
     * 保存文件
     * @param file
     * @return
     */
    File saveFile(File file);

    /**
     * 删除文件
     * @param file
     * @return
     */
    void removeFile(File file);

    /**
     * 删除文件
     * @param id
     */
    void removeFile(String id);
    /**
     * 根据id获取文件
     * @param id
     * @return
     */
    File getFileById(String id);

    /**
     * 获取文件列表
     * @return
     */
    List<File> listFiles();
}
