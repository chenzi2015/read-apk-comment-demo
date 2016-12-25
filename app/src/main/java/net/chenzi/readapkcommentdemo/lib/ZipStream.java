package net.chenzi.readapkcommentdemo.lib;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * Read Apk comment
 * Created by chenzi on 2016/12/23.
 */
public class ZipStream {

    /**
     * zip end magic
     */
    private static long MAGIC = 0x06054b50;
    /**
     * zip end magic string
     */
    private static String StringEndArchive = "PK\005\006";
    /**
     * max comment length
     */
    private static long maxComment = 100;
    /**
     * zip file object
     */
    private RandomAccessFile mZipFile;
    /**
     * zip comment
     */
    private String mComment;

    /**
     * ZipStream construct
     *
     * @param file
     */
    public ZipStream(RandomAccessFile file) {
        mZipFile = file;
    }

    public String getComment() throws Exception {
        long fileSize = mZipFile.length();
        long startPosition = Math.max((fileSize - maxComment), 0);
        System.out.println("filesize=" + fileSize);
        System.out.println("maxComment=" + maxComment);
        System.out.println("startPosition=" + startPosition);

        //seek to start position
        mZipFile.seek(startPosition);
        int readLength = (int) (fileSize - startPosition);
        System.out.println("readLength=" + readLength);

        //read end data
        byte[] endData = new byte[readLength];
        mZipFile.read(endData, 0, readLength);
        String endDataString = new String(endData, "UTF-8");
        System.out.println("endData=" + endDataString);
        System.out.println("endDataByte=" + Arrays.toString(endData));

        //find the zip end magic string position
        int StringEndArchivePosition = endDataString.indexOf(StringEndArchive);
        System.out.println("StringEndArchivePosition=" + StringEndArchivePosition);
        if (StringEndArchivePosition == -1) {
            return "";
        }

        //check sign
        long endRecordPosition = startPosition + StringEndArchivePosition;
        System.out.println("StringEndArchivelength=" + StringEndArchive.length());
        mZipFile.seek(endRecordPosition);
        System.out.println("StringEndArchivelengthReadLine=" + mZipFile.readLine());

        byte[] sign = new byte[4];
        mZipFile.read(sign);
        String signString = new String(sign, "UTF-8");
        System.out.println("getFilePointer=" + mZipFile.getFilePointer());
        System.out.println("signString=" + signString);
        System.out.println("signByte=" + Arrays.toString(sign));
        System.out.println("signStringlength=" + signString.length());

        //read comment length
        mZipFile.seek(endRecordPosition + 20);
        byte[] commentLengthByte = new byte[2];
        mZipFile.read(commentLengthByte);

        int commentLength = byte2int(commentLengthByte);
        System.out.println("getFilePointer=" + mZipFile.getFilePointer());
        System.out.println("commentLength=" + Arrays.toString(commentLengthByte));
        System.out.println("commentLength=" + commentLength);

        //read comment
        mZipFile.seek(endRecordPosition + 22);
        byte[] commentByte = new byte[commentLength];
        mZipFile.read(commentByte);
        System.out.println("commentByte=" + Arrays.toString(commentByte));
        mComment = new String(commentByte, "UTF-8");

        //close
        mZipFile.close();

        return mComment.trim();
    }


    /**
     * Check signature
     */
    private boolean isSignature(String sign) {
        String ss = String.valueOf((int) MAGIC);
        System.out.println("isSignature=" + ss + "|end");


        return true;
    }

    /**
     * byte to int
     *
     * @param bytes byte[]
     * @return int
     */
    private static int byte2int(byte[] bytes) {
        return (bytes[0] & 0xff) | ((bytes[1] << 8) & 0xff00);
    }
}
