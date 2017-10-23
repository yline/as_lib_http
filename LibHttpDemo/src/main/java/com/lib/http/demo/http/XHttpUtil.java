package com.lib.http.demo.http;

import com.lib.http.demo.bean.VNewsMultiplexBean;
import com.lib.http.demo.bean.VNewsSingleBean;
import com.lib.http.demo.bean.WNewsMultiplexBean;
import com.yline.http.OkHttpManager;
import com.yline.http.XHttp;
import com.yline.http.XHttpAdapter;
import com.yline.http.client.HttpCachePriorClient;
import com.yline.http.client.HttpNetPriorClient;

import okhttp3.OkHttpClient;

public class XHttpUtil {
    public static void doGetDefault(XHttpAdapter<VNewsSingleBean> adapter) {
        String httpUrl = "http://192.168.0.112/android/git_api/libhttp/puppet_list.txt";
        OkHttpManager.doGet(httpUrl, null, VNewsSingleBean.class, adapter);
    }

    public static void doPostDefault(WNewsMultiplexBean wNewsMultiplexBean, XHttpAdapter<VNewsMultiplexBean> adapter) {
        String httpUrl = "http://192.168.0.112/android/git_api/libhttp/puppet_list.txt";
        OkHttpManager.doPost(httpUrl, wNewsMultiplexBean, VNewsMultiplexBean.class, adapter);
    }

    public static void doGetNetPrior(XHttpAdapter<VNewsSingleBean> adapter) {
        String httpUrl = "http://192.168.0.112/android/git_api/libhttp/puppet_list.txt";
        new XHttp() {
            @Override
            protected OkHttpClient getOkHttpClient() {
                return HttpNetPriorClient.getInstance();
            }
        }.doGet(httpUrl, null, VNewsSingleBean.class, adapter);
    }

    public static void doPostNetPrior(WNewsMultiplexBean wNewsMultiplexBean, XHttpAdapter<VNewsMultiplexBean> adapter) {
        String httpUrl = "http://192.168.0.112/android/git_api/libhttp/puppet_list.txt";
        new XHttp() {
            @Override
            protected OkHttpClient getOkHttpClient() {
                return HttpNetPriorClient.getInstance();
            }
        }.doPost(httpUrl, wNewsMultiplexBean, VNewsMultiplexBean.class, adapter);
    }

    public static void doGetCachePrior(XHttpAdapter<VNewsSingleBean> adapter) {
        String httpUrl = "http://192.168.0.112/android/git_api/libhttp/puppet_list.txt";
        new XHttp() {
            @Override
            protected OkHttpClient getOkHttpClient() {
                return HttpCachePriorClient.getInstance();
            }
        }.doGet(httpUrl, null, VNewsSingleBean.class, adapter);
    }

    public static void doPostCachePrior(WNewsMultiplexBean wNewsMultiplexBean, XHttpAdapter<VNewsMultiplexBean> adapter) {
        String httpUrl = "http://192.168.0.112/android/git_api/libhttp/puppet_list.txt";
        new XHttp() {
            @Override
            protected OkHttpClient getOkHttpClient() {
                return HttpCachePriorClient.getInstance();
            }
        }.doPost(httpUrl, wNewsMultiplexBean, VNewsMultiplexBean.class, adapter);
    }
}
