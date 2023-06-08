package com.example.refuseclassification;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.refuseclassification.R;

public class DetailActivity extends AppCompatActivity {
    private ImageView mCommodityImage;
    private TextView mCommodityText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailactivity);

        // 获取XML布局文件中的ImageView和TextView
        mCommodityImage = findViewById(R.id.commodity_image);
        mCommodityText = findViewById(R.id.commodity_text);

        // 获取并设置传递的商品信息
        String commodityName = getIntent().getStringExtra("commodityName");
        int commodityImageResourceId = getIntent().getIntExtra("commodityImageResourceId", R.drawable.hongfushi);
        String commodityDescription = getIntent().getStringExtra("commodityDescription");

        mCommodityImage.setImageResource(commodityImageResourceId);
        mCommodityText.setText(commodityName + "\n" + commodityDescription);
    }
}
