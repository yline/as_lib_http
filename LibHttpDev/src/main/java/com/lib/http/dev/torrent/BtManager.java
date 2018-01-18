package com.lib.http.dev.torrent;

import android.content.Context;
import android.text.TextUtils;

import com.lib.http.dev.torrent.decode.BitTorrentModel;
import com.lib.http.dev.torrent.decode.BtDecodeManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 蓝牙管理类
 *
 * @author yline 2018/1/17 -- 13:27
 * @version 1.0.0
 */
public class BtManager {
    /**
     * 解析种子文件
     */
    public static BitTorrentModel load(Context context, String assetFileName) {
        try {
            InputStream inputStream = context.getResources().getAssets().open(assetFileName);
            return load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析种子文件
     */
    public static BitTorrentModel load(InputStream inputStream) {
        try {
            return BtDecodeManager.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成磁力链接
     */
    public static String genMagnet(BitTorrentModel torrentModel) {
        if (null != torrentModel) {
            return genMagnet(torrentModel.getInfoHash(), torrentModel.getInfoName(), torrentModel.getAnnounceList());
        }
        return null;
    }

    /**
     * 生成磁力链接，放入浏览器中无法打开，放入迅雷中可以打开
     *
     * @return 磁力链接
     */
    private static String genMagnet(String infoHash, String fileName, List<String> fileUrlList) {
        StringBuilder stringBuilder = new StringBuilder("magnet:?");
        if (!TextUtils.isEmpty(infoHash)) {
            stringBuilder.append("xt=urn:btih:");
            stringBuilder.append(infoHash);
        }

        if (!TextUtils.isEmpty(fileName)) {
            stringBuilder.append("&dn");
            stringBuilder.append(fileName);
        }

        if (null != fileUrlList && !fileUrlList.isEmpty()) {
            for (String url : fileUrlList) {
                stringBuilder.append("&tr=");
                stringBuilder.append(url);
            }
        }

        return stringBuilder.toString();
    }
}
