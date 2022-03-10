package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.FileForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aaron
 * @date 1/15/22 10:38 AM
 */
@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int insertFile(File file){
        return fileMapper.insertFile(file);
    }

    public List<File> getFilesByUserId(Integer userId){
        return fileMapper.getFilesByUserId(userId);
    }

    public List<FileForm> getFileForms(List<File> files) {
        List<FileForm> fileFormList = new ArrayList<>();
        for (File file : files) {
            FileForm fileForm = new FileForm(file.getFilename(), file.getFileId());
            fileFormList.add(fileForm);
        }
        return fileFormList;
    }

    public boolean deleteFileByFileId(Integer fileId){
        return fileMapper.deleteFile(fileId);
    }

    public File getFileById(Integer fileId){
        return fileMapper.getFilesById(fileId).get(0);
    }

    public boolean ifExist(String filename) {
        return fileMapper.getFilesByName(filename).size() > 0;
    }
}
