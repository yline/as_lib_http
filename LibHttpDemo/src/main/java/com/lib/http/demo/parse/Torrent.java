package com.lib.http.demo.parse;

import android.util.Log;

import com.lib.http.demo.parse.model.BitTorrentModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by yline on 2017/12/5.
 */
public class Torrent {
    private static final String TOP_INFO = "info";

    private final static String TAG = "Torrent";

    private final Map<String, BitTorrentModel> mDecoded_info;
    private final Date mCreateDate;
    private final String mComment;
    private final String mCreatedBy;
    private final String mName;
    private final int mPieceLength;
    private final LinkedList<TorrentFile> mFiles;
    private final long mSize;

    public Torrent(byte[] torrent) throws IOException {
        this(new ByteArrayInputStream(torrent));
    }

    public Torrent(InputStream inputStream) throws IOException {
        BitTorrentParser bitTorrentParser = new BitTorrentParser(inputStream);
        Map<String, BitTorrentModel> topMap = bitTorrentParser.getBitTorrentModel().getMap();

        ArrayList<List<URI>> mTrackers = new ArrayList<>();
        try {
            HashSet<URI> mAllTrackers = new HashSet<>();
            // 解析获得announce-list, 获取tracker地址
            if (topMap.containsKey("announce-list")) {
                List<BitTorrentModel> tiers = topMap.get("announce-list").getList();
                for (BitTorrentModel bv : tiers) {
                    List<BitTorrentModel> trackers = bv.getList();
                    if (trackers.isEmpty()) {
                        continue;
                    }

                    List<URI> tier = new ArrayList<>();
                    for (BitTorrentModel tracker : trackers) {
                        URI uri = new URI(tracker.getString());

                        if (!mAllTrackers.contains(uri)) {
                            tier.add(uri);
                            mAllTrackers.add(uri);
                        }
                    }

                    if (!tier.isEmpty()) {
                        mTrackers.add(tier);
                    }
                }
            } else if (topMap.containsKey("announce")) { // 对应单个tracker地址
                URI tracker = new URI(topMap.get("announce").getString());
                mAllTrackers.add(tracker);

                List<URI> tier = new ArrayList<>();
                tier.add(tracker);
                mTrackers.add(tier);
            }
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }

        // 获取文件创建日期
        mCreateDate = topMap.containsKey("creation date") ? new Date(topMap.get("creation date").getLong() * 1000L) : null;
        // 获取文件的comment
        mComment = topMap.containsKey("comment") ? topMap.get("comment").getString() : null;
        // 获取谁创建的文件
        mCreatedBy = topMap.containsKey("created by") ? topMap.get("created by").getString() : null;

        mDecoded_info = topMap.get("info").getMap();
        // 获取文件名字
        mName = mDecoded_info.get("name").getString();
        mPieceLength = mDecoded_info.get("piece length").getInt();

        mFiles = new LinkedList<>();

        // 解析多文件的信息结构
        if (mDecoded_info.containsKey("files")) {
            for (BitTorrentModel file : mDecoded_info.get("files").getList()) {
                Map<String, BitTorrentModel> fileInfo = file.getMap();
                StringBuilder path = new StringBuilder();
                for (BitTorrentModel pathElement : fileInfo.get("path").getList()) {
                    path.append(File.separator).append(pathElement.getString());
                }
                mFiles.add(new TorrentFile(new File(mName, path.toString()), fileInfo.get("length").getLong()));
            }
        } else {
            // 对于单文件的bt种子, bt文件的名字就是单文件的名字
            mFiles.add(new TorrentFile(new File(mName), mDecoded_info.get("length").getLong()));
        }

        // 计算bt种子中所有文件的大小
        long size = 0;
        for (TorrentFile file : mFiles) {
            size += file.size;
        }
        mSize = size;
        // 下面就是单纯的将bt种子文件解析的内容打印出来
        String infoType = isMultiFile() ? "Multi" : "Single";
        Log.i(TAG, "Torrent: file information: " + infoType);
        Log.i(TAG, "Torrent: file name: " + mName);
        Log.i(TAG, "Torrent: Announced at: " + (mTrackers.size() == 0 ? " Seems to be trackerless" : ""));
        for (int i = 0; i < mTrackers.size(); ++i) {
            List<URI> tier = mTrackers.get(i);
            for (int j = 0; j < tier.size(); ++j) {
                Log.i(TAG, "Torrent: {} " + (j == 0 ? String.format("%2d. ", i + 1) : "    ")
                        + tier.get(j));
            }
        }

        if (mCreateDate != null) {
            Log.i(TAG, "Torrent: createDate: " + mCreateDate);
        }

        if (mComment != null) {
            Log.i(TAG, "Torrent: Comment: " + mComment);
        }

        if (mCreatedBy != null) {
            Log.i(TAG, "Torrent: created by: " + mCreatedBy);
        }

        if (isMultiFile()) {
            Log.i(TAG, "Found {} file(s) in multi-file torrent structure." + mFiles.size());
            int i = 0;
            for (TorrentFile file : mFiles) {
                Log.i(TAG, "Torrent: file is " +
                        (String.format("%2d. path: %s size: %s", ++i, file.file.getPath(), file.size)));
            }
        }

        long pieces = (mSize / mDecoded_info.get("piece length").getInt()) + 1;

        Log.i(TAG, "Torrent: Pieces....: (byte(s)/piece" +
                pieces + " " + mSize / mDecoded_info.get("piece length").getInt());

        Log.i(TAG, "Torrent: Total size...: " + mSize);
    }

    /**
     * 加载指定的种子文件, 将种子文件转化为Torrent对象
     */
    public static Torrent load(File torrent) throws IOException {
        byte[] data = readFileToByteArray(torrent);
        return new Torrent(data);
    }

    public static Torrent load(InputStream inputStream) throws IOException {
        return new Torrent(inputStream);
    }

    /**
     * 由file对象获得byte[]对象
     */
    private static byte[] readFileToByteArray(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public boolean isMultiFile() {
        return mFiles.size() > 1;
    }

    // 对应bt文件中包含多个文件, 定义TorrentFile类来表示每个文件,方便管理
    public static class TorrentFile {

        public final File file;
        public final long size;

        public TorrentFile(File file, long size) {
            this.file = file;
            this.size = size;
        }
    }
}
