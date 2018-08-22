package com.yline.http.json;

import com.alibaba.fastjson.JSON;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Json解析，隔离层
 *
 * @author yline 2018/7/26 -- 13:57
 */
public class FastJson {
    /**
     * 对象 转 String
     * @param object 支持{序列化实体类、HashMap、Set、List、ArrayMap}
     * @return 字符串
     */
    public static String toString(Object object){
        return JSON.toJSONString(object);
    }

    /**
     * 数据流 转 实体类
     * @param <T> 实体类结构
     * @return 实体类对象
     */
    public static <T> T toClass(InputStream inputStream, Class<? extends T> clazz){
        try {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                bao.write(buffer, 0, len);
            }

            return toClass(new String(bao.toByteArray(), Charset.forName("utf-8")), clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 数据流 转 实体类
     * @param <T> 实体类结构
     * @return 实体类对象
     */
    public static <T> T toClass(String jsonStr, Class<? extends T> clazz){
        return JSON.parseObject(jsonStr, clazz);
    }

    /**
     * 数据流 转 实体类
     * @param <T> 实体类结构
     * @return 实体类对象
     */
    public static <T> T toClass(String jsonStr, Type type){
        return JSON.parseObject(jsonStr, type);
    }

    /**
     * json字符串，转成，数组
     *
     * @param jsonStr json字符串
     * @param clazz   对象类型
     * @param <T>     对象类型
     * @return 指定类型的对象 数组
     */
    public static <T> List<T> toClassArray(String jsonStr, Class<T> clazz) {
        return JSON.parseArray(jsonStr, clazz);
    }
}
