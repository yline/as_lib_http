package com.lib.http.demo.parse.model;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 单个 BitTorrent 对象，它是解析帮助类
 *
 * @author yline 2017/12/6 -- 16:06
 * @version 1.0.0
 */
class BitTorrentObject {
    private final Object mValue;

    public BitTorrentObject(byte[] value) {
        this.mValue = value;
    }

    public BitTorrentObject(String value) throws UnsupportedEncodingException {
        this.mValue = value.getBytes("utf-8");
    }

    public BitTorrentObject(String value, String encode) throws UnsupportedEncodingException {
        this.mValue = value.getBytes(encode);
    }

    public BitTorrentObject(int value) {
        this.mValue = value;
    }

    public BitTorrentObject(long value) {
        this.mValue = value;
    }

    public BitTorrentObject(Number value) {
        this.mValue = value;
    }

    public BitTorrentObject(List<BitTorrentObject> value) {
        this.mValue = value;
    }

    public BitTorrentObject(Map<String, BitTorrentObject> value) {
        this.mValue = value;
    }

    public Object getValue() {
        return mValue;
    }

    public byte[] getBytes() throws ClassCastException {
        return byte[].class.cast(mValue);
    }

    public String getString() throws UnsupportedEncodingException {
        return new String(getBytes(), "utf-8");
    }

    public String getString(String encode) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(encode)) {
            return new String(getBytes(), "utf-8");
        }
        return new String(getBytes(), encode);
    }

    public Number getNumber() throws ClassCastException {
        return Number.class.cast(mValue);
    }

    public int getInt() throws ClassCastException {
        if (mValue instanceof BigInteger) {
            return ((BigInteger) mValue).intValue();
        }
        return int.class.cast(mValue);
    }

    public short getShort() throws ClassCastException {
        return short.class.cast(mValue);
    }

    public long getLong() throws ClassCastException {
        if (mValue instanceof BigInteger) {
            return ((BigInteger) mValue).longValue();
        }
        return (long) mValue;
    }

    public List<BitTorrentObject> getList() throws ClassCastException {
        return (ArrayList<BitTorrentObject>) mValue;
    }

    public Map<String, BitTorrentObject> getMap() throws ClassCastException {
        return (Map<String, BitTorrentObject>) mValue;
    }
}
