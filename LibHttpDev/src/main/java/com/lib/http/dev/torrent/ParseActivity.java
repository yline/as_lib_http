package com.lib.http.dev.torrent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lib.http.dev.torrent.decode.BitTorrentModel;
import com.lib.http.dev.util.EncryptUtil;
import com.yline.test.BaseTestActivity;
import com.yline.utils.LogUtil;

public class ParseActivity extends BaseTestActivity {
    private TextView hintTextView;

    public static void launcher(Context context) {
        if (null != context) {
            Intent intent = new Intent(context, ParseActivity.class);
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        }
    }

    @Override
    public void testStart(View view, Bundle savedInstanceState) {
        final TextView singleTextView = addTextView("");
        addButton("解析，单文件结构", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitTorrentModel torrent = BtManager.load(ParseActivity.this, "single.torrent");

                if (null != torrent) {
                    String result = torrent.print();
                    singleTextView.setText(result);

//                        String magnet = BtDecodeManager.genMagnet(torrent);
//                        LogUtil.v("magnet = " + magnet);
                } else {
                    LogUtil.v("torrent is null");
                }
            }
        });

        final TextView multiTextView = addTextView("");
        addButton("解析，多文件结构", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitTorrentModel torrent = BtManager.load(ParseActivity.this, "multi.torrent");

                if (null != torrent) {
                    String result = torrent.print();
                    multiTextView.setText(result);

//                    String magnet = BtManager.genMagnet(torrent);
//                    LogUtil.v("magnet = " + magnet);
                } else {
                    LogUtil.v("torrent is null");
                }
            }
        });

        addButton("检测，SHA1值", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String singleShaStr = EncryptUtil.getAssetsEncrypt(ParseActivity.this, "single.torrent", EncryptUtil.SHA1);
                LogUtil.v("single SHA1 = " + singleShaStr);
                
                String multiShaStr = EncryptUtil.getAssetsEncrypt(ParseActivity.this, "multi.torrent", EncryptUtil.SHA1);
                LogUtil.v("multi SHA1 = " + multiShaStr);
            }
        });

        // 生成，下载链接

    }
}
