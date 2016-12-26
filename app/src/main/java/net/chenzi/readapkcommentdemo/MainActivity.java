package net.chenzi.readapkcommentdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import net.chenzi.readapkcommentdemo.lib.ZipStream;
import java.io.File;
import java.io.RandomAccessFile;

public class MainActivity extends Activity {

    private String mComment;

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
            RandomAccessFile apkFile = new RandomAccessFile(file, "r");
            ZipStream zipStream = new ZipStream(apkFile);
            mComment = zipStream.getComment();

            System.out.println("zip comment = " + mComment);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (!mComment.equals("")) {
            TextView textView = (TextView)findViewById(R.id.textView);
            textView.setText("ZIP注释内容：" + mComment);
        }
    }
}
