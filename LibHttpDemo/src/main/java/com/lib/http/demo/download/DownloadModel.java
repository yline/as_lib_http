package com.lib.http.demo.download;

import java.io.Serializable;

/**
 * 下载的 Model格式
 *
 * @author yline 2017/11/27 -- 10:01
 * @version 1.0.0
 */
public class DownloadModel implements Serializable {
    /**
     * 建立请求的时间
     */
    private String requestTime;
    /**
     * 存储文件，父路径
     */
    private String storeDirPath;
    /**
     * 储存文件，具体名称
     */
    private String storeFileName;

    public DownloadModel(String requestTime, String storeDirPath, String storeFileName) {
        this.requestTime = requestTime;
        this.storeDirPath = storeDirPath;
        this.storeFileName = storeFileName;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getStoreDirPath() {
        return storeDirPath;
    }

    public void setStoreDirPath(String storeDirPath) {
        this.storeDirPath = storeDirPath;
    }

    public String getStoreFileName() {
        return storeFileName;
    }

    public void setStoreFileName(String storeFileName) {
        this.storeFileName = storeFileName;
    }

    @Override
    public String toString() {
        return "DownloadModel{" +
                "requestTime='" + requestTime + '\'' +
                ", storeDirPath='" + storeDirPath + '\'' +
                ", storeFileName='" + storeFileName + '\'' +
                '}';
    }
}
