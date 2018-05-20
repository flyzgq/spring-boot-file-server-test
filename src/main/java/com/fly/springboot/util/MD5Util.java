package com.fly.springboot.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author fly
 * @date 2018/5/19 20:18
 * @description
 **/
public class MD5Util {

    /**
     * 获取输入流的MD5值
     * @param is
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static String getMD5(InputStream is) throws NoSuchAlgorithmException, IOException {
        StringBuffer md5 = new StringBuffer();
        MessageDigest md5Instance = MessageDigest.getInstance("MD5");
        byte[] dataBytes = new byte[1024];
        int nread = 0;
        while ((nread = is.read(dataBytes))!= -1){
            md5Instance.update(dataBytes,0, nread);
        };

        byte[] md5Bytes = md5Instance.digest();
        for (int i = 0; i <md5Bytes.length ; i++) {
            md5.append(Integer.toString((md5Bytes[i] & 0xff) + 0x100,16).substring(1));
        }
        return md5.toString();
    }
}
