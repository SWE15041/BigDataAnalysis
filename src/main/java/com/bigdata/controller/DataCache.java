package com.bigdata.controller;

import com.bigdata.core.MainDataAnalysis;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例-双重检测机制
 */
public class DataCache {

    private static DataCache dataCache;
    private boolean analyzing = true;
    private Map<Integer, JSONObject> logResults = new HashMap<>();

    //私有构造函数
    private DataCache() {
    }


    public static DataCache getInstance() {
        if (dataCache == null) {
            synchronized (DataCache.class) {
                if (dataCache == null) {
                    dataCache = new DataCache();
                }
            }
        }
        return dataCache;
    }

    public void analyze() {
        analyzing = true;
        System.out.println("***********开始解析日志文件**************");
        //todo 使用三种方式将日志分析结果加载起来
        new Thread(() -> {
            JSONObject jsonObject1 = MainDataAnalysis.runByParallelComputing();
            logResults.put(1, jsonObject1);
            System.out.println("*********并行解析解析完成");
        }).start();
        new Thread(() -> {
            JSONObject jsonObject2 = MainDataAnalysis.runByParallelComputingNotSafe();
            logResults.put(2, jsonObject2);
            System.out.println("*********非安全并行解析完成");
        }).start();
        new Thread(() -> {
            JSONObject jsonObject0 = MainDataAnalysis.runBySequentialComputing();
            logResults.put(0, jsonObject0);
            System.out.println("*********串行解析完成");
        }).start();
        analyzing = false;
    }

    public boolean isAnalyzing() {
        return analyzing;
    }

    public JSONObject getAnalysisResult(int type) {
        //todo 从缓存里面获取
        JSONObject jsonObject = logResults.get(type);
        if (jsonObject == null) {
            jsonObject = new JSONObject();
        }
        return jsonObject;
    }

}
