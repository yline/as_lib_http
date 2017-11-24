package com.lib.http.demo.http;

import com.lib.http.demo.bean.VNewsMultiplexBean;
import com.lib.http.demo.bean.VNewsSingleBean;
import com.lib.http.demo.bean.WNewsMultiplexBean;
import com.yline.http.OkHttpManager;
import com.yline.http.manager.XHttpAdapter;

public class XHttpUtil {
    private static final String IP = "192.168.2.252";

    public static void doGetDefault(XHttpAdapter<VNewsSingleBean> adapter) {
        String httpUrl = "http://" + IP + "/android/git_api/libhttp/puppet_list.txt";
        OkHttpManager.doGet(httpUrl, null, VNewsSingleBean.class, adapter);
    }

    public static void doPostDefault(WNewsMultiplexBean wNewsMultiplexBean, XHttpAdapter<VNewsMultiplexBean> adapter) {
        String httpUrl = "http://" + IP + "/android/git_api/libhttp/puppet_list.txt";
        OkHttpManager.doPost(httpUrl, wNewsMultiplexBean, VNewsMultiplexBean.class, adapter);
    }

    public static void doGetNetPrior(XHttpAdapter<VNewsSingleBean> adapter) {
        String httpUrl = "http://" + IP + "/android/git_api/libhttp/puppet_list.txt";
        OkHttpManager.doGetNetPrior(httpUrl, null, VNewsSingleBean.class, adapter);
    }

    public static void doPostNetPrior(WNewsMultiplexBean wNewsMultiplexBean, XHttpAdapter<VNewsMultiplexBean> adapter) {
        String httpUrl = "http://" + IP + "/android/git_api/libhttp/puppet_list.txt";
        OkHttpManager.doPostNetPrior(httpUrl, wNewsMultiplexBean, VNewsMultiplexBean.class, adapter);
    }

    public static void doGetCachePrior(XHttpAdapter<VNewsSingleBean> adapter) {
        String httpUrl = "http://" + IP + "/android/git_api/libhttp/puppet_list.txt";
        OkHttpManager.doGetCachePrior(httpUrl, null, VNewsSingleBean.class, adapter);
    }

    public static void doPostCachePrior(WNewsMultiplexBean wNewsMultiplexBean, XHttpAdapter<VNewsMultiplexBean> adapter) {
        String httpUrl = "http://" + IP + "/android/git_api/libhttp/puppet_list.txt";
        OkHttpManager.doPostCachePrior(httpUrl, wNewsMultiplexBean, VNewsMultiplexBean.class, adapter);
    }
}
