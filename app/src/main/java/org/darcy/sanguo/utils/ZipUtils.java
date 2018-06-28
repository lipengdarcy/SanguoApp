package org.darcy.sanguo.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    private static void ZipFiles(String path, String fileName, ZipOutputStream paramZipOutputStream) throws Exception {
        Log.v("XZip", "ZipFiles(String, String, ZipOutputStream)");
        if (paramZipOutputStream == null)
            return;
        File file = new File(path + fileName);
        if (file.isFile()) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            paramZipOutputStream.putNextEntry(zipEntry);
            byte[] array = new byte[4096];
            while (true) {
                int i = fileInputStream.read(array);
                if (i == -1) {
                    paramZipOutputStream.closeEntry();
                    break;
                }
                paramZipOutputStream.write(array, 0, i);
            }
        }
        String[] fileList = file.list();
        if (fileList.length <= 0) {
            paramZipOutputStream.putNextEntry(new ZipEntry(fileName + File.separator));
            paramZipOutputStream.closeEntry();
        }
        for (int i = 0; i < fileList.length; ++i) {
            ZipFiles(path, fileName + File.separator + fileList[i], paramZipOutputStream);
        }
    }

    public static List<File> getFileList(String fileName, boolean paramBoolean1, boolean paramBoolean2) throws Exception {
        ArrayList localArrayList = new ArrayList();
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(fileName));
        while (true) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            if (zipEntry == null) {
                zipInputStream.close();
                return localArrayList;
            }
            String str = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                File file = new File(str.substring(0, str.length() - 1));
                if (!(paramBoolean1))
                    continue;
                localArrayList.add(file);
            }
            File file = new File(str);
            if (!(paramBoolean2))
                continue;
            localArrayList.add(file);
        }
    }

    public static InputStream unzip(String paramString1, String paramString2) throws Exception {
        ZipFile zipFile = new ZipFile(paramString1);
        return zipFile.getInputStream(zipFile.getEntry(paramString2));
    }


}