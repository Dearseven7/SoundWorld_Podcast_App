package com.example.refuseclassification.mainfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.refuseclassification.Database.Item;
import com.example.refuseclassification.PagerAdapter;
import com.example.refuseclassification.R;
import com.example.refuseclassification.adapter.RecyclerAdapter;
import com.example.refuseclassification.setTitleCenter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class GuideFragment extends Fragment {
    private List<Item> mData=new ArrayList<>();
    private RecyclerView mRecyclerview;
    private RecyclerAdapter mAdapter;




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
//        添加分割线
//        mRecyclerview.addItemDecoration(new AdvanceDecoration(this, OrientationHelper.VERTICAL));
        
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
