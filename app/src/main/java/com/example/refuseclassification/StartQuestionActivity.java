package com.example.refuseclassification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;

public class StartQuestionActivity extends BaseActivity {

    private Toolbar toolbar;
    private Button begin_question;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        toolbar = (Toolbar) findViewById(R.id.about_toolbar);
        toolbar.setTitle("开始答题");
        new setTitleCenter().setTitleCenter(toolbar);
        begin_question = findViewById(R.id.button_begin_question);
        begin_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartQuestionActivity.this, CommonActivity.class);
                startActivity(intent);
            }
        });
    }
}
