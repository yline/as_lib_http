package com.lib.http.demo.download;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lib.http.demo.util.XHttpUtil;
import com.yline.test.BaseTestActivity;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_download);
    }

    @Override
    public void testStart(View view, Bundle savedInstanceState) {
        addButton("DownLoad", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XHttpUtil.doDownload();
            }
        });
    }
}