package com.example.soundworld;
/*
 * @author: Dearseven
 * @description:
 */

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundworld.Database.MyOpenHelper;
import com.example.soundworld.Database.PodcastList;
import com.example.soundworld.adapter.SearchPodcastAdapter;

import java.util.ArrayList;
import java.util.List;

public class PodcastSearchActivity extends AppCompatActivity {
    private EditText editText;
    private ImageView search_icon,back_search;
    private RecyclerView recyclerView;
    private String record;
    List<PodcastList> podcastLists = new ArrayList<>();
    private SearchPodcastAdapter searchPodcastAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.search_recyclerView);

        SearchPodcastAdapter searchPodcastAdapter1 = new SearchPodcastAdapter(podcastLists);
        recyclerView.setAdapter(searchPodcastAdapter1);

        //list竖向排布
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        searchPodcastAdapter = new SearchPodcastAdapter(podcastLists);
        recyclerView.setAdapter(searchPodcastAdapter);

        search_icon = findViewById(R.id.msearch_icon);
        back_search = findViewById(R.id.backsearch);
        back_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 实例化EditText
        editText = findViewById(R.id.msearch);

        // 初始化数据列表
        MyOpenHelper dbHelper = new MyOpenHelper(this, "PodcastList.db", null, 2);//打开数据库

        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                editText.setText(record);
                record = editText.getText().toString();

                SQLiteDatabase db = dbHelper.getReadableDatabase();
                PodcastList searchPodcast = dbHelper.getDataByTitle(record);
//                PodcastList searchPodcast = dbHelper.getDataById(1);

                if(searchPodcast!= null){
                    PodcastList mpodcast = new PodcastList(searchPodcast.getId(),searchPodcast.getTitle(),searchPodcast.getDescription(),searchPodcast.getImage_url());
                    podcastLists.add(mpodcast);
                    Toast.makeText(PodcastSearchActivity.this, "搜索成功！", Toast.LENGTH_SHORT).show();


                    Log.d("YourActivity", "ID: " + mpodcast.getTitle() + ", Data: " + mpodcast.getDescription());
                }else{
                    Toast.makeText(PodcastSearchActivity.this, "SoundWorld没有此播客，等待后续添加～", Toast.LENGTH_SHORT).show();

                    Log.d("YourActivity", "NUllllllllllllll " );
                }
                searchPodcastAdapter = new SearchPodcastAdapter(podcastLists);
                recyclerView.setAdapter(searchPodcastAdapter);

            }
        });

    }



}
