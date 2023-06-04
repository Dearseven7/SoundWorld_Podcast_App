package com.example.refuseclassification;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

public class PersonalHomeActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_home);
        toolbar = (Toolbar) findViewById(R.id.persontool);
        toolbar.setTitle("个人主页");
        new setTitleCenter().setTitleCenter(toolbar);
    }
}
