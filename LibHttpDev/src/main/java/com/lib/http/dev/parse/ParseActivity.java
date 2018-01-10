package com.lib.http.dev.parse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lib.http.dev.parse.model.BitTorrentManager;
import com.lib.http.dev.parse.model.BitTorrentModel;
import com.yline.test.BaseTestActivity;
import com.yline.utils.LogUtil;
import com.yline.utils.UIScreenUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public static String getSHA1(byte[] bytes) throws NoSuchAlgorithmException, IOException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.update(bytes);

        byte[] digestBytes = messageDigest.digest();
        return BitTorrentManager.byte2HexString(digestBytes, 0, digestBytes.length);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void testStart(View view, Bundle savedInstanceState) {
        addButton("Parse", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputStream inputStream = getResources().getAssets().open("test.torrent");
                    BitTorrentModel torrent = BitTorrentManager.load(inputStream);

                    if (null != torrent) {
                        String result = torrent.print();
                        hintTextView.setText(result);

                        hintTextView.append("infoHash:" + torrent.getInfoName());

                        String magnet = BitTorrentManager.genMagnet(torrent);
                        LogUtil.v("magnet = " + magnet);
                    } else {
                        LogUtil.v("torrent is null");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        hintTextView = addTextView("");
        hintTextView.setTextSize(UIScreenUtil.dp2px(this, 4));

        addButton("Parse One By One", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    File file = FileUtil.getFileExternal(ParseActivity.this, "test", "test");
//                    writeBytesToFile(getAssets().open("test.torrent"), file);
//
////                    LogUtil.v("SHA1 = " + getSHA1(file));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    public void writeBytesToFile(InputStream is, File file) throws IOException {
        FileOutputStream fos = null;
        try {
            byte[] data = new byte[2048];
            int nbread = 0;
            fos = new FileOutputStream(file);
            while ((nbread = is.read(data)) > -1) {
                fos.write(data, 0, nbread);
            }
        } catch (Exception ex) {
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
}
