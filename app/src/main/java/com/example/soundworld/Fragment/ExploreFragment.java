package com.example.soundworld.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundworld.Database.Session;
import com.example.soundworld.PodcastSearchActivity;
import com.example.soundworld.R;
import com.example.soundworld.Database.Item;
import com.example.soundworld.adapter.SessionAdapter;
import com.example.soundworld.setTitleCenter;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {
    RecyclerView mSessionview;
    ArrayList<Session> mSessionList = new ArrayList<>();
    SessionAdapter mSessionAdapter;


    private boolean refresh;

    private List<Item> mData=new ArrayList<>();
    private RecyclerView mRecyclerview;

    private ImageView imageSession;

    private TextView session_search;
    private Toolbar toolbar;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.ordertoolbar);
        toolbar.setTitle("Explore");
        new setTitleCenter().setTitleCenter(toolbar);

        mSessionview = view.findViewById(R.id.session_list);

        session_search = view.findViewById(R.id.session_search);
        GridLayoutManager manager=new GridLayoutManager(getActivity(),2);

        mSessionview.setLayoutManager(manager);


        if(refresh==true) {
            mSessionAdapter.clearData();
        }


//        mSessionview.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        mSessionAdapter = new SessionAdapter(mSessionList,getActivity());

        mSessionview.setAdapter(mSessionAdapter);
        refresh = true;

        initedata();


        session_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PodcastSearchActivity.class);
                startActivity(intent);
            }
        });

        imageSession = view.findViewById(R.id.session);

//        imageSession.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), SessionDetilActivity.class);
//                startActivity(intent);
//            }
//        });

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
        Session session1 =new Session( R.drawable.culture,"Chinese Culture");
        Session session2 =new Session( R.drawable.language,"Language Learning");
        Session session3 =new Session( R.drawable.music,"Music");
        Session session4 =new Session(R.drawable.personalgrowth,"Personal Growth");

        mSessionList.add(session1);
        mSessionList.add(session2);
        mSessionList.add(session3);
        mSessionList.add(session4);


    }
}
