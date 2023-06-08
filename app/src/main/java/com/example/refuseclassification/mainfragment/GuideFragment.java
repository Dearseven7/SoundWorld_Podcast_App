package com.example.refuseclassification.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.refuseclassification.CreditInformationActivity;
import com.example.refuseclassification.Database.Item;
import com.example.refuseclassification.R;
import com.example.refuseclassification.adapter.RecyclerAdapter;
import com.example.refuseclassification.setTitleCenter;

import java.util.ArrayList;
import java.util.List;

public class GuideFragment extends Fragment {
    private List<Item> mData=new ArrayList<>();
    private RecyclerView mRecyclerview;
    private RecyclerAdapter mAdapter;

    private ImageView imageV;

    private Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_credit, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.ordertoolbar);
        toolbar.setTitle("积分兑换");
        new setTitleCenter().setTitleCenter(toolbar);
        mRecyclerview=(RecyclerView)view.findViewById(R.id.recyclercommodity);

        GridLayoutManager manager=new GridLayoutManager(getActivity(),2);

        initedata();
        mAdapter=new RecyclerAdapter(getActivity(),getLayoutInflater(),mData);
//        将设置的布局添加到视图
        mRecyclerview.setLayoutManager(manager);
        mRecyclerview.setAdapter(mAdapter);

        imageV = (ImageView) view.findViewById(R.id.imageView3);
        imageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreditInformationActivity.class);
                startActivity(intent);
            }
        });
//        添加分割线
//        mRecyclerview.addItemDecoration(new AdvanceDecoration(this, OrientationHelper.VERTICAL));

        //找到imageView3
//        ImageView imageView3 = view.findViewById(R.id.imageView3);
//        //为imageView3添加点击事件监听器
//        imageView3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //创建Intent对象并指定跳转目标为CreditInformationActivity.class
//                Intent intent = new Intent(getActivity(), CreditInformationActivity.class);
//                //启动Intent
//                startActivity(intent);
//            }
//        });
//        RecyclerView recyclercommodity =view.findViewById(R.id.recyclercommodity);
//        recyclercommodity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),DetailActivity.class);
//                startActivity(intent);
//            }
//        });
        return view;
    }

    public void initedata(){
        Item item =new Item("红富士", R.drawable.hongfushi);
        Item item1=new Item("保温杯", R.drawable.baowenbei);
        Item item2=new Item("眼影盘", R.drawable.yanying);
        Item item3=new Item("猕猴桃", R.drawable.mihoutao);
        Item item4=new Item("电煮锅", R.drawable.guo);
        Item item5=new Item("充电宝", R.drawable.chongdianbao);
        mData.add(item);
        mData.add(item1);
        mData.add(item2);
        mData.add(item3);
        mData.add(item4);
        mData.add(item5);

    }
}
