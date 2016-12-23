package net.chenzi.readapkcommentdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.zip.ZipFile;

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
            ZipFile zip = new ZipFile(path);
            String comment = zip.getComment();
            System.out.println("zip comment = " + comment);
        } catch (IOException e) {
            System.out.println(e.toString());
        }

    }
}
