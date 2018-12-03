package com.bigdata.controller;

import com.bigdata.config.LogFileParser;
import com.bigdata.core.MainDataAnalysis;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.xml.ws.http.HTTPException;

@RestController
@RequestMapping(value = "/api")
public class DataController {


//    @Autowired
//    private AsyncTaskService asyncTaskService;

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

    @PostConstruct
    public void init() {

        new Thread(new LogFileParser()).start();

    }

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

}
