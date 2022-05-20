package com.arch.demo.network_api.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author pqc
 */
public class MD5Utils {
    private static final String key="a8r7wyr!$#@#^$&^*)(*&^5qW_QW_EWQE_W@!##!$&^$#^#";
    public static String encrypt(String source) {
        source+=key;
        return encodeMd5(source.getBytes());
    }
    public static String encryptWithoutKey(String source) {
        source = key + source;
        return encodeMd5(source.getBytes());
    }
    private static String encodeMd5(byte[] source) {
        try {
            return encodeHex(MessageDigest.getInstance("MD5").digest(source));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private static String encodeHex(byte[] bytes) {
        StringBuffer buffer = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buffer.append("0");
            }
            buffer.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buffer.toString();
    }


}
