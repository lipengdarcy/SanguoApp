package org.cocos2dx.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class PSJNIHelper {
    static ArrayList<String> mArrayList;
    static HashMap<String, String> mHashMap = null;
    static Vector<String> mVector = null;

    static {
        mArrayList = null;
    }

    public static void createArrayList() {
        mArrayList = new ArrayList();
    }

    public static void createHashMap() {
        mHashMap = new HashMap();
    }

    public static void createVector() {
        mVector = new Vector();
    }

    public static ArrayList<String> getArrayList() {
        return mArrayList;
    }

    public static HashMap<String, String> getHashMap() {
        return mHashMap;
    }

    public static Vector<String> getVector() {
        return mVector;
    }

    public static void pushArrayListElement(String paramString) {
        if (mArrayList == null)
            return;
        mArrayList.add(paramString);
    }

    public static void pushHashMapElement(String paramString1, String paramString2) {
        if (mHashMap == null)
            return;
        mHashMap.put(paramString1, paramString2);
    }

    public static void pushVectorElement(String paramString) {
        if (mVector == null)
            return;
        mVector.add(paramString);
    }
}