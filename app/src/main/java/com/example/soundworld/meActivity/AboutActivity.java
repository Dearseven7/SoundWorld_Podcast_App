package com.example.soundworld.meActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.soundworld.BaseActivity;
import com.example.soundworld.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AboutActivity extends BaseActivity {

    private TextView titleAbout;
    ImageView backme;
    ImageView test2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_about);
        titleAbout = findViewById(R.id.title_about);
        backme = findViewById(R.id.backme);
        titleAbout.setText("关于我们");
//        test2 = findViewById(R.id.testt);

//        Glide.with(this).load("" +"https://bts-image.xyzcdn.net/aHR0cHM6Ly9mZGZzLnhtY2RuLmNvbS9zdG9yYWdlcy8xZjY2LWF1ZGlvZnJlZWhpZ2hxcHMvODMvQ0EvQ0t3UklKRUVfTGs4QUFFMmxRRGNUV00wLmpwZWc=.jpeg@small").into(test2);





        backme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }


    public Bitmap getBitmap(String path) throws IOException {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
