package com.example.soundworld;
/*
 * @author: Dearseven
 * @description:
 */

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soundworld.Database.Creator;
import com.example.soundworld.Database.EpisodeList;
import com.example.soundworld.Database.MyOpenHelper;
import com.example.soundworld.Database.PodcastList;
import com.example.soundworld.adapter.CreatorAdapter;
import com.example.soundworld.adapter.EpisodeAdapter;

import java.util.ArrayList;
import java.util.List;

public class PodcastDetailActivity extends AppCompatActivity {
    private List<Creator> mcreator = new ArrayList<>();

    private boolean isDataLoaded = false;
    private boolean isFirstClicklove = true;

    private List<EpisodeList> mepisode = new ArrayList<>();

    private ProgressBar progressBar;
//    private NestedScrollView scrollView;
    private TextView detailTitle,description;
    MyOpenHelper myOpenHelper;
    private int podcastID,ToppodcastID,currID;
    private ImageView detailImage,backIcon,favouriteIcon,addIcon;
    private List<PodcastList> mpodcastList  = new ArrayList<>();
    private EpisodeAdapter episodeAdapter;
    private CreatorAdapter creatorAdapter;





    private RecyclerView recyclerViewCreators,recyclerViewEpisodeList;
    private RecyclerView.Adapter adapterCreatorList,adapterEpisodeList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_podcast);
        initDetail();

//        episodeAdapter = new EpisodeAdapter(mepisode);
//        episodeAdapter.clearData();
        episodeAdapter = new EpisodeAdapter(mepisode);

        recyclerViewEpisodeList.setAdapter(episodeAdapter);

        creatorAdapter = new CreatorAdapter(mcreator);
        recyclerViewCreators.setAdapter(creatorAdapter);


        podcastID = getIntent().getIntExtra("id",0);
        ToppodcastID = getIntent().getIntExtra("topid",0);

        if(podcastID!=0){
            currID = podcastID;
        }else if(ToppodcastID != 0){
            currID = ToppodcastID;

        };


//        打开数据库
        myOpenHelper = new MyOpenHelper(this, "PodcastList.db", null, 2);
        SQLiteDatabase db = myOpenHelper.getReadableDatabase();

        PodcastList detipodcastList = myOpenHelper.getDataById(podcastID);
        PodcastList detitoppodcastList = myOpenHelper.getDataById(ToppodcastID) ;

        PodcastList currpodcast = new PodcastList();

        if (detitoppodcastList != null) {
            int retrievedId = detitoppodcastList.getId();
            String title1 = detitoppodcastList.getTitle();

            // 使用检索到的数据进行操作
            Log.d("YourActivity", "ID: " + retrievedId + ", Data: " + title1);
        }

        if (detipodcastList != null) {
            int retrievedId = detipodcastList.getId();
            String description1 = detipodcastList.getTitle();

            // 使用检索到的数据进行操作
            Log.d("YourActivity", "ID: " + retrievedId + ", Data: " + description1);
        }

        if (ToppodcastID == 0) {
             currpodcast = detipodcastList;
        }else if (podcastID == 0){
             currpodcast = detitoppodcastList;
        }


        showCreatorDatabase();
        showEpisodeDatabase();

//
//        if (!isDataLoaded){
//            isDataLoaded = true;
//        }

//        progressBar.setVisibility(View.VISIBLE);

//        添加播客订阅
        MyOpenHelper mdb = new MyOpenHelper(this, "PodcastList.db", null, 2);//打开数据库

        PodcastList finalCurrpodcast = currpodcast;
        PodcastList findpodcast = mdb.getDataByTitleSub(finalCurrpodcast.getTitle());
        if(finalCurrpodcast != null && findpodcast == null ){
            addIcon.setImageResource(R.drawable.baseline_library_add_24);

        }else {
            addIcon.setImageResource(R.drawable.baseline_library_add_check_24);
        }



        favouriteIcon = findViewById(R.id.favouriteIcon);

        if(finalCurrpodcast.isLove()==false) {
            favouriteIcon.setImageResource(R.drawable.favouriteicon);
        }else {
            favouriteIcon.setImageResource(R.drawable.baseline_favorite_24);
        }
        favouriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (finalCurrpodcast.isLove() == false) {
                    if (isFirstClicklove) {
                        ContentValues updatedValues = new ContentValues();
                        updatedValues.put("love", 1);
                        mdb.updatePodcast(finalCurrpodcast.getId(), updatedValues);
                        favouriteIcon.setImageResource(R.drawable.baseline_favorite_24);
                        Toast.makeText(PodcastDetailActivity.this, "欢迎标记播客为喜欢！", Toast.LENGTH_SHORT).show();
                        isFirstClicklove = false;
                    }else{
                        ContentValues updatedValues = new ContentValues();
                        updatedValues.put("love", 0);
                        mdb.updatePodcast(finalCurrpodcast.getId(), updatedValues);
                        favouriteIcon.setImageResource(R.drawable.favouriteicon);
                        Toast.makeText(PodcastDetailActivity.this, "已取消喜欢该节目～", Toast.LENGTH_SHORT).show();
                        isFirstClicklove = true;

                    }

                    } else {
                    if(isFirstClicklove){
                        ContentValues updatedValues = new ContentValues();
                        updatedValues.put("love", 0);
                        mdb.updatePodcast(finalCurrpodcast.getId(), updatedValues);
                        favouriteIcon.setImageResource(R.drawable.favouriteicon);
                        Toast.makeText(PodcastDetailActivity.this, "已取消喜欢该节目～", Toast.LENGTH_SHORT).show();
                        isFirstClicklove = false;

                    }else {
                        ContentValues updatedValues = new ContentValues();
                        updatedValues.put("love", 1);
                        mdb.updatePodcast(finalCurrpodcast.getId(), updatedValues);
                        favouriteIcon.setImageResource(R.drawable.baseline_favorite_24);
                        Toast.makeText(PodcastDetailActivity.this, "欢迎标记播客为喜欢！", Toast.LENGTH_SHORT).show();
                        isFirstClicklove = true;

                    }

                }
            }
        });



        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PodcastList findpodcast = mdb.getDataByTitleSub(finalCurrpodcast.getTitle());
                ContentValues values=new ContentValues();//获取ContentValues对象
                if(finalCurrpodcast != null && findpodcast == null ) {
                    ContentValues updatedValues = new ContentValues();
                    updatedValues.put("subscribe",1);
                    int rowsAffected = mdb.updatePodcast(finalCurrpodcast.getId(),updatedValues);
                    if (rowsAffected > 0) {
                        Log.d("YourActivity", "Data updated successfully.");
                    } else {
                        Log.e("YourActivity", "Failed to update data.");
                    }
                    SQLiteDatabase subdb = mdb.getWritableDatabase();
                    values.put("id",finalCurrpodcast.getId());
                    values.put("title", finalCurrpodcast.getTitle());
                    values.put("description", finalCurrpodcast.getDescription());
                    values.put("podcast_url", finalCurrpodcast.getImage_url());
                    values.put("subscribe", true);
                    values.put("love",finalCurrpodcast.isLove());
                    subdb.insert("Subscribe", null, values);//插入第一条数据
                    addIcon.setImageResource(R.drawable.baseline_library_add_check_24);
                    Toast.makeText(PodcastDetailActivity.this, "欢迎订阅，播客节目已成功添加至Subscribe！", Toast.LENGTH_SHORT).show();
                }
                else {

                    Toast.makeText(PodcastDetailActivity.this, "该播客已经订阅～", Toast.LENGTH_SHORT).show();

                }
            }
        });




//        数据库查询根据id判断并获取播客
        PodcastList podcastList1 = new PodcastList(podcastID,currpodcast.getTitle(),currpodcast.getDescription(),finalCurrpodcast.getImage_url());

        mpodcastList.add(podcastList1);

        detailTitle.setText(podcastList1.getTitle());
        description.setText(podcastList1.getDescription());
        Glide.with(this).load(podcastList1.getImage_url()).into(detailImage);
        CreatorAdapter creatorAdapter = new CreatorAdapter(mcreator);
        recyclerViewCreators.setAdapter(creatorAdapter);

        progressBar.setVisibility(View.GONE);

    }




    private void showCreatorDatabase(){
        MyOpenHelper mdb = new MyOpenHelper(this, "PodcastList.db", null, 2);//打开数据库

        String sqlSelect = "SELECT * FROM Creator";
        SQLiteDatabase sd = mdb.getReadableDatabase();//获取数据库
        Cursor cursor=sd.rawQuery(sqlSelect,new String[]{});
        while(cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String name=cursor.getString(cursor.getColumnIndex("creatorName"));
            String image=cursor.getString(cursor.getColumnIndex("creatorImage"));
            int podcast_id = cursor.getInt(cursor.getColumnIndex("podcastId"));
//            将数据库中的数据加入到PodcastList中
            Creator creator=new Creator(id,name,image,podcast_id);
            if(creator.getPodcastId()== currID){
            mcreator.add(creator);}
        }
        cursor.close();

    }

    private void showEpisodeDatabase(){
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
//            将数据库中的数据加入到PodcastList中
            EpisodeList episode=new EpisodeList(id,title,image_url,podcast_id,description,audio_url);
            if(episode.getPodcastId()== currID){
                mepisode.add(episode);}
        }
        cursor.close();

    }




    @SuppressLint("WrongViewCast")
    private void initDetail(){
//        一一绑定对应的控件
        detailTitle = findViewById(R.id.detailTitle);
        progressBar = findViewById(R.id.progressBarDetail);
//        scrollView = findViewById(R.id.scrollViewDetail);
        description = findViewById(R.id.detailDescription);
        detailImage = findViewById(R.id.program_img);
        backIcon = findViewById(R.id.backIcon);
        favouriteIcon = findViewById(R.id.favouriteIcon);
        addIcon = findViewById(R.id.addIcon);
        addIcon.setImageResource(R.drawable.baseline_library_add_24);
        recyclerViewCreators = findViewById(R.id.creatorList);
        recyclerViewEpisodeList = findViewById(R.id.episodeList);
//        设置list横向滑动
        recyclerViewCreators.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//        设置epidoe的list竖向滑动
        recyclerViewEpisodeList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
//        backicon的点击事件
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });




    };
}
