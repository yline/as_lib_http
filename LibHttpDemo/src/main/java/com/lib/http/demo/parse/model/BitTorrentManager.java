package com.lib.http.demo.parse.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * BitTorrent解析
 *
 * @author yline 2017/12/6 -- 13:49
 * @version 1.0.0
 */
public class BitTorrentManager {
    private static final String TOP_ANNOUNCE = "announce";
    private static final String TOP_ANNOUNCE_LIST = "announce-list";
    private static final String TOP_COMMENT = "comment";
    private static final String TOP_COMMENT_UTF8 = "comment.utf-8";
    private static final String TOP_CREATION_DATE = "creation date";
    private static final String TOP_CREATION_BY = "created by";
    private static final String TOP_ENCODING = "encoding";
    private static final String TOP_INFO = "info";
    private static final String TOP_NODES = "nodes";

    private static final String INFO_NAME = "name";
    private static final String INFO_NAME_UTF8 = "name.utf-8";
    private static final String INFO_PIECE_LENGTH = "piece length";
    private static final String INFO_PIECE = "pieces";
    private static final String INFO_PUBLISHER = "publisher";
    private static final String INFO_PUBLISHER_URL = "publisher-url";
    private static final String INFO_PUBLISHER_URL_UTF8 = "publisher-url.utf-8";
    private static final String INFO_PUBLISHER_UTF8 = "publisher.utf-8";
    private static final String INFO_FILES = "files";

    private static final String FILES_LENGTH = "length";
    private static final String FILES_PATH = "path";
    private static final String FILES_PATH_UTF8 = "path.utf-8";

    private BitTorrentManager() {
    }

//    /**
//     * 加载指定的种子文件, 将种子文件转化为Torrent对象
//     */
//    public static BitTorrentManager load(File torrent) throws IOException {
//        byte[] data = readFileToByteArray(torrent);
//        return new BitTorrentManager(data);
//    }
//
//    public static BitTorrentManager load(InputStream inputStream) throws IOException {
//        return new BitTorrentManager(inputStream);
//    }
//
//    /**
//     * 由file对象获得byte[]对象
//     */
//    private static byte[] readFileToByteArray(File file) {
//        byte[] buffer = null;
//        try {
//            FileInputStream fis = new FileInputStream(file);
//            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
//            byte[] b = new byte[1024];
//            int n;
//            while ((n = fis.read(b)) != -1) {
//                bos.write(b, 0, n);
//            }
//            fis.close();
//            bos.close();
//            buffer = bos.toByteArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return buffer;
//    }

    //    public BitTorrentManager(byte[] torrent) throws IOException {
//        this(new ByteArrayInputStream(torrent));
//    }

    public static BitTorrentModel load(InputStream inputStream) throws IOException {
        return new BitTorrentManager().expressionBitTorrent(inputStream);
    }

    private BitTorrentModel expressionBitTorrent(InputStream inputStream) throws IOException {
        BitTorrentObject bitTorrentObject = BitTorrentParser.getParseResult(inputStream);
        if (null != bitTorrentObject) {
            BitTorrentModel expressionResult = new BitTorrentModel();

            Map<String, BitTorrentObject> topMap = bitTorrentObject.getMap();
            expressionResult.setTopKeySet(topMap.keySet());

            // 开始解析 --> encoding [编码格式]
            String encoding = topMap.containsKey(TOP_ENCODING) ? topMap.get(TOP_ENCODING).getString() : null;
            expressionResult.setEncoding(encoding);

            // --> announce [主要地址]
            if (topMap.containsKey(TOP_ANNOUNCE)) {
                String announceStr = topMap.get(TOP_ANNOUNCE).getString(encoding);
                expressionResult.setAnnounce(announceStr);
            }

            // --> announce-list [其它地址]
            if (topMap.containsKey(TOP_ANNOUNCE_LIST)) {
                BitTorrentObject announceListModel = topMap.get(TOP_ANNOUNCE_LIST);
                List<BitTorrentObject> announceList = announceListModel.getList();

                List<String> announceListResult = new ArrayList<>();
                for (BitTorrentObject announceModel : announceList) {
                    List<BitTorrentObject> trackerList = announceModel.getList();
                    if (null == trackerList || trackerList.isEmpty()) {
                        continue;
                    }

                    for (BitTorrentObject trackerModel : trackerList) {
                        String announceListStr = trackerModel.getString(encoding);
                        announceListResult.add(announceListStr);
                    }
                }

                expressionResult.setAnnounceList(announceListResult);
            }

            // --> comment [备注信息]
            String comment = topMap.containsKey(TOP_COMMENT) ? topMap.get(TOP_COMMENT).getString(encoding) : null;
            expressionResult.setComment(comment);

            // --> comment [备注信息 utf-8]
            String commentUtf8 = topMap.containsKey(TOP_COMMENT_UTF8) ? topMap.get(TOP_COMMENT_UTF8).getString(encoding) : null;
            expressionResult.setCommentUtf8(commentUtf8);

            // --> creation date [创建日期]
            long createDate = topMap.containsKey(TOP_CREATION_DATE) ? topMap.get(TOP_CREATION_DATE).getLong() * 1000 : 0;
            expressionResult.setCreateTime(createDate);

            // --> created by [创建者]
            String createBy = topMap.containsKey(TOP_CREATION_BY) ? topMap.get(TOP_CREATION_BY).getString(encoding) : null;
            expressionResult.setCreateAuthor(createBy);

            // --> nodes [节点]
            String nodes = topMap.containsKey(TOP_NODES) ? topMap.get(TOP_NODES).getString(encoding) : null;
            expressionResult.setNodes(nodes);

            // --> info [文件信息]
            Map<String, BitTorrentObject> infoMap = topMap.get(TOP_INFO).getMap();
            expressionResult.setInfoKeySet(infoMap.keySet());

            // --> info --> name
            String infoName = infoMap.containsKey(INFO_NAME) ? infoMap.get(INFO_NAME).getString(encoding) : null;
            expressionResult.setInfoName(infoName);

            // --> info --> name.utf-8
            String infoNameUtf8 = infoMap.containsKey(INFO_NAME_UTF8) ? infoMap.get(INFO_NAME_UTF8).getString(encoding) : null;
            expressionResult.setInfoNameUtf8(infoNameUtf8);

            // --> info --> piece length
            long infoPieceLength = infoMap.containsKey(INFO_PIECE_LENGTH) ? infoMap.get(INFO_PIECE_LENGTH).getLong() : 0;
            expressionResult.setInfoPieceLength(infoPieceLength);

            // --> info --> pieces  [文件的特征信息，该字段比较大，实际上是种子内包含所有的文件段的SHA1的校验值的连接]
            byte[] infoPiece = infoMap.containsKey(INFO_PIECE) ? infoMap.get(INFO_PIECE).getBytes() : null;
            expressionResult.setInfoPiece(infoPiece);

            // --> info --> publisher
            String infoPublisher = infoMap.containsKey(INFO_PUBLISHER) ? infoMap.get(INFO_PUBLISHER).getString(encoding) : null;
            expressionResult.setInfoPublisher(infoPublisher);

            // --> info --> publisher-url
            String infoPublisherUrl = infoMap.containsKey(INFO_PUBLISHER_URL) ? infoMap.get(INFO_PUBLISHER_URL).getString(encoding) : null;
            expressionResult.setInfoPublisherUrl(infoPublisherUrl);

            // --> info --> publisher-url.utf-8
            String infoPublisherUrlUtf8 = infoMap.containsKey(INFO_PUBLISHER_URL_UTF8) ? infoMap.get(INFO_PUBLISHER_URL_UTF8).getString(encoding) : null;
            expressionResult.setInfoPublisherUrlUtf8(infoPublisherUrlUtf8);

            // --> info --> publisher.utf-8
            String infoPublisherUtf8 = infoMap.containsKey(INFO_PUBLISHER_UTF8) ? infoMap.get(INFO_PUBLISHER_UTF8).getString(encoding) : null;
            expressionResult.setInfoPublisherUtf8(infoPublisherUtf8);

            // --> info --> files
            if (infoMap.containsKey(INFO_FILES)) {
                List<BitTorrentObject> fileList = infoMap.get(INFO_FILES).getList();

                List<BitTorrentModel.BitTorrentFileModel> fileModelList = new ArrayList<>();
                for (BitTorrentObject fileObject : fileList) {
                    Map<String, BitTorrentObject> fileMap = fileObject.getMap();
                    BitTorrentModel.BitTorrentFileModel fileModel = new BitTorrentModel.BitTorrentFileModel();

                    if (fileMap.containsKey(FILES_PATH)) {
                        StringBuilder filesPathBuilder = new StringBuilder();
                        for (BitTorrentObject filePathModel : fileMap.get(FILES_PATH).getList()) {
                            filesPathBuilder.append(filePathModel.getString(encoding));
                        }

                        fileModel.setFilePath(filesPathBuilder.toString());
                    }

                    if (fileMap.containsKey(FILES_PATH_UTF8)) {
                        StringBuilder filesPathUtf8Builder = new StringBuilder();
                        for (BitTorrentObject filePathModel : fileMap.get(FILES_PATH_UTF8).getList()) {
                            filesPathUtf8Builder.append(filePathModel.getString(encoding));
                        }

                        fileModel.setFilePathUtf8(filesPathUtf8Builder.toString());
                    }

                    long length = fileMap.containsKey(FILES_LENGTH) ? fileMap.get(FILES_LENGTH).getLong() : 0;
                    fileModel.setLength(length);

                    fileModelList.add(fileModel);
                }
                expressionResult.setFileModelList(fileModelList);
                return expressionResult;
            }
        }
        return null;
    }
}
