package com.lx.treasure.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Auther: Lx
 * @Date: 2020/6/25 13:38
 * @Description: 流处理工具
 */
public class StreamUtils {

    /**
     * stream 转 string
     * @param inputStream 输入流
     * @return 字符
     */
    public static String streamToString(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String tmp;
        while ((tmp = br.readLine()) != null) {
            sb.append(tmp).append("\n");
        }
        br.close();
        return sb.toString();
    }

    /**
     * stream 转 string
     * @param is 输入流
     * @param length 获取流中数据的长度
     * @return string
     * @throws IOException 读取异常
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String streamToString(InputStream is, int length) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        char[] tmpChars = new char[length];
        br.read(tmpChars);
        br.close();
        return new String(tmpChars);
    }
}
