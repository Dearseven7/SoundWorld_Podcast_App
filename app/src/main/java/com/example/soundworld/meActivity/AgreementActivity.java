package com.example.soundworld.meActivity;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.soundworld.BaseActivity;
import com.example.soundworld.R;
import com.example.soundworld.setTitleCenter;

public class AgreementActivity extends BaseActivity {
    ImageView backme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_agreement);
        backme = findViewById(R.id.backme);
        backme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }
}
