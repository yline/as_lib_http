package com.lib.http.dev;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lib.http.dev.download.DownloadActivity;
import com.lib.http.dev.parse.ParseActivity;
import com.lib.http.dev.util.EncryptUtil;
import com.yline.test.BaseTestActivity;

public class MainActivity extends BaseTestActivity {

    @Override
    public void testStart(View view, final Bundle savedInstanceState) {
        final TextView hintEncryptTextView = addTextView("");
        addButton("解密Utils 测试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();

                long startTime = System.currentTimeMillis();
                String result = EncryptUtil.getAssetsEncrypt(MainActivity.this, "test.torrent", EncryptUtil.MD5);
                stringBuilder.append("Asset2MD5 = ");
                stringBuilder.append(result);
                stringBuilder.append("    diffTime = ");
                stringBuilder.append(System.currentTimeMillis() - startTime);
                stringBuilder.append('\n');

                startTime = System.currentTimeMillis();
                result = EncryptUtil.getAssetsEncrypt(MainActivity.this, "test.torrent", EncryptUtil.SHA1);
                stringBuilder.append("Asset2SHA1 = ");
                stringBuilder.append(result);
                stringBuilder.append("    diffTime = ");
                stringBuilder.append(System.currentTimeMillis() - startTime);
                stringBuilder.append('\n');

                startTime = System.currentTimeMillis();
                result = EncryptUtil.getAssetsEncrypt(MainActivity.this, "test.torrent", EncryptUtil.SHA256);
                stringBuilder.append("Asset2SHA1 = ");
                stringBuilder.append(result);
                stringBuilder.append("    diffTime = ");
                stringBuilder.append(System.currentTimeMillis() - startTime);
                stringBuilder.append('\n');

                result = stringBuilder.toString();

                hintEncryptTextView.setText(result);
            }
        });

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
