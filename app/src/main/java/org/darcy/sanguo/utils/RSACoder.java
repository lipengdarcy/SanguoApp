package org.darcy.sanguo.utils;

import android.util.Base64;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSACoder {
    public static final String KEY_ALGORITHM = "RSA";
    private static final int KEY_SIZE = 512;
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    private static final String PUBLIC_KEY = "RSAPublicKey";

    public static byte[] decryptByPrivateKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(paramArrayOfByte2);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, privateKey);
        return cipher.doFinal(paramArrayOfByte1);
    }

    public static byte[] decryptByPublicKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(paramArrayOfByte2));
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, publicKey);
        return cipher.doFinal(paramArrayOfByte1);
    }

    public static byte[] encryptByPrivateKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(paramArrayOfByte2);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, privateKey);
        return cipher.doFinal(paramArrayOfByte1);
    }

    public static byte[] encryptByPublicKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(paramArrayOfByte2));
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, publicKey);
        return cipher.doFinal(paramArrayOfByte1);
    }

    public static void main(String[] args) throws Exception {
        PrivateKey privateKey = RSAHelper.getPrivateKey("MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAkcVRrQ7Qr1cnQu/Ke/AcEX4xzmsBfObsn5UoG096VIrWtvhrK1FcMnrl9aIX9XWMBxADaMj8pwL5fRXuPeQfAQIDAQABAkBmecjJWDjsiuBW9ivqpQmWYpiw/Bi3rb97EGh5io39D+yJDWwsjO6AeD+1Nid+003YpSUDtydrQjZotetn/tlhAiEA1gVlI4pvyJwBmFjraq21sHWo6uslDvQJOZ4ikClFGPsCIQCuXOIQfy4mj6KZJob0G/4yt3tKOlxumNQiOi/YEaFfMwIgRv7LKhsGMdnufYchsOWm275sJmGSjX9OgBqBBKlsDnkCIQCAxPfvVv2uXMWodVuVTFce/YJ//znkM9n2rSJIYDzdIQIgGiDJCXaL/hwFhfLvkMXViLE9R5ofwZfnuXeC0f4qRsA=");
        PublicKey publicKey = RSAHelper.getPublicKey("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJHFUa0O0K9XJ0LvynvwHBF+Mc5rAXzm7J+VKBtPelSK1rb4aytRXDJ65fWiF/V1jAcQA2jI/KcC+X0V7j3kHwECAwEAAQ==");
        byte[] array = encryptByPrivateKey("RSA密码交换算法".getBytes(), privateKey.getEncoded());
        System.out.println("加密后的数据：" + Base64.encodeToString(array, 0));
        byte[] array2 = decryptByPublicKey(array, publicKey.getEncoded());
        System.out.println("乙方解密后的数据：" + new String(array2) + "\n\n");
    }
}