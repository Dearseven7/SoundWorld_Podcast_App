package com.example.refuseclassification;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;

import com.baidu.speech.EventManager;

public class GarbageInfoActivity extends BaseActivity {

    private Toolbar toolbar;
    private ImageButton recyclable_button;
    private ImageButton harmful_button;
    private ImageButton wet_button;
    private ImageButton dry_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garbage_information);
        toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        toolbar.setTitle("四大垃圾分类");

        new setTitleCenter().setTitleCenter(toolbar);// 初始化ToolBar

        recyclable_button = findViewById((R.id.recyclable_button));
        recyclable_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GarbageInfoActivity.this, RecyclableActivity.class);
                startActivity(intent);
            }
        });
        harmful_button = findViewById(R.id.harmful_button);
        harmful_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GarbageInfoActivity.this, HarmfulActivity.class);
                startActivity(intent);
            }
        });
        wet_button = findViewById(R.id.wet_button);
        wet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GarbageInfoActivity.this, KitchenActivity.class);
                startActivity(intent);
            }
        });
        dry_button = findViewById(R.id.dry_button);
        dry_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GarbageInfoActivity.this, OthersActivity.class);
                startActivity(intent);
            }
        });

    }
}
