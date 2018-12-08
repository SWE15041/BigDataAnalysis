package com.bigdata.service.impl;

import com.bigdata.service.DataService;
import com.mongodb.gridfs.GridFSFile;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
public class DataServiceImpl implements DataService {

    private static final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Override
    public boolean uploadFile(MultipartFile multipartFile) {
        String targetFilePath = "E:/filePath";
        String fileName = UUID.randomUUID().toString().replace("-", "");
        String fileSuffix = getFileSuffix(multipartFile);
        if(fileSuffix!=null){
            fileName+=fileSuffix;
        }
        File targetFile = new File(targetFilePath + File.separator + fileName);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(targetFile);
            try {
                IOUtils.copy(multipartFile.getInputStream(), fileOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    //获取文件扩展名（后缀）
    private String getFileSuffix(MultipartFile multipartFile) {
        if (multipartFile == null) {
            return null;
        }
        String fileName = multipartFile.getOriginalFilename();
        int suffixIndex = fileName.lastIndexOf(".");
        if (suffixIndex == -1) {
            return null;
        } else {
            return fileName.substring(suffixIndex, fileName.length());
        }
    }

    //将文件上传到mongodb
//        原文：https://blog.csdn.net/xiaoxiangzi520/article/details/80595287
    @Override
    public boolean uploadFileToMongoDB(MultipartFile multipartFile) {

        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String fileName = UUID.randomUUID().toString().replace("-", "");
//        String fileSuffix = getFileSuffix(multipartFile);
//        if(fileSuffix!=null){
//            fileName+=fileSuffix;
//        }
        String filename = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        ObjectId store = gridFsTemplate.store(inputStream, filename, contentType);
        System.out.println(store.toString());
        return true;
//        GridFSFile gridFSFile = gridFsTemplate.store(inputStream,filename,contentType);
//        FileInfoAO fileInfo = new FileInfoAO();
//        fileInfo.setContentType(contentType);
//        fileInfo.setFileName(fileName);
//        fileInfo.setLastUpdateBy(user != null ? user.getId() : null);
//        fileInfo.setMongoFileId(gridFSFile.getId().toString());
    }
}
