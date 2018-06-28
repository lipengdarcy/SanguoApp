package org.darcy.sanguo.utils;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import java.util.ArrayList;
import java.util.List;

public class HttpFetcher {

    private List<BasicNameValuePair> paramBuilder(String serviceId, String data, String version, boolean paramBoolean) {
        if (data == null)
            return null;
        ArrayList localArrayList = new ArrayList();
        StringBuilder localStringBuilder = new StringBuilder();
        try {

            if (serviceId != null) {
                BasicNameValuePair localBasicNameValuePair = new BasicNameValuePair("serviceId", serviceId);
                localArrayList.add(localBasicNameValuePair);
                localStringBuilder.append("serviceId=");
                localStringBuilder.append(serviceId);
                localStringBuilder.append("&");
            }
            if (version != null) {
                BasicNameValuePair localBasicNameValuePair = new BasicNameValuePair("version", version);
                localArrayList.add(localBasicNameValuePair);
                localStringBuilder.append("version=");
                localStringBuilder.append(version);
                localStringBuilder.append("&");
            }
            BasicNameValuePair localBasicNameValuePair = new BasicNameValuePair("data", data);
            localArrayList.add(localBasicNameValuePair);
            localStringBuilder.append("data=");
            localStringBuilder.append(data);
            localStringBuilder.append("02000016-0010-0080-8000-10CA006D2CA5");
            Log.i("ALP", localArrayList.toString());
        } catch (Exception paramString1) {
            Log.i("ALP", paramString1.getMessage());
            paramString1 = null;
        }
        return localArrayList;
    }

    public HttpClient getHttpClient() {
        BasicHttpParams localBasicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 5000);
        HttpConnectionParams.setSoTimeout(localBasicHttpParams, 5000);
        return new DefaultHttpClient(localBasicHttpParams);
    }

    public HttpResponse uploadCollectedData(Context paramContext, String uri, String paramString2, String paramString3, String paramString4, boolean paramBoolean) {
        paramContext = null;
        if (paramString3 == null)
            return null;
        try {
            HttpPost localHttpPost = new HttpPost(uri);
            List<BasicNameValuePair> list = paramBuilder(paramString2, paramString3, paramString4, paramBoolean);
            if (list == null)
                return null;
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
            localHttpPost.setEntity(encodedFormEntity);
            HttpResponse response = getHttpClient().execute(localHttpPost);
            return response;
        } catch (Exception e) {
            Log.i("ALP", e.getMessage());
        }
        return null;
    }
}