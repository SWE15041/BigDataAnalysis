package com.bigdata.service;

import org.springframework.web.multipart.MultipartFile;

public interface DataService {

    boolean uploadFile(MultipartFile multipartFile);

    boolean uploadFileToMongoDB(MultipartFile multipartFile);
}
