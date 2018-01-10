package com.lib.http.dev.parse.model;

import com.yline.http.util.LogUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 解析的结果
 *
 * @author yline 2017/12/6 -- 16:09
 * @version 1.0.0
 */
public class BitTorrentModel implements Serializable {
    // 顶部
    private Set<String> topKeySet; // 顶级目录的，key
    private String encoding; // 编码格式
    private String announce; // 主要地址
    private List<String> announceList; // 其它地址
    private String comment; // 备注信息
    private String commentUtf8; // 备注信息 utf-8
    private long createTime; // 创建日期
    private String createAuthor; // 创建者
    private String nodes; // 节点

    // info 层
    private Set<String> infoKeySet; // info目录的，key
    private String infoHash;

    private String infoName;
    private String infoNameUtf8;
    private long infoPieceLength;
    private List<String> infoPieceList; // SHA1值，具有很多个，个数与片段个数相同
    private String infoPublisher;
    private String infoPublisherUrl;
    private String infoPublisherUrlUtf8;
    private String infoPublisherUtf8;

    private List<BitTorrentFileModel> fileModelList;

    public List<BitTorrentFileModel> getFileModelList() {
        return fileModelList;
    }

    public void setFileModelList(List<BitTorrentFileModel> fileModelList) {
        this.fileModelList = fileModelList;
    }

    public Set<String> getInfoKeySet() {
        return infoKeySet;
    }

    public void setInfoKeySet(Set<String> infoKeySet) {
        this.infoKeySet = infoKeySet;
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public String getInfoNameUtf8() {
        return infoNameUtf8;
    }

    public void setInfoNameUtf8(String infoNameUtf8) {
        this.infoNameUtf8 = infoNameUtf8;
    }

    public long getInfoPieceLength() {
        return infoPieceLength;
    }

    public void setInfoPieceLength(long infoPieceLength) {
        this.infoPieceLength = infoPieceLength;
    }

    public List<String> getInfoPieceList() {
        return infoPieceList;
    }

    public void setInfoPieceList(List<String> infoPieceList) {
        this.infoPieceList = infoPieceList;
    }

    public String getInfoPublisher() {
        return infoPublisher;
    }

    public void setInfoPublisher(String infoPublisher) {
        this.infoPublisher = infoPublisher;
    }

    public String getInfoPublisherUrl() {
        return infoPublisherUrl;
    }

    public void setInfoPublisherUrl(String infoPublisherUrl) {
        this.infoPublisherUrl = infoPublisherUrl;
    }

    public String getInfoPublisherUrlUtf8() {
        return infoPublisherUrlUtf8;
    }

    public void setInfoPublisherUrlUtf8(String infoPublisherUrlUtf8) {
        this.infoPublisherUrlUtf8 = infoPublisherUrlUtf8;
    }

    public String getInfoPublisherUtf8() {
        return infoPublisherUtf8;
    }

    public void setInfoPublisherUtf8(String infoPublisherUtf8) {
        this.infoPublisherUtf8 = infoPublisherUtf8;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentUtf8() {
        return commentUtf8;
    }

    public void setCommentUtf8(String commentUtf8) {
        this.commentUtf8 = commentUtf8;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCreateAuthor() {
        return createAuthor;
    }

    public void setCreateAuthor(String createAuthor) {
        this.createAuthor = createAuthor;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public List<String> getAnnounceList() {
        return announceList;
    }

    public void setAnnounceList(List<String> announceList) {
        this.announceList = announceList;
    }

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Set<String> getTopKeySet() {
        return topKeySet;
    }

    public void setTopKeySet(Set<String> topKeySet) {
        this.topKeySet = topKeySet;
    }

    public String getInfoHash() {
        return infoHash;
    }

    public void setInfoHash(String infoHash) {
        this.infoHash = infoHash;
    }

    public String print() {
        StringBuilder printMsgBuilder = new StringBuilder();
        printMsgBuilder.append('\n');
        printMsgBuilder.append(String.format("topKeySet:%s\n", topKeySet));
        printMsgBuilder.append(String.format("encoding:%s \n comment:%s \n commentUtf8:%s \n createTime:%s \n createAuthor:%s \n nodes:%s \n ", encoding, comment, commentUtf8, String.valueOf(createTime), createAuthor, nodes));
        printMsgBuilder.append(String.format("announce:%s\n", announce));
        printMsgBuilder.append(String.format("announceList:%s\n", announceList));
        printMsgBuilder.append(String.format("infoPieceList length:%s\n", (null == infoPieceList ? 0 : infoPieceList.size())));
        printMsgBuilder.append(String.format("infoKeySet:%s\n", infoKeySet));
        printMsgBuilder.append(String.format("name:%s \n nameUtf8:%s \n pieceLength:%s \n publisher:%s \n publisherUrl:%s \n publisherUrlUtf8:%s \n publisherUtf8:%s \n ",
                infoName, infoNameUtf8, String.valueOf(infoPieceLength), infoPublisher, infoPublisherUrl, infoPublisherUrlUtf8, infoPublisherUtf8));
        printMsgBuilder.append(fileModelList);
        String result = printMsgBuilder.toString();
        LogUtil.v("result = " + result);
        return result;
    }

    public static class BitTorrentFileModel implements Serializable {
        private long length;
        private String filePath;
        private String filePathUtf8;

        public long getLength() {
            return length;
        }

        public void setLength(long length) {
            this.length = length;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getFilePathUtf8() {
            return filePathUtf8;
        }

        public void setFilePathUtf8(String filePathUtf8) {
            this.filePathUtf8 = filePathUtf8;
        }

        @Override
        public String toString() {
            return String.format("FileModel{length:%s,filePath:%s,filePathUtf8:%s}\n", length, filePath, filePathUtf8);
        }
    }
}
