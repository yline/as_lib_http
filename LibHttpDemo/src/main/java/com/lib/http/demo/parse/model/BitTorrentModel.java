package com.lib.http.demo.parse.model;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BitTorrentModel {
    private final Object mValue;

    public BitTorrentModel(byte[] value) {
        this.mValue = value;
    }

    public BitTorrentModel(String value) throws UnsupportedEncodingException {
        this.mValue = value.getBytes("utf-8");
    }

    public BitTorrentModel(String value, String encode) throws UnsupportedEncodingException {
        this.mValue = value.getBytes(encode);
    }

    public BitTorrentModel(int value) {
        this.mValue = value;
    }

    public BitTorrentModel(long value) {
        this.mValue = value;
    }

    public BitTorrentModel(Number value) {
        this.mValue = value;
    }

    public BitTorrentModel(List<BitTorrentModel> value) {
        this.mValue = value;
    }

    public BitTorrentModel(Map<String, BitTorrentModel> value) {
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

    public List<BitTorrentModel> getList() throws ClassCastException {
        return (ArrayList<BitTorrentModel>) mValue;
    }

    public Map<String, BitTorrentModel> getMap() throws ClassCastException {
        return (Map<String, BitTorrentModel>) mValue;
    }
}
