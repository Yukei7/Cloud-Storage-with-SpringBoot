package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public File getById(Integer fileId) {
        return fileMapper.getById(fileId);
    }

    public List<File> getAllByUserId(Integer userId) {
        return fileMapper.getAllByUserId(userId);
    }

    public File getByFilename(String filename) {
        return fileMapper.getByFilename(filename);
    }

    public File getByFilenameAndUserId(String filename, Integer userId) {
        return fileMapper.getByFilenameAndUserId(filename, userId);
    }

    public Integer insert(File file) {
        return fileMapper.insertFile(file);
    }

    public Integer update(File file) {
        return fileMapper.updateFile(file);
    }

    public Integer deleteById(Integer fileId) {
        return fileMapper.deleteById(fileId);
    }

    public Integer deleteByFilename(String filename) {
        return fileMapper.deleteByFilename(filename);
    }

}
