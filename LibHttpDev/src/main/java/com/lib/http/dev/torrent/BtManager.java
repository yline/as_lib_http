package com.lib.http.dev.torrent;

import android.content.Context;

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
    public static BitTorrentModel load(InputStream inputStream) {
        try {
            return BtDecodeManager.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

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
     * 目前还缺少，infoHash信息
     */
    public static String genMagnet(BitTorrentModel torrentModel) {
        StringBuilder stringBuilder = new StringBuilder("magnet:?xt=urn:");
        stringBuilder.append("btih:");
        stringBuilder.append(torrentModel.getInfoPieceList().get(0));

        stringBuilder.append("&dn=");
        stringBuilder.append(torrentModel.getInfoName());

        List<String> fileUrlList = torrentModel.getAnnounceList();
        for (String url : fileUrlList) {
            stringBuilder.append("&tr=");
            stringBuilder.append(url);
        }
        return stringBuilder.toString();
    }
}
