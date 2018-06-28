package org.darcy.sanguo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import org.darcy.sanguo.client.GameAdmin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ChujianFileUtil {
    public static final String MAPNAMEFLAG_STRING = "name";
    public static final String MAPPASSWORDFLAG_STRING = "password";

    public static byte[] decryptFileToBytes(Context paramContext, String fileName) {
        File file = new File(paramContext.getFilesDir(), fileName);
        return new DESUtil(paramContext).decrypt(readBytesFromFile(file.getAbsolutePath()));
    }

    public static void encryptBytesToFile(Context paramContext, byte[] paramArrayOfByte, String paramString)
            throws IOException {
        InputStream localInputStream = paramContext.getAssets().open("client.p12");
        paramArrayOfByte = new byte[localInputStream.available()];
        localInputStream.read(paramArrayOfByte);
        File file = new File(paramContext.getFilesDir(), paramString);
        byte[] array = new DESUtil(paramContext).encrypt(paramArrayOfByte);
        saveBytesToFile(file.getAbsolutePath(), array);
    }

    public static Map<String, String> getUserLoginMap(Activity paramActivity) {
        SharedPreferences localObject = paramActivity.getSharedPreferences("chujian_login_user", 0);
        Map map = new HashMap();
        String username = localObject.getString("name", "");
        String password = localObject.getString("password", "");
        password = new String(Base64.decode(password, 0));
        map.put("name", username);
        map.put("password", password);
        return map;
    }

    public static byte[] readBytesFromFile(String fileName) {
        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bas = new ByteArrayOutputStream();
            byte[] arrayOfByte = new byte[1024];
            int i = fis.read(arrayOfByte);
            if (i <= 0) {
                fis.close();
                return arrayOfByte;
            }
            bas.write(arrayOfByte, 0, i);
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), e.toString());
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] readBytesFromInputStream(InputStream paramInputStream) {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        byte[] arrayOfByte = new byte[1024];
        try {
            int i = paramInputStream.read(arrayOfByte);
            if (i <= 0)
                return localByteArrayOutputStream.toByteArray();
            localByteArrayOutputStream.write(arrayOfByte, 0, i);
        } catch (IOException e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), paramInputStream.toString());
            e.printStackTrace();
        }
        return null;
    }

    @Deprecated
    public static String readFile(Context paramContext) {
        try {
            FileInputStream localFileInputStream = paramContext.openFileInput("users");
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            StringBuffer sb = new StringBuffer();
            if (localFileInputStream.read(buffer) == -1) {
                sb.append(localByteArrayOutputStream.toString());
            }
            localByteArrayOutputStream.write(buffer, 0, sb.length());
        } catch (FileNotFoundException e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), paramContext.toString());
            e.printStackTrace();
        } catch (IOException e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), paramContext.toString());
            e.printStackTrace();
        }
        return null;
    }

    public static Object readObject(String paramString) {
        try {
            File localFile = new File(paramString);
            FileInputStream localFileInputStream = new FileInputStream(localFile);
            ObjectInputStream ois = new ObjectInputStream(localFileInputStream);
            Object obj = ois.readObject();
            return obj;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), paramString.toString());
            e.printStackTrace();
        }
        return null;
    }

    public static void saveBytesToFile(String paramString, byte[] paramArrayOfByte) {
        File localFile = new File(paramString);
        try {
            FileOutputStream fos = new FileOutputStream(localFile);
            fos.write(paramArrayOfByte);
            fos.close();
            return;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), paramString.toString());
            e.printStackTrace();
        }
    }

    @Deprecated
    public static void saveLoginState(Activity paramActivity, boolean paramBoolean) {
        paramActivity.getSharedPreferences("login", 0).edit();
    }


    public static void saveObject(String fileName, Object paramObject) {
        File file = new File(fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(fileOutputStream);
            localObjectOutputStream.writeObject(paramObject);
            localObjectOutputStream.close();
            return;
        } catch (Exception paramString) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), paramString.toString());
            paramString.printStackTrace();
        }
    }

    public static void saveUserInfo(Activity paramActivity, String paramString1, String paramString2) {
        SharedPreferences.Editor editor = paramActivity.getSharedPreferences("chujian_login_user", 0).edit();
        paramString1 = new String(Base64.encode(paramString1.getBytes(), 0));
        paramString2 = new String(Base64.encode(paramString2.getBytes(), 0));
        editor.putString("name", paramString1);
        editor.putString("password", paramString2);
        editor.commit();
    }


    @Deprecated
    public static void saveUsers(Activity paramActivity, String paramString) {
        try {
            FileOutputStream fileOutputStream = paramActivity.openFileOutput("users", 0);
            fileOutputStream.write(paramString.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            return;
        } catch (FileNotFoundException e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), e.toString());
            e.printStackTrace();
        } catch (IOException e1) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), e1.toString());
            e1.printStackTrace();
        }
    }
}