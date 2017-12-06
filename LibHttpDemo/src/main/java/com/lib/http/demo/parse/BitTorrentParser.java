package com.lib.http.demo.parse;

import com.lib.http.demo.parse.model.BitTorrentModel;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BitTorrentParser {
    private final InputStream mInput;

    // Zero 未知类型
    // '0'..'9' 表示是byte[]数组也就是字符串类型.
    // 'i' 表示是数字数字.
    // 'l' 表示是列表类型.
    // 'd' 表示是字典类型
    // 'e' 表示是数字,列表或字典的结束字符
    // -1 表示读取到流的结尾
    // 调用getNextIndicator接口获取当前的值
    private int mIndicator = 0;

    BitTorrentParser(InputStream in) {
        mInput = in;
    }

    BitTorrentModel getBitTorrentModel() throws IOException {
        if (getNextIndicator() == -1) {
            return null;
        }

        if (mIndicator >= '0' && mIndicator <= '9') {
            return btParseBytes();     //read string
        } else if (mIndicator == 'i') {
            return btParseNumber();    // read integer
        } else if (mIndicator == 'l') {
            return btParseList();      // read list
        } else if (mIndicator == 'd') {
            return btParseMap();       // read Map
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
    private BitTorrentModel btParseBytes() throws IOException {
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
        return new BitTorrentModel(read(num));
    }

    /**
     * 对应解析bt文件中的数字类型
     * 1. 判断是否是以 i 字符开头
     * 2. 判断要解析的数字是否为负数
     * 3. 读取数字到chars数组中直到遇见字符e
     * 4. 有chars数组生成数字, 并生成BitTorrentModel对象
     * 对应bt文件的 i5242e 数字格式
     */
    private BitTorrentModel btParseNumber() throws IOException {
        int b = getNextIndicator();
        if (b != 'i') {
            throw new IOException("parse number error: not '" + (char) b + "'");
        }
        mIndicator = 0;

        b = read();
        if (b == '0') {
            b = read();
            if (b == 'e') {
                return new BitTorrentModel(BigInteger.ZERO);
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
        return new BitTorrentModel(new BigInteger(s));
    }

    /**
     * 对应解析bt文件中的列表类型
     * 1. 判断是否是以'l'字符开头
     * 2. 调用btParse解析出BitTorrentModel对象, 添加到list中, 直到遇见'e'字符
     * 3. 使用获得的list对象构造BitTorrentModel对象(这时代表了list)
     * 对应bt文件的 l4:spam4:tease 格式
     * 如果是 l4:spam4:tease 那么 list对象包含两个BitTorrentModel对象, 分别为 spam 和 tease 字符串
     */
    private BitTorrentModel btParseList() throws IOException {
        int b = getNextIndicator();
        if (b != 'l') {
            throw new IOException("Expected 'l', not '" + (char) b + "'");
        }

        mIndicator = 0;

        List<BitTorrentModel> result = new ArrayList<>();
        b = getNextIndicator();
        while (b != 'e') {
            result.add(getBitTorrentModel());
            b = getNextIndicator();
        }
        mIndicator = 0;

        return new BitTorrentModel(result);
    }

    /**
     * 对应解析bt文件中的字典类型
     * 1. 判断是否是以'd'字符开头
     * 2. 调用btParse解析获得key与value, 添加到Map中, 直到遇见'e'字符
     * 3. 使用获得的Map对象构造BitTorrentModel对象(这时代表了Map)
     * 对应bt文件的 <d> <key String> <value content> <e>格式
     */
    private BitTorrentModel btParseMap() throws IOException {
        int b = getNextIndicator();
        if (b != 'd') {
            throw new IOException("Expected 'd', not '" + (char) b + "'");
        }
        mIndicator = 0;

        Map<String, BitTorrentModel> result = new HashMap<>();
        b = getNextIndicator();
        while (b != 'e') {
            // Dictionary keys are always strings
            String key = getBitTorrentModel().getString();
            BitTorrentModel value = getBitTorrentModel();
            result.put(key, value);

            b = getNextIndicator();
        }
        mIndicator = 0;
        return new BitTorrentModel(result);
    }

    private int getNextIndicator() throws IOException {
        if (mIndicator == 0) {
            mIndicator = mInput.read();
        }
        return mIndicator;
    }

    /**
     * 从输入流读取一个数据
     */
    private int read() throws IOException {
        int b = mInput.read();
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
            int i = mInput.read(result, read, length - read);
            if (i == -1) {
                throw new EOFException();
            }
            read += i;
        }

        return result;
    }
}
