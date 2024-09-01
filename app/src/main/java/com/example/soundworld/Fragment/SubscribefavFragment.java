package com.example.soundworld.Fragment;
/*
 * @author: Dearseven
 * @description:
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundworld.Database.MyOpenHelper;
import com.example.soundworld.Database.PodcastList;
import com.example.soundworld.R;
import com.example.soundworld.adapter.SubscribeAdapter;
import com.example.soundworld.setTitleCenter;

import java.util.ArrayList;
import java.util.List;


public class SubscribefavFragment extends Fragment {



    private boolean refresh;


    private Toolbar toolbar;

    ProgressBar subProgressbar;
    SubscribeAdapter subscribeAdapter;
    private List<PodcastList> subPodcast = new ArrayList<>();

    private RecyclerView subView;
    private boolean isDataLoaded = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscribe, container, false);



        subProgressbar = view.findViewById(R.id.subProgressBar);


        toolbar = view.findViewById(R.id.subscribe_bar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle("Subscribe");
        }





//        initedata();
        if (!isDataLoaded) {
            showSubDatabase();
            isDataLoaded=true;
        }



//
        new setTitleCenter().setTitleCenter(toolbar);// 初始化ToolBar

        subView = view.findViewById(R.id.subscribe_viewlist);
        subView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        updateRecyclerViewData(subPodcast);

        subscribeAdapter = new SubscribeAdapter(subPodcast,getActivity());

        subscribeAdapter.clearData();

        showSubDatabase();


        subscribeAdapter = new SubscribeAdapter(subPodcast,getActivity());
        subView.setAdapter(subscribeAdapter);
        subProgressbar.setVisibility(View.GONE);
        refresh = true;
        return view;
    }

    private void updateRecyclerViewData(List<PodcastList> newDataList) {
        // 获取 RecyclerView 的适配器
        SubscribeAdapter subscribeAdapter = new SubscribeAdapter(subPodcast,getActivity());
        subView.setAdapter(subscribeAdapter);
    }



    private void showSubDatabase(){
            MyOpenHelper mdb = new MyOpenHelper(this.getContext(), "PodcastList.db", null, 2);//打开数据库
            String sqlSelect = "SELECT * FROM Subscribe";
            SQLiteDatabase sd = mdb.getReadableDatabase();//获取数据库
            Cursor cursor = sd.rawQuery(sqlSelect, new String[]{});

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String podcast_url = cursor.getString(cursor.getColumnIndex("podcast_url"));
//            将数据库中的数据加入到PodcastList中
                PodcastList podcast = new PodcastList(id, title, description, podcast_url);
                subPodcast.add(podcast);
            }
            cursor.close();

    }

    }
