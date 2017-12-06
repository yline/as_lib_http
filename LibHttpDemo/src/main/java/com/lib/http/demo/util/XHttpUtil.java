package com.lib.http.demo.util;

import com.lib.http.demo.download.DownloadModel;
import com.lib.http.demo.download.HttpDownloadResponse;
import com.lib.http.demo.model.VNewsMultiplexModel;
import com.lib.http.demo.model.VNewsSingleModel;
import com.lib.http.demo.model.WNewsMultiplexModel;
import com.yline.application.SDKManager;
import com.yline.http.OkHttpManager;
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

    public static void doGetDefault(XHttpAdapter<VNewsSingleModel> adapter) {
        String httpUrl = "http://" + IP + "/android/git_api/libhttp/puppet_list.txt";
        OkHttpManager.doGet(httpUrl, null, VNewsSingleModel.class, adapter);
    }

    public static void doPostDefault(WNewsMultiplexModel wNewsMultiplexBean, XHttpAdapter<VNewsMultiplexModel> adapter) {
        String httpUrl = "http://" + IP + "/android/git_api/libhttp/puppet_list.txt";
        OkHttpManager.doPost(httpUrl, wNewsMultiplexBean, VNewsMultiplexModel.class, adapter);
    }

    public static void doGetNetPrior(XHttpAdapter<VNewsSingleModel> adapter) {
        String httpUrl = "http://" + IP + "/android/git_api/libhttp/puppet_list.txt";
        OkHttpManager.doGetNetPrior(httpUrl, null, VNewsSingleModel.class, adapter);
    }

    public static void doPostNetPrior(WNewsMultiplexModel wNewsMultiplexBean, XHttpAdapter<VNewsMultiplexModel> adapter) {
        String httpUrl = "http://" + IP + "/android/git_api/libhttp/puppet_list.txt";
        OkHttpManager.doPostNetPrior(httpUrl, wNewsMultiplexBean, VNewsMultiplexModel.class, adapter);
    }

    public static void doGetCachePrior(XHttpAdapter<VNewsSingleModel> adapter) {
        String httpUrl = "http://" + IP + "/android/git_api/libhttp/puppet_list.txt";
        OkHttpManager.doGetCachePrior(httpUrl, null, VNewsSingleModel.class, adapter);
    }

    public static void doPostCachePrior(WNewsMultiplexModel wNewsMultiplexBean, XHttpAdapter<VNewsMultiplexModel> adapter) {
        String httpUrl = "http://" + IP + "/android/git_api/libhttp/puppet_list.txt";
        OkHttpManager.doPostCachePrior(httpUrl, wNewsMultiplexBean, VNewsMultiplexModel.class, adapter);
    }

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
