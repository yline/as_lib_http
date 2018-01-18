package com.lib.http.dev.torrent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lib.http.dev.piecedownload.PieceDownloadActivity;
import com.lib.http.dev.torrent.decode.BitTorrentModel;
import com.lib.http.dev.util.EncryptUtil;
import com.yline.test.BaseTestActivity;
import com.yline.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class ParseActivity extends BaseTestActivity {
    private List<Integer> mMultiChoiceId = new ArrayList<>();

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

                    LogUtil.v("单文件：" + BtManager.genMagnet(torrent));
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

                    LogUtil.v("多文件：" + BtManager.genMagnet(torrent));
                } else {
                    LogUtil.v("torrent is null");
                }
            }
        });

        final TextView fileTextView = addTextView("");
        addButton("检测，文件SHA1值", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String singleShaStr = EncryptUtil.getAssetsEncrypt(ParseActivity.this, "single.torrent", EncryptUtil.SHA1);
                LogUtil.v("single SHA1 = " + singleShaStr);

                String multiShaStr = EncryptUtil.getAssetsEncrypt(ParseActivity.this, "multi.torrent", EncryptUtil.SHA1);
                LogUtil.v("multi SHA1 = " + multiShaStr);

                fileTextView.setText((singleShaStr + "\n" + multiShaStr));
            }
        });

        addButton("测试，字符串和16进制相互转换", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startStr = "123456789qwertyuiop";

                String hexString = EncryptUtil.byte2HexString(startStr.getBytes(), 0, startStr.getBytes().length);
                byte[] hexBytes = EncryptUtil.hexString2Byte(hexString);

                String resultStr = new String(hexBytes);
                LogUtil.v(resultStr);
            }
        });

        addButton("解析文件并下载", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PieceDownloadActivity.launcher(ParseActivity.this);
            }
        });
    }
}














