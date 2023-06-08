package com.example.refuseclassification;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.refuseclassification.R;

public class CreditInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_information);
        Toolbar toolbar = findViewById(R.id.credit_info);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("积分说明");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView textView = findViewById(R.id.tv_credit_info);
        textView.setText("一、用户答每对一题计1分；\n" +
                "二、用户每累计答题100题，根据用户正确率奖励用户相应积分：\n" +
                "正确率为60%（含）-70%（不含）：50分，\n" +
                "正确率为70%（含）-80%（不含）：70分，\n" +
                "正确率为80%（含）-90%（不含）：100分，\n" +
                "正确率为90%（含）-100%（不含）：150分，\n" +
                "正确率为100%：200分；");
    }
}