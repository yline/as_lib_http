package com.lib.http.dev.parse.model;

import com.lib.http.dev.parse.ParseActivity;
import com.yline.http.util.LogUtil;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 负责将，mInputStream 转换成 BitTorrentObject
 *
 * @author yline 2017/12/6 -- 16:04
 * @version 1.0.0
 */
class BitTorrentParser {
    private final InputStream mInputStream;

    // Zero 未知类型
    // '0'..'9' 表示是byte[]数组也就是字符串类型.
    // 'i' 表示是数字数字.
    // 'l' 表示是列表类型.
    // 'd' 表示是字典类型
    // 'e' 表示是数字,列表或字典的结束字符
    // -1 表示读取到流的结尾
    // 调用getNextIndicator接口获取当前的值
    private int mIndicator = 0;

    private BitTorrentParser(InputStream in) {
        mInputStream = in;
    }

    static BitTorrentObject getParseResult(InputStream inputStream) throws IOException {
        return new BitTorrentParser(inputStream).getBitTorrentModel();
    }

    /**
     * 通过 递归回调的方式，进行一次性的解析
     *
     * @return BitTorrent文件所有数据
     * @throws IOException 解析异常
     */
    private BitTorrentObject getBitTorrentModel() throws IOException {
        if (getNextIndicator() == -1) {
            return null;
        }

        if (mIndicator >= '0' && mIndicator <= '9') {
            return getParseBytes();     //read string
        } else if (mIndicator == 'i') {
            return getParseNumber();    // read integer
        } else if (mIndicator == 'l') {
            return getParseList();      // read list
        } else if (mIndicator == 'd') {
            return getParseMap();       // read Map
        } else {
            throw new IOException("Unknown indicator '" + mIndicator + "'");
        }
    }

    /**
     * 对应解析bt文件的字符串类型
     * 1. 解析字符串的长度
     * 2. 根据解析的长度从输入流中读取指定长度的字符
     * 3. 根据读取到的字符数组构建BitTorrentModel对象
     * 对应bt文件的 4:ptgh 字符串格式
     */
    private BitTorrentObject getParseBytes() throws IOException {
        int b = getNextIndicator();
        int num = b - '0';
        if (num < 0 || num > 9) {
            throw new IOException("parse bytes(String) error: not '" + (char) b + "'");
        }
        mIndicator = 0;

        b = read();
        int i = b - '0';
        while (i >= 0 && i <= 9) {
            num = num * 10 + i;
            b = read();
            i = b - '0';
        }

        if (b != ':') {
            throw new IOException("Colon error: not '" + (char) b + "'");
        }
        return new BitTorrentObject(read(num));
    }

    /**
     * 对应解析bt文件中的数字类型
     * 1. 判断是否是以 i 字符开头
     * 2. 判断要解析的数字是否为负数
     * 3. 读取数字到chars数组中直到遇见字符e
     * 4. 有chars数组生成数字, 并生成BitTorrentModel对象
     * 对应bt文件的 i5242e 数字格式
     */
    private BitTorrentObject getParseNumber() throws IOException {
        int b = getNextIndicator();
        if (b != 'i') {
            throw new IOException("parse number error: not '" + (char) b + "'");
        }
        mIndicator = 0;

        b = read();
        if (b == '0') {
            b = read();
            if (b == 'e') {
                return new BitTorrentObject(BigInteger.ZERO);
            } else {
                throw new IOException("'e' expected after zero," + " not '" + (char) b + "'");
            }
        }

        // don't support more than 255 char big integers
        char[] chars = new char[255];
        int offset = 0;

        // to determine whether the number is negative
        if (b == '-') {
            b = read();
            if (b == '0') {
                throw new IOException("Negative zero not allowed");
            }
            chars[offset] = '-';
            offset++;
        }

        if (b < '1' || b > '9') {
            throw new IOException("Invalid Integer start '" + (char) b + "'");
        }
        chars[offset] = (char) b;
        offset++;

        // start read the number, save in chars
        b = read();
        int i = b - '0';
        while (i >= 0 && i <= 9) {
            chars[offset] = (char) b;
            offset++;
            b = read();
            i = b - '0';
        }

        if (b != 'e') {
            throw new IOException("Integer should end with 'e'");
        }

        String s = new String(chars, 0, offset);
        return new BitTorrentObject(new BigInteger(s));
    }

    /**
     * 对应解析bt文件中的列表类型
     * 1. 判断是否是以'l'字符开头
     * 2. 调用btParse解析出BitTorrentModel对象, 添加到list中, 直到遇见'e'字符
     * 3. 使用获得的list对象构造BitTorrentModel对象(这时代表了list)
     * 对应bt文件的 l4:spam4:tease 格式
     * 如果是 l4:spam4:tease 那么 list对象包含两个BitTorrentModel对象, 分别为 spam 和 tease 字符串
     */
    private BitTorrentObject getParseList() throws IOException {
        int b = getNextIndicator();
        if (b != 'l') {
            throw new IOException("Expected 'l', not '" + (char) b + "'");
        }
        mIndicator = 0;

        List<BitTorrentObject> result = new ArrayList<>();
        b = getNextIndicator();
        while (b != 'e') {
            result.add(getBitTorrentModel());
            b = getNextIndicator();
        }
        mIndicator = 0;

        return new BitTorrentObject(result);
    }

    /**
     * 对应解析bt文件中的字典类型
     * 1. 判断是否是以'd'字符开头
     * 2. 调用btParse解析获得key与value, 添加到Map中, 直到遇见'e'字符
     * 3. 使用获得的Map对象构造BitTorrentModel对象(这时代表了Map)
     * 对应bt文件的 <d> <key String> <value content> <e>格式
     */
    private BitTorrentObject getParseMap() throws IOException {
        int b = getNextIndicator();
        if (b != 'd') {
            throw new IOException("Expected 'd', not '" + (char) b + "'");
        }
        mIndicator = 0;

        Map<String, BitTorrentObject> result = new HashMap<>();
        b = getNextIndicator();
        while (b != 'e') {
            // Dictionary keys are always strings
            BitTorrentObject mapModel = getBitTorrentModel();
            if (null != mapModel) {
                String key = mapModel.getString();

                BitTorrentObject value = getBitTorrentModel();

                if ("info".equals(key)) {
                    try {
                        String sha = ParseActivity.getSHA1(value.getBytes());
                        LogUtil.v("sha : " + sha);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
                result.put(key, value);
                b = getNextIndicator();
            }
        }
        mIndicator = 0;
        return new BitTorrentObject(result);
    }

    private int getNextIndicator() throws IOException {
        if (mIndicator == 0) {
            mIndicator = mInputStream.read();
        }
        return mIndicator;
    }

    /**
     * 从输入流读取一个数据
     */
    private int read() throws IOException {
        int b = mInputStream.read();
        if (b == -1) {
            throw new EOFException();
        }
        return b;
    }

    /**
     * 根据指定长度, 从输入流读取字符数组
     */
    private byte[] read(int length) throws IOException {
        byte[] result = new byte[length];

        int read = 0;
        while (read < length) {
            int i = mInputStream.read(result, read, length - read);
            if (i == -1) {
                throw new EOFException();
            }
            read += i;
        }

        return result;
    }
}
