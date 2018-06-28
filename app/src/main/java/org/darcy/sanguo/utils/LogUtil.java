package org.darcy.sanguo.utils;

import android.os.Environment;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogUtil {
    private static final String ERROR_LOG;
    private static final String HTTP_RETURN_LOG;
    private static final String OFFLINE_DATE = Environment.getExternalStorageDirectory() + File.separator + "darcy.sanguo" + File.separator + "log" + File.separator + "OfflineData.txt";
    private static SimpleDateFormat cjLogFormat;

    static {
        ERROR_LOG = Environment.getExternalStorageDirectory() + File.separator + "darcy.sanguo" + File.separator + "log" + File.separator + "Error.txt";
        HTTP_RETURN_LOG = Environment.getExternalStorageDirectory() + File.separator + "darcy.sanguo" + File.separator + "log" + File.separator + "HttpReturn.txt";
        cjLogFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    private static void deleteFile(String paramString) {
        File file = new File(paramString);
        if (!(file.exists()))
            return;
        file.delete();
    }

    public static void deleteTheHttpReturn() {
        deleteFile(HTTP_RETURN_LOG);
    }

    public static void deleteTheOffline() {
        deleteFile(OFFLINE_DATE);
    }

    private static List<String> readFile(String path) {
        ArrayList localArrayList = new ArrayList();
        File file = new File(path);
        if (!file.exists())
            return null;
        try {
            FileInputStream localFileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(localFileInputStream));
            String str = null;
            while (str == null) {
                str = bufferedReader.readLine();
                localArrayList.add(str);
                str = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localArrayList;
    }


    public static List<String> readOfflineStrings() {
        return readFile(OFFLINE_DATE);
    }

    private static void writeData(String path, String fileName) {
        File file = new File(fileName);
        File parent = new File(file.getParent() + File.separator);
        if (!parent.exists())
            parent.mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            StringBuilder sb = new StringBuilder(fileName);
            sb.append("\n");
            FileOutputStream fos = new FileOutputStream(path, true);
            IOUtils.write(path, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeErrorLog(String paramString) {
        writeData(ERROR_LOG, cjLogFormat.format(new Date(System.currentTimeMillis())) + ":" + paramString);
    }

    public static void writeHttpLog(String paramString) {
        writeData(HTTP_RETURN_LOG, cjLogFormat.format(new Date(System.currentTimeMillis())) + ":" + paramString);
    }

    public static void writeOfflineData(String paramString) {
        writeData(OFFLINE_DATE, cjLogFormat.format(new Date(System.currentTimeMillis())) + ":" + paramString);
    }
}