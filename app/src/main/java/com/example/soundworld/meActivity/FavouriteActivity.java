package com.example.soundworld.meActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundworld.BaseActivity;
import com.example.soundworld.Database.EpisodeList;
import com.example.soundworld.Database.MyOpenHelper;
import com.example.soundworld.R;
import com.example.soundworld.adapter.FavouriteAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends BaseActivity {

    ImageView backme;

    private List<EpisodeList> favepisode = new ArrayList<>();
    FavouriteAdapter favouriteAdapter;
    RecyclerView favRecycleview;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_favourite);
        favRecycleview = findViewById(R.id.fav_recycleview);
        favRecycleview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        backme = findViewById(R.id.backme);
        backme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        favouriteAdapter = new FavouriteAdapter(favepisode);
        favouriteAdapter.clearData();
        showFavouriteDatabase();
        favouriteAdapter = new FavouriteAdapter(favepisode);
        favRecycleview.setAdapter(favouriteAdapter);

    }

    private void showFavouriteDatabase(){
        MyOpenHelper mdb = new MyOpenHelper(this, "PodcastList.db", null, 2);//打开数据库

        String sqlSelect = "SELECT * FROM Episode";
        SQLiteDatabase sd = mdb.getReadableDatabase();//获取数据库
        Cursor cursor=sd.rawQuery(sqlSelect,new String[]{});
        while(cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String title=cursor.getString(cursor.getColumnIndex("title"));
            String description=cursor.getString(cursor.getColumnIndex("description"));
            String image_url=cursor.getString(cursor.getColumnIndex("image_url"));
            String audio_url=cursor.getString(cursor.getColumnIndex("audio_url"));
            int podcast_id = cursor.getInt(cursor.getColumnIndex("podcastId"));
            boolean isstar = cursor.getInt(cursor.getColumnIndex("star"))>0;
            boolean isdownload = cursor.getInt(cursor.getColumnIndex("download"))>0;
            boolean islove = cursor.getInt(cursor.getColumnIndex("love"))>0;

//            将数据库中的数据加入到PodcastList中
            EpisodeList mepisode=new EpisodeList(id,title,image_url,audio_url,description,podcast_id,isstar,isdownload,islove);
            if(mepisode.getLove()==true){
                favepisode.add(mepisode);}
        }
        cursor.close();

    }
}
