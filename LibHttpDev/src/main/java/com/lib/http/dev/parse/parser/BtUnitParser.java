package com.lib.http.dev.parse.parser;

import java.io.IOException;
import java.io.InputStream;

/**
 * BitTorrent 协议，解析单条
 * 1，字节串 string、bytes
 * 2，整数 int
 * 3，List
 * 4，Map
 *
 * @author yline 2017/12/19 -- 14:20
 * @version 1.0.0
 */
public class BtUnitParser {
    /**
     * 解析整形
     * 1) i0e
     * 2) i-3e
     * 3) i31e
     *
     * @param inputStream 字符流
     * @throws IOException IO异常
     */
    public int parseInteger(InputStream inputStream, int readValue) throws IOException {
        if ('i' == readValue) {
            int nextValue = inputStream.read();
            if ('0' == nextValue) {
                nextValue = inputStream.read();
                if ('e' == nextValue) {
                    return 0;
                } else {
                    throw new NumberFormatException("parse int, not accord to i0e");
                }
            }

            char[] chars = new char[255];
            int offset = 0;

            // 解析负数
            if ('-' == nextValue) {
                nextValue = inputStream.read();
                if ('0' == nextValue) {
                    throw new NumberFormatException("parse int, not accord to i-3e");
                }

                chars[offset] = '-';
                offset++;

                if ('9' < nextValue || '1' > nextValue) {
                    throw new NumberFormatException("parse int, not accord to i-4e");
                } else {
                    chars[offset] = (char) nextValue;
                    offset++;
                }
            } else {
                if ('9' < nextValue || '1' > nextValue) {
                    throw new NumberFormatException("parse int, not accord to i3e");
                } else {
                    chars[offset] = (char) nextValue;
                    offset++;
                }
            }

            // 解析第二位开始的，正常逻辑
            nextValue = inputStream.read();
            while (!('9' < nextValue || '1' > nextValue)) {
                chars[offset] = (char) nextValue;
                offset++;

                nextValue = inputStream.read();
            }

            // 结束校验
            if ('e' != nextValue) {
                throw new NumberFormatException("parse int, endChar not accord to e");
            } else {
                return Integer.parseInt(new String(chars, 0, offset));
            }
        } else {
            throw new NumberFormatException("parse int, startChar not accord to i");
        }
    }

    /**
     * 解析，字节串
     * 1）5:yline
     *
     * @param inputStream 字符流
     * @throws IOException IO异常
     */
    public byte[] parseByte(InputStream inputStream, int readValue) throws IOException {
        if (('9' < readValue || '1' > readValue)) {
            throw new NumberFormatException("parse byte, startChar not accord to 5");
        } else {
            int length = readValue - '0';

            int nextValue = inputStream.read();
            while (!('9' < nextValue || '1' > nextValue)) {
                length = length * 10 + (nextValue - '0');

                nextValue = inputStream.read();
            }

            // 结束校验
            if (':' != nextValue) {
                throw new NumberFormatException("parse byte, endChar not accord to e");
            } else {
                byte[] resultBytes = new byte[length];

                int len;
                int read = 0;
                while (read < length) {
                    len = inputStream.read(resultBytes, read, length - read);
                    if (-1 == len) {
                        break;
                    }
                    read += len;
                }

                return resultBytes;
            }
        }
    }

    /**
     * 解析，列表
     * 1）l<编码值>e  --> l4:spam4:eggse
     */
//    public List parseList(InputStream inputStream, int readValue) throws IOException {
//        if ('l' != readValue) {
//            throw new NumberFormatException("parse list, startChar not accord to l");
//        } else {
//            List result = new ArrayList();
//
//            int nextValue = inputStream.read();
////            while ()
//        }
//    }
}
