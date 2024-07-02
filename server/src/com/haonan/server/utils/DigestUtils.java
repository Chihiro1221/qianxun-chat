package com.haonan.server.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要计算类
 *
 * @author wanghaonan
 */
public class DigestUtils {
    public static String getMd5(String str) {
        MessageDigest instance = null;
        try {
            instance = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        instance.update(bytes);

        return bytesToHex(instance.digest());
    }


    //bytesToHex 方法用于将字节数组转换为十六进制字符串。
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
