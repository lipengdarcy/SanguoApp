package org.darcy.sanguo.utils;

import org.darcy.sanguo.pojo.NameValuePair;
import org.darcy.sanguo.pojo.SimpleMap;

import org.darcy.sanguo.exception.SysException;

public class ParamUtil {
    public static void addParam(SimpleMap<String, Object> paramSimpleMap, NameValuePair paramNameValuePair)
            throws Exception {
        if (paramNameValuePair == null)
            return;
        if (paramNameValuePair.getValue() == null)
            throw new Exception(paramNameValuePair.getName() + "not be null");
        paramSimpleMap.put(paramNameValuePair.getName(), paramNameValuePair.getValue());
    }

    public static void addParam(SimpleMap<String, Object> paramSimpleMap, String paramString, Object paramObject)
            throws SysException {
        if (paramObject != null) {
            paramSimpleMap.put(paramString, paramObject);
            return;
        }
        throw new SysException(paramString + " not be null");
    }

    public static void addParam(SimpleMap<String, Object> paramSimpleMap, String paramString, Object paramObject1, Object paramObject2)
            throws SysException {
        if (paramObject1 != null) ;
        for (paramObject1 = paramObject1; ; paramObject1 = paramObject2) {
            paramSimpleMap.put(paramString, paramObject1);
            return;
        }
    }

    public static void addParamCanBeNull(SimpleMap<String, Object> paramSimpleMap, NameValuePair paramNameValuePair)
            throws Exception {
        String str = null;
        if (paramNameValuePair != null) {
            str = paramNameValuePair.getName();
            if (paramNameValuePair.getValue() == null)
                return;
        }
        paramSimpleMap.put(str, paramNameValuePair);
    }

    public static void addParamCanBeNull(SimpleMap<String, Object> paramSimpleMap, String paramString, Object paramObject)
            throws SysException {
        if ((paramObject != null) && (!("".equals(paramObject)))) ;
        for (paramObject = paramObject; ; paramObject = null) {
            paramSimpleMap.put(paramString, paramObject);
            return;
        }
    }
}