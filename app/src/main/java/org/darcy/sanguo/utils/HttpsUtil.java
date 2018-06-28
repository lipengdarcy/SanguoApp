package org.darcy.sanguo.utils;

import android.content.Context;

import org.darcy.sanguo.pojo.NameValuePair;
import org.darcy.sanguo.pojo.SimpleMap;

import org.darcy.sanguo.client.GameAdmin;
import org.darcy.sanguo.constant.InnerConstants;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class HttpsUtil {
    private static InputStream keyInputStream;
    public static String sessionID = null;
    private static SSLSocketFactory socketFactory;

    static {
        keyInputStream = null;
        socketFactory = null;
    }

    private static SSLSocketFactory getFactory(InputStream paramInputStream, String paramString)
            throws GeneralSecurityException, IOException {
        KeyManagerFactory localKeyManagerFactory = KeyManagerFactory.getInstance("X509");
        KeyStore localKeyStore = KeyStore.getInstance("PKCS12");
        localKeyStore.load(paramInputStream, paramString.toCharArray());
        paramInputStream.close();
        localKeyManagerFactory.init(localKeyStore, paramString.toCharArray());
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(localKeyManagerFactory.getKeyManagers(), new TrustManager[]{new DefaultTrustManager()}, null);
        if (paramInputStream != null)
            paramInputStream.close();
        return sslContext.getSocketFactory();
    }

    public static InputStream getInputStream(String paramString) {
        try {
            URL localURL = new URL(paramString);
            URLConnection connection = localURL.openConnection();
            connection.setConnectTimeout(5000);
            connection.connect();
            return connection.getInputStream();
        } catch (MalformedURLException e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), e.toString());
            e.printStackTrace();
        } catch (IOException e2) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), e2.toString());
            e2.printStackTrace();
        }
        return null;
    }

    private static InputStream getKeyInputStream(Context paramContext)
            throws IOException {
        try {
            InputStream inputStream = paramContext.getAssets().open("client.p12");
            return inputStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static long iocopy(InputStream paramInputStream, OutputStream paramOutputStream)
            throws IOException {
        long l = 0L;
        byte[] arrayOfByte = new byte[4096];
        while (true) {
            int i = paramInputStream.read(arrayOfByte);
            if (-1 == i)
                return l;
            paramOutputStream.write(arrayOfByte, 0, i);
            l += i;
        }
    }

    private static String listToString(List<NameValuePair> paramList) {
        StringBuffer stringBuffer = new StringBuffer();
        String str = "";
        for (int i = 0; i < paramList.size(); ++i) {
            NameValuePair localNameValuePair = (NameValuePair) paramList.get(i);
            str = str + "&" + localNameValuePair.getName() + "=" + localNameValuePair.getValue();
            if (str.startsWith("&"))
                str = str.substring(1);
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }


    private static String sign(SimpleMap<String, String> paramSimpleMap) {
        paramSimpleMap.put("gameKey", InnerConstants.APP_KEY);
        return BaseInfoUtil.MD5(JsonUtil.toJson(paramSimpleMap));
    }
}