package org.darcy.sanguo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

public class HttpConnectionUtil {
    private static HttpClient client = null;

    static {
        BasicHttpParams localBasicHttpParams = new BasicHttpParams();
        HttpProtocolParams.setVersion(localBasicHttpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(localBasicHttpParams, "UTF-8");
        HttpProtocolParams.setUseExpectContinue(localBasicHttpParams, true);
        HttpProtocolParams.setUserAgent(localBasicHttpParams, "mokredit mobile");
        SchemeRegistry localSchemeRegistry = new SchemeRegistry();
        localSchemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        localSchemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        client = new DefaultHttpClient(new ThreadSafeClientConnManager(localBasicHttpParams, localSchemeRegistry), localBasicHttpParams);
        ((AbstractHttpClient) client).setRedirectHandler(new RedirectHandler() {
            public URI getLocationURI(HttpResponse paramHttpResponse, HttpContext paramHttpContext)
                    throws ProtocolException {
                return null;
            }

            public boolean isRedirectRequested(HttpResponse paramHttpResponse, HttpContext paramHttpContext) {
                return false;
            }
        });
    }

    public static String getResponse(String paramString) {
        StringBuffer localStringBuffer = new StringBuffer();
        HttpPost post = new HttpPost(paramString);
        try {
            HttpResponse response = client.execute(post);
            int i = response.getStatusLine().getStatusCode();
            if ((i != 301) && (i != 302) && (i != 303) && (i != 307))
                return null;
            String head = response.getLastHeader("Location").getValue();
            localStringBuffer.append("\n");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

}
