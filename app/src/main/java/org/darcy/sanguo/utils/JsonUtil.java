package org.darcy.sanguo.utils;

import org.darcy.sanguo.pojo.SimpleMap;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonUtil {
    private static Gson GSON = new Gson();
    private static Type MAP_TYPE = null;

    public static String toJson(SimpleMap<String, String> paramSimpleMap) {
        return GSON.toJson(paramSimpleMap, MAP_TYPE);
    }

    public static String toJson(Object paramObject) {
        return GSON.toJson(paramObject);
    }

    public static Map<String, String> toMap(String paramString) {
        if ((paramString == null) || (!(paramString.contains("{"))))
            return null;
        return (Map) GSON.fromJson(paramString, MAP_TYPE);
    }

    public static <T> Object toObject(String paramString, Class<T> paramClass) {
        return GSON.fromJson(paramString, paramClass);
    }
}