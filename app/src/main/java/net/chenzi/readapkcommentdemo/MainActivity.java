package net.chenzi.readapkcommentdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.chenzi.readapkcommentdemo.lib.ZipStream;

import java.io.File;
import java.io.RandomAccessFile;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find apk position
        String path = getApplicationContext().getPackageResourcePath();
        System.out.println("path===" + path);

        //red apk comment
        try {
            File file = new File(path);
            RandomAccessFile apkFile = new RandomAccessFile(file,"r");
            ZipStream zipStream = new ZipStream(apkFile);
            String commnet = zipStream.getComment();

            System.out.println("zip comment = " + commnet);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
