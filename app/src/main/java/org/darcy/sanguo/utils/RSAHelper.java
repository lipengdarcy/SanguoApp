package org.darcy.sanguo.utils;

import android.annotation.SuppressLint;
import android.util.Base64;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

@SuppressLint({"TrulyRandom"})
public class RSAHelper {
    public static String getKeyString(Key paramKey)
            throws Exception {
        return Base64.encodeToString(paramKey.getEncoded(), 0);
    }

    public static PrivateKey getPrivateKey(String paramString)
            throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(paramString.getBytes(), 0));
        return KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);
    }

    public static PublicKey getPublicKey(String paramString)
            throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(paramString.getBytes(), 0));
        return KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);
    }

    public static void main(String[] args) throws Exception {
        //rsa公钥私钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        String publicKey = getKeyString((Key) rsaPublicKey);
        System.out.println("public:\n" + publicKey);
        String privateKey = getKeyString(rsaPrivateKey);
        System.out.println("private:\n" + privateKey);

        Cipher cipher = Cipher.getInstance("RSA");
        byte[] arrayOfByte = "我们都很好！邮件：@sina.com".getBytes();
        cipher.init(1, rsaPublicKey);
        arrayOfByte = cipher.doFinal(arrayOfByte);
        PublicKey publicKey1 = getPublicKey(publicKey);
        PrivateKey privateKey1 = getPrivateKey(privateKey);
        cipher.init(2, publicKey1);
        byte[] array = cipher.doFinal(arrayOfByte);
        String publicKey2 = getKeyString(publicKey1);
        System.out.println("public:\n" + publicKey2);
        String privateKey2 = getKeyString(privateKey1);
        System.out.println("private:\n" + privateKey2);
        System.out.println(new String(array));
    }
}