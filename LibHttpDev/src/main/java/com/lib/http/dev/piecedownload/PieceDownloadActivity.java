package com.lib.http.dev.piecedownload;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lib.http.dev.R;
import com.lib.http.dev.torrent.BtManager;
import com.lib.http.dev.torrent.decode.BitTorrentModel;
import com.yline.application.SDKManager;
import com.yline.base.BaseAppCompatActivity;
import com.yline.utils.LogUtil;
import com.yline.view.recycler.adapter.AbstractCommonRecyclerAdapter;
import com.yline.view.recycler.decoration.CommonLinearDecoration;
import com.yline.view.recycler.holder.RecyclerViewHolder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PieceDownloadActivity extends BaseAppCompatActivity {
    public static final int BYTE_UNIT = 1024;
    private PieceDownloadAdapter mPieceAdapter;

    public static void launcher(Context context) {
        if (null != context) {
            Intent intent = new Intent(context, PieceDownloadActivity.class);
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        }
    }

    private static String byteLength2byteSize(long length) {
        if (length < BYTE_UNIT) {
            return length + "B";
        } else if (length < BYTE_UNIT * BYTE_UNIT) {
            return String.format(Locale.CHINA, "%.2fK", length * 1.0f / BYTE_UNIT);
        } else if (length < BYTE_UNIT * BYTE_UNIT * BYTE_UNIT) {
            return String.format(Locale.CHINA, "%.2fM", length * 1.0f / BYTE_UNIT / BYTE_UNIT);
        } else {
            return String.format(Locale.CHINA, "%.2fG", length * 1.0f / BYTE_UNIT / BYTE_UNIT / BYTE_UNIT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piece_download);

        initView();
        initData();

        String httpUrl = "";

        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("info_hash", ""); // 片段hash值
        reqMap.put("peer_id", ""); // 一般是客户端在启动时生成的 20 字节字符串, 假定在您的本地计算机上是唯一的
        reqMap.put("port", "6888"); // 监听的网络端口号，一般是 6881-6889
        reqMap.put("uploaded", "100"); // 自上一次向 Tracker 发送 event=start 请求之后，BT客户端 （对于该种子）的总上传字节数，以十进制整数 ASCII 字符串表示
        reqMap.put("downloaded", "100"); // 自上一次向 Tracker 发送 event=start 请求之后，BT客户端 （对于该种子）的总下载字节数，以十进制整数 ASCII 字符串表示
        reqMap.put("left", "100"); // 十进制表示的剩余字节总数，注意这个值不能通过downloaded和length进行算数计算得到，因为当一些下载文件块的数据的完整性校验失败的，这些文件块必须被重新下载。
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.piece_download_recycler);
        mPieceAdapter = new PieceDownloadAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new CommonLinearDecoration(this));
        recyclerView.setAdapter(mPieceAdapter);

        initViewClick();
    }

    private void initViewClick() {
        findViewById(R.id.piece_download_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDKManager.toast("确定");
            }
        });
    }

    private void initData() {
        BitTorrentModel bitTorrentModel = BtManager.load(PieceDownloadActivity.this, "multi.torrent");
        if (null != bitTorrentModel) {
            // 显示主题
            TextView nameTv = findViewById(R.id.piece_download_name);
            nameTv.setText(bitTorrentModel.getInfoName());

            // 每个选项
            mPieceAdapter.setDataList(bitTorrentModel.getFileModelList(), true);
        } else {
            LogUtil.v("解析失败");
            SDKManager.toast("解析失败");
        }
    }

    private class PieceDownloadAdapter extends AbstractCommonRecyclerAdapter<BitTorrentModel.BitTorrentFileModel> {
        private boolean[] mCheckedArray;

        @Override
        public int getItemRes() {
            return R.layout.item_piece_download;
        }

        @Override
        public void setDataList(List<BitTorrentModel.BitTorrentFileModel> bitTorrentFileModels, boolean isNotify) {
            if (null != bitTorrentFileModels && !bitTorrentFileModels.isEmpty()) {
                mCheckedArray = new boolean[bitTorrentFileModels.size()];
                Arrays.fill(mCheckedArray, true);
            }
            super.setDataList(bitTorrentFileModels, isNotify);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
            BitTorrentModel.BitTorrentFileModel fileModel = getItem(position);

            // 路径
            holder.setText(R.id.item_piece_download_name, fileModel.getFilePath());

            // 大小
            String sizeStr = byteLength2byteSize(fileModel.getLength());
            holder.setText(R.id.item_piece_download_size, sizeStr);

            // 是否选择
            final AppCompatCheckBox checkBox = holder.get(R.id.item_piece_download_check_box);
            if (position >= mCheckedArray.length) {
                checkBox.setVisibility(View.GONE);
            } else {
                checkBox.setVisibility(View.VISIBLE);
                checkBox.setChecked(mCheckedArray[position]);
                checkBox.setClickable(false);

                // 状态改变
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean oldIsChecked = checkBox.isChecked();
                        checkBox.setChecked(!oldIsChecked);

                        mCheckedArray[position] = !oldIsChecked;
                    }
                });
            }
        }
    }
}
