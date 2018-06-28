package org.darcy.sanguo.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

public class Md5Encrypt {
    private static Logger logger = Logger.getLogger(Md5Encrypt.class.getName());
    private static Md5Encrypt md5 = new Md5Encrypt();

    public static String bytesToHexString(byte[] paramArrayOfByte) {
        StringBuffer localStringBuffer = new StringBuffer(paramArrayOfByte.length);
        for (int i = 0; ; ++i) {
            if (i >= paramArrayOfByte.length)
                return localStringBuffer.toString();
            String str = Integer.toHexString(paramArrayOfByte[i] & 0xFF);
            if (str.length() < 2)
                localStringBuffer.append(0);
            localStringBuffer.append(str.toUpperCase());
        }
    }

    public static String encrypt(String paramString) {
        try {
            byte[] array = paramString.getBytes("UTF-8");
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(array);
            paramString = bytesToHexString(localMessageDigest.digest());
            return paramString;
        } catch (java.security.NoSuchAlgorithmException e1) {
            throw new RuntimeException(e1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Md5Encrypt getInstance() {
        return md5;
    }


    public static void main(String[] paramArrayOfString) {
        //paramArrayOfString = encrypt("liyi@camel4u.comada11112111111111212127.00CNYGGACJDJFOFKPBOOC687EBF887E15515F911E915FAD9EC154");
        System.out.println(paramArrayOfString);
    }

    public static String sign(Map<String, String> paramMap, String paramString) {
        ArrayList localArrayList = new ArrayList(paramMap.keySet());
        Collections.sort(localArrayList);
        StringBuffer localStringBuffer = new StringBuffer();
        int i = 0;
        if (i >= localArrayList.size())
            return encrypt(localStringBuffer.toString().substring(0, localStringBuffer.lastIndexOf("&")) + paramString);
        String str1 = (String) localArrayList.get(i);
        String str2 = (String) paramMap.get(str1);
        if ((str1 == null) || (str1.equalsIgnoreCase("sign")) || (str1.equalsIgnoreCase("sign_type")))
            ;
        ++i;
        localStringBuffer.append(str1 + "=" + str2 + "&");
        return null;
    }


    public static byte unitBytes(byte paramByte1, byte paramByte2) {
        return (byte) ((byte) (Byte.decode("0x" + new String(new byte[]{paramByte1})).byteValue() << 4) ^ Byte.decode("0x" + new String(new byte[]{paramByte2})).byteValue());
    }
}