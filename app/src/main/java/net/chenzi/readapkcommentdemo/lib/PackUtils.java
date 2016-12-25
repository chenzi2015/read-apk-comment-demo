package net.chenzi.readapkcommentdemo.lib;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chenzi on 2016/12/24.
 */

public class PackUtils {

    /**
     * 打包字符串
     * 类似php中pack在java中的实现
     *
     * @param str
     * @return
     */
    public static byte[] pack(String str) {
        int nibbleshift = 4;
        int position = 0;
        int len = str.length() / 2 + str.length() % 2;
        byte[] output = new byte[len];
        for (char v : str.toCharArray()) {
            byte n = (byte) v;
            if (n >= '0' && n <= '9') {
                n -= '0';
            } else if (n >= 'A' && n <= 'F') {
                n -= ('A' - 10);
            } else if (n >= 'a' && n <= 'f') {
                n -= ('a' - 10);
            } else {
                continue;
            }
            output[position] |= (n << nibbleshift);

            if (nibbleshift == 0) {
                position++;
            }
            nibbleshift = (nibbleshift + 4) & 7;
        }

        return output;
    }

    /**
     * 16进制的字符解压 类php中unpack
     *
     * @param is
     * @param len
     * @return
     * @throws IOException
     */
    public static String unpack(InputStream is, int len) throws IOException {
        byte[] bytes = new byte[len];
        is.read(bytes);
        return unpack(bytes);
    }

    /***
     * 16进制的字符解压  类php中unpack
     *
     * @param bytes
     * @return
     */
    public static String unpack(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
