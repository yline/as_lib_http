package com.lib.http.dev.download;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lib.http.dev.util.XHttpUtil;
import com.yline.application.SDKManager;
import com.yline.http.controller.ResponseMethodCallback;
import com.yline.test.BaseTestActivity;
import com.yline.utils.LogUtil;

import okhttp3.Call;
import okhttp3.Response;

public class DownloadActivity extends BaseTestActivity {

    public static void launcher(Context context) {
        if (null != context) {
            Intent intent = new Intent(context, DownloadActivity.class);
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        }
    }

    @Override
    public void testStart(View view, Bundle savedInstanceState) {
        addButton("DownLoad", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XHttpUtil.doDownload(new ResponseMethodCallback<String>() {
                    @Override
                    public void onSuccess(Call call, Response response, String s) {
                        // 文件下载处理
                        LogUtil.v("length = " + (null != s ? s.length() : "null"));
                        SDKManager.toast("下载成功");
                    }

                    @Override
                    public void onFailure(Call call, Exception ex) {
                        SDKManager.toast("下载失败， ex = " + ex);
                    }
                });
            }
        });
    }
}
