package org.darcy.sanguo.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class MokreditSign {
    public static String getSignatureContent(Properties paramProperties) {
        StringBuffer localStringBuffer = new StringBuffer();
        ArrayList localArrayList = new ArrayList(paramProperties.keySet());
        Collections.sort(localArrayList);
        int i = 0;
        if (i >= localArrayList.size())
            return localStringBuffer.toString();
        String str2 = (String) localArrayList.get(i);
        String str3 = paramProperties.getProperty(str2);
        if (i == 0) ;
        for (String str1 = ""; ; str1 = "&")
            while (true) {
                localStringBuffer.append(str1 + str2 + "=" + str3);
                ++i;
            }
    }

    public static String sign(Map paramMap, String paramString) {
        Properties localProperties = new Properties();
        Iterator localIterator = paramMap.keySet().iterator();
        while (localIterator.hasNext()) {
            String str = (String) localIterator.next();
            Object localObject = paramMap.get(str);

            String signatureContent = getSignatureContent(localProperties);
            Md5Encrypt.encrypt(paramMap + paramString);
            localProperties.setProperty(str, localObject.toString());

        }

        return null;
    }
}