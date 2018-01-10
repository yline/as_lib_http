package com.lib.http.dev.util;

import com.lib.http.dev.download.DownloadModel;
import com.lib.http.dev.download.HttpDownloadResponse;
import com.yline.application.SDKManager;
import com.yline.http.client.DefaultClient;
import com.yline.http.controller.ResponseHandlerCallback;
import com.yline.http.manager.XHttp;
import com.yline.http.manager.XHttpAdapter;
import com.yline.utils.FileUtil;
import com.yline.utils.LogUtil;

import java.io.File;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class XHttpUtil {
    private static final String IP = "192.168.2.184";

    public static void doDownload() {
        String httpUrl = "http://" + IP + "/android/git_api/libhttp/BY2.zip";

        new XHttp() {
            @Override
            protected OkHttpClient getOkHttpClient() {
                return DefaultClient.getInstance();
            }

            @Override
            protected void onRequestBuilder(Request.Builder builder) {
                File dirFile = FileUtil.getFileExternalDir(SDKManager.getApplication(), "WAMP");
                builder.tag(new DownloadModel(mTag, dirFile.getPath(), String.valueOf(mTag)));
            }
        }.doPost(httpUrl, "", String.class, new XHttpAdapter<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                // 文件下载处理
                LogUtil.v("length = " + (null != s ? s.length() : "null"));
            }

            @Override
            public boolean isResponseHandler() {
                return false;
            }

            @Override
            public ResponseHandlerCallback getRequestHandler() {
                return new HttpDownloadResponse(this);
            }
        });
    }
}
