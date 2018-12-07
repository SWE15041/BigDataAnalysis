package com.bigdata.controller;

import com.bigdata.constant.Status;
import com.bigdata.core.MainDataAnalysis;
import com.bigdata.service.DataService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.http.HTTPException;

@RestController
@RequestMapping(value = "/api")
public class DataController {


    //    @Autowired
//    private AsyncTaskService asyncTaskService;
    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/data/analysis/{type}", method = RequestMethod.GET)
    public JSONObject analysis(@PathVariable int type) {
        if (DataCache.getInstance().isAnalyzing()) {
            // 返回提示  正在解析。。
//            throw new HTTPException(1);
//            return null;
            JSONObject result = new JSONObject();
            result.put("code", "0");
            result.put("msg", "系统正在解析，请稍后访问");
            return result;
        } else {
            return DataCache.getInstance().getAnalysisResult(type);
        }

    }

//    @PostConstruct
//    public void init() {
//
//        new Thread(new LogFileParser()).start();
//
//    }

    @RequestMapping(value = "/server/cpuInfo", method = RequestMethod.GET)
    public JSONObject getCPUinfo() {
        JSONObject systemInfo = null;
        try {
            systemInfo = MainDataAnalysis.getSystemInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return systemInfo;
    }


    @RequestMapping(value = "/uploadtextfile", method = RequestMethod.POST)
    public Status uploadFile(@RequestParam("uploadfile") MultipartFile multipartFile) {

        if (multipartFile.isEmpty()) {
            throw new HTTPException(400);
        }
        //todo 上传文件
        dataService.uploadFile(multipartFile);
        return Status.SUCCESS;
    }


}
