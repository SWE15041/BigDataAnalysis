package com.bigdata.config;

import com.bigdata.controller.DataCache;

public class LogFileParser implements Runnable {

    private boolean fileChanged = false;

    @Override
    public void run() {
        DataCache.getInstance().analyze();
        // 定时取日志文件调用初始化方法重新解析
        while (true) {
            //todo 探测文件是否有改变

            try {
                if (fileChanged) {
                    DataCache.getInstance().analyze();
                }
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
