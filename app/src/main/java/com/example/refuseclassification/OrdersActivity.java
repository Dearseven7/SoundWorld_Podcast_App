package com.example.refuseclassification;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.refuseclassification.mainfragment.SettingFragment;

public class OrdersActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        toolbar = (Toolbar) findViewById(R.id.ordertoolbar);
        toolbar.setTitle("我的订单");
        new setTitleCenter().setTitleCenter(toolbar);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrdersActivity.this, SettingFragment.class);
                startActivity(intent);
            }
        });

    }
}
