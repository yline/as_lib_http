package com.lib.http.demo.parse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lib.http.demo.parse.model.BitTorrentManager;
import com.lib.http.demo.parse.model.BitTorrentModel;
import com.yline.test.BaseTestActivity;
import com.yline.utils.UIScreenUtil;

import java.io.IOException;
import java.io.InputStream;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_parse);
    }

    @Override
    public void testStart(View view, Bundle savedInstanceState) {
        addButton("Parse", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputStream inputStream = getResources().getAssets().open("test.torrent");
                    BitTorrentModel torrent = BitTorrentManager.load(inputStream);

                    String result = torrent.print();
                    hintTextView.setText(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        hintTextView = addTextView("");
        hintTextView.setTextSize(UIScreenUtil.dp2px(this, 4));
    }
}
