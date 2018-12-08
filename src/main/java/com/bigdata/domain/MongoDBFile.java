package com.bigdata.domain;

import lombok.Data;

@Data
public class MongoDBFile {
    private String _id;
    private String length;
    private String chunkSize;
    private String uploadDate;
    private String md5;
    private String fileName;
    private String contentType;
    private String aliases;
    private String metadata;

}
