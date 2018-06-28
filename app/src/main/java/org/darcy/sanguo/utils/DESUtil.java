package org.darcy.sanguo.utils;

import android.content.Context;
import android.util.Log;


import org.darcy.sanguo.client.GameAdmin;

import java.io.File;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESUtil {
    private static final String KEY_FILENAME = "DESUtilKey";
    private byte[] DESIV = {18, 52, 86, 120, -112, -85, -51, -17};
    private byte[] DESkey;
    private AlgorithmParameterSpec iv;
    private Key key;

    public DESUtil(Context paramContext) {
        try {
            this.DESkey = "abcdefghijk".getBytes("UTF-8");
            DESKeySpec localDESKeySpec = new javax.crypto.spec.DESKeySpec(DESkey);
            AlgorithmParameterSpec localObject = new javax.crypto.spec.IvParameterSpec(DESIV);
            this.iv =  localObject;
            File file = new File(paramContext.getFilesDir(), "DESUtilKey");

            if (file.exists()) {
                logD("Key文件已存在，即将从文件中读取");
                this.key = ((Key) ChujianFileUtil.readObject(file.getAbsolutePath()));
                return;
            }
            logD("Key不存在，即将随机生成");
            this.key = SecretKeyFactory.getInstance("DES").generateSecret(localDESKeySpec);
            ChujianFileUtil.saveObject(((File) localObject).getAbsolutePath(), this.key);
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), paramContext.toString());
            e.printStackTrace();
        }
    }

    private void logD(String paramString) {
        if (!(GameAdmin.isConsoleLog()))
            return;
        Log.d("DESUtil", paramString);
    }

    public byte[] decrypt(byte[] paramArrayOfByte) {
        try {
            Cipher localCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            localCipher.init(2, this.key, this.iv);
            paramArrayOfByte = localCipher.doFinal(paramArrayOfByte);
            return paramArrayOfByte;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    public byte[] encrypt(byte[] paramArrayOfByte) {
        try {
            Cipher localCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            localCipher.init(1, this.key, this.iv);
            paramArrayOfByte = localCipher.doFinal(paramArrayOfByte);
            return paramArrayOfByte;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), paramArrayOfByte.toString());
            e.printStackTrace();
        } finally {
        }
        return null;
    }
}