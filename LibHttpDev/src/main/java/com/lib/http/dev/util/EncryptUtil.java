package com.lib.http.dev.util;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密算法
 *
 * @author yline 2018/1/11 -- 19:33
 * @version 1.0.0
 */
public class EncryptUtil {
    public static final String MD5 = "MD5";
    public static final String SHA1 = "SHA1";
    public static final String SHA256 = "SHA256";
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'}; // byte 解析成 hex

    /**
     * 获取Assets文件的  编码值
     *
     * @param context   上下文
     * @param fileName  文件名
     * @param algorithm 加密算法 {EncryptUtil.MD5, EncryptUtil.SHA1}
     * @return 返回结果, null if IOException happened
     */
    public static String getAssetsEncrypt(Context context, String fileName, String algorithm) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            ByteArrayOutputStream byteArrayOutputStream = inputSteam2ByteArrayOutputSteam(inputStream);

            String result = getMessageDigest(byteArrayOutputStream.toByteArray(), algorithm);
            com.yline.utils.LogUtil.v("result = " + result);
            return result;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 获取字符串的 编码值
     *
     * @param content   编辑内容
     * @param algorithm 加密算法 {EncryptUtil.MD5, EncryptUtil.SHA1}
     * @return
     */
    public static String getStringEncrypt(@NonNull String content, String algorithm) {
        String result = getMessageDigest(content.getBytes(), algorithm);
        com.yline.utils.LogUtil.v("result = " + result);
        return result;
    }

    /**
     * 获取 内容 编码
     *
     * @return 编码结果; null if exception happened
     */
    public static String getMessageDigest(@NonNull byte[] bytes, String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(bytes);

            byte[] digestBytes = messageDigest.digest();
            return byte2HexString(digestBytes, 0, digestBytes.length);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String byte2HexString(@NonNull byte[] bytes, int start, int length) {
        if (bytes.length < length) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = start; i < start + length; i++) {
            stringBuilder.append(DIGITS[(bytes[i] & 0xf0) >> 4]); // 高4位
            stringBuilder.append(DIGITS[(bytes[i] & 0x0f)]); // 低4位
        }
        return stringBuilder.toString();
    }

    private static ByteArrayOutputStream inputSteam2ByteArrayOutputSteam(InputStream in) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int length;
        while ((length = in.read()) != -1) {
            byteArrayOutputStream.write(length);
        }
        return byteArrayOutputStream;
    }
}
