package com.lib.http.dev;

import android.os.Bundle;
import android.view.View;

import com.lib.http.dev.download.DownloadActivity;
import com.lib.http.dev.parse.ParseActivity;
import com.yline.test.BaseTestActivity;

public class MainActivity extends BaseTestActivity {

    @Override
    public void testStart(View view, Bundle savedInstanceState) {
        addButton("Download Test", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadActivity.launcher(MainActivity.this);
            }
        });

        addButton("Parse Text", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseActivity.launcher(MainActivity.this);
            }
        });
    }
}
