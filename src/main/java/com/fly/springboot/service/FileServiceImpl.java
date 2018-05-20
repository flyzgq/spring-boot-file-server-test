package com.fly.springboot.service;

import com.fly.springboot.entity.File;
import com.fly.springboot.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fly
 * @date 2018/5/19 20:09
 * @description
 **/
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public File saveFile(File file) {
        return fileRepository.save(file);
    }

    @Override
    public void removeFile(String id) {
        fileRepository.deleteById(id);
    }

    @Override
    public void removeFile(File file) {
        fileRepository.delete(file);
    }

    @Override
    public File getFileById(String id) {
        return fileRepository.findFileById(id);
    }

    @Override
    public List<File> listFiles() {
        return fileRepository.findAll();
    }
}
