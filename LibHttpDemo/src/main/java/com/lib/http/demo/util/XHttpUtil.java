package com.lib.http.demo.util;

import com.lib.http.demo.model.VNewsMultiplexModel;
import com.lib.http.demo.model.VNewsSingleModel;
import com.lib.http.demo.model.WNewsMultiplexModel;
import com.yline.http.OkHttpManager;
import com.yline.http.manager.XHttpAdapter;

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
}
