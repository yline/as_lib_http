package com.lib.http.demo.main;

import android.os.Bundle;
import android.view.View;

import com.lib.http.demo.model.VNewsMultiplexModel;
import com.lib.http.demo.model.VNewsSingleModel;
import com.lib.http.demo.model.WNewsMultiplexModel;
import com.lib.http.demo.util.XHttpUtil;
import com.yline.http.manager.XHttpAdapter;
import com.yline.test.BaseTestActivity;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends BaseTestActivity {
    @Override
    public void testStart(View view, Bundle savedInstanceState) {
        addButton("doGetDefault", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XHttpUtil.doGetDefault(new XHttpAdapter<VNewsSingleModel>() {
                    @Override
                    public void onSuccess(Call call, Response response, VNewsSingleModel vNewsSingleBean) {

                    }
                });
            }
        });

        addButton("doPostDefault", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XHttpUtil.doPostDefault(new WNewsMultiplexModel(0, 10), new XHttpAdapter<VNewsMultiplexModel>() {
                    @Override
                    public void onSuccess(Call call, Response response, VNewsMultiplexModel vNewsMultiplexBean) {

                    }
                });
            }
        });

        addButton("doGetNetPrior", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XHttpUtil.doGetNetPrior(new XHttpAdapter<VNewsSingleModel>() {
                    @Override
                    public void onSuccess(Call call, Response response, VNewsSingleModel vNewsSingleBean) {

                    }
                });
            }
        });

        addButton("doPostNetPrior", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XHttpUtil.doPostNetPrior(new WNewsMultiplexModel(0, 10), new XHttpAdapter<VNewsMultiplexModel>() {
                    @Override
                    public void onSuccess(Call call, Response response, VNewsMultiplexModel vNewsMultiplexBean) {

                    }
                });
            }
        });

        addButton("doGetCachePrior", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XHttpUtil.doGetCachePrior(new XHttpAdapter<VNewsSingleModel>() {
                    @Override
                    public void onSuccess(Call call, Response response, VNewsSingleModel vNewsSingleBean) {

                    }
                });
            }
        });

        addButton("doPostCachePrior", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XHttpUtil.doPostCachePrior(new WNewsMultiplexModel(0, 10), new XHttpAdapter<VNewsMultiplexModel>() {
                    @Override
                    public void onSuccess(Call call, Response response, VNewsMultiplexModel vNewsMultiplexBean) {

                    }
                });
            }
        });
    }
}
