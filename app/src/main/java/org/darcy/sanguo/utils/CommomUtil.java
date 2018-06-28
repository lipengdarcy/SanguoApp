package org.darcy.sanguo.utils;


public class CommomUtil {

    private static final int KEY = 119;
    private static int PIECE_LENGTH = 20;
    private static int SKIP = 4;

    public static byte[] decodeBytes(byte[] paramArrayOfByte) {
        int length = paramArrayOfByte.length;
        int i = 0;
        for (int j = 0; j < length; ++j) {
            int k = 0;
            if (j >= PIECE_LENGTH)
                k = i;
            j = k + SKIP;
            i = j;
            if (j >= length)
                return paramArrayOfByte;

            paramArrayOfByte[i] = (byte) (paramArrayOfByte[i] ^ 0x77);
            k = ++i;
        }
        return paramArrayOfByte;
    }

    public static void encodeBytes(byte[] paramArrayOfByte) {

    }

    public static String encrpyDecode(String paramString) {
        if ((paramString == null) || (paramString.trim().length() == 0))
            return paramString;
        byte[] array = Base64.decode(paramString.toCharArray());
        String result = new String(decodeBytes(array));
        return result;
    }

    public static String encrpyEncode(String paramString) {
        if ((paramString == null) || (paramString.trim().length() == 0))
            return paramString;
        byte[] array = paramString.getBytes();
        encodeBytes(array);
        return new String(array);
    }
}
