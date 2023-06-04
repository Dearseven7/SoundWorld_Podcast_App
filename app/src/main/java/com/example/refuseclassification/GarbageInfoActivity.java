package com.example.refuseclassification;

import static android.app.PendingIntent.getActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.example.refuseclassification.ASRresponse;
import com.example.refuseclassification.CommonActivity;
import com.example.refuseclassification.KnowledgeDatabase;
import com.example.refuseclassification.R;
import com.example.refuseclassification.SearchActivity;
import com.example.refuseclassification.TestActivity;
import com.example.refuseclassification.setTitleCenter;
import com.google.gson.Gson;

import java.util.ArrayList;

public class GarbageInfoActivity extends BaseActivity {

    private Toolbar toolbar;
    private ImageButton recyclable_button;
    private ImageButton harmful_button;
    private ImageButton wet_button;
    private ImageButton dry_button;
    private ImageButton information_button;
    private ImageButton exercise_button;
    private ImageButton errorProne_button;
    private ImageButton common_button;
    private ImageButton special_button;
    private EditText search;
    private ImageButton recording_button;
    private EventManager asr;//语音识别核心库
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garbage_information);
        toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        toolbar.setTitle("garbage");

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
                Intent intent = new Intent(GarbageInfoActivity.this, WetActivity.class);
                startActivity(intent);
            }
        });
        dry_button = findViewById(R.id.dry_button);
        dry_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GarbageInfoActivity.this, DryActivity.class);
                startActivity(intent);
            }
        });

    }
}
