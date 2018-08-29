package com.ckj.projects;

import java.security.MessageDigest;

/**
 * created by ChenKaiJu on 2018/8/29  12:15
 */
public class Md5Utils {

    public static String md5(String text, String key) throws Exception {
        byte[] bytes = (text + key).getBytes();
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(bytes);
        bytes = messageDigest.digest();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bytes.length; ++i) {
            if ((bytes[i] & 255) < 16) {
                sb.append("0");
            }
            sb.append(Long.toString((long)(bytes[i] & 255), 16));
        }
        return sb.toString().toLowerCase();
    }

    public static boolean verify(String text, String key, String md5) throws Exception {
        String md5Text = md5(text, key);
        return md5Text.equalsIgnoreCase(md5);
    }
}
