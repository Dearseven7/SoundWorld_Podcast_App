package com.example.soundworld.Fragment;
/*
 * @author: Dearseven
 * @description:
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.baidu.speech.EventListener;
import com.example.soundworld.Database.CoverItem;
import com.example.soundworld.Database.MyOpenHelper;
import com.example.soundworld.Database.PodcastList;
import com.example.soundworld.PodcastSearchActivity;
import com.example.soundworld.R;
import com.example.soundworld.adapter.CoverAdapter;
import com.example.soundworld.adapter.RecommendPodcastAdapter;
//import com.example.soundworld.databinding.FragmentHomeBinding;
import com.example.soundworld.adapter.TopPodcastAdapter;
import com.example.soundworld.setTitleCenter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements EventListener {

//    数据绑定
//    private FragmentHomeBinding fragmentHomeBinding;

//    private PodcastViewModel mpodcastViewmodel;
    MyOpenHelper myOpenHelper;
    private List<PodcastList> PodcastList  = new ArrayList<>();
    private List<PodcastList> TopPodcastList  = new ArrayList<>();


    private RecyclerView.Adapter adapterTop, adapterRecommend;
    private TopPodcastAdapter toppodcastListAdapter;

    private RecyclerView recyclerViewTop,recyclerViewRecommend;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest1,mStringRequest2,mStringRequest3;
    private ProgressBar progressBar1,progressBar2,progressBar3;
    private ViewPager2 viewPager2;
    private boolean isDataLoaded = false;

    private boolean isShowLoaded = false;


    private EditText search;
    private Toolbar toolbar;
    private Handler coverhandler = new Handler();

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //    绑定对应的viewpager

        viewPager2 = view.findViewById(R.id.cover);
        //list竖向排布
        recyclerViewTop = view.findViewById(R.id.recyclerView2);
        recyclerViewTop.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        toppodcastListAdapter = new TopPodcastAdapter(TopPodcastList);
        recyclerViewTop.setAdapter(toppodcastListAdapter);


        recyclerViewRecommend = view.findViewById(R.id.recyclerView1);
        recyclerViewRecommend.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        RecommendPodcastAdapter repodcastListAdapter = new RecommendPodcastAdapter(PodcastList);
        recyclerViewRecommend.setAdapter(repodcastListAdapter);

        progressBar1 = view.findViewById(R.id.progressBar1);
        progressBar2 = view.findViewById(R.id.progressBar2);
//        设置加载圈圈
        progressBar1.setVisibility(View.VISIBLE);
        progressBar2.setVisibility(View.VISIBLE);


//
//
        if(!isDataLoaded){
            RecommendPodcast();
            TopPodcast();
            EpisodeData();
            Creator();
            isDataLoaded = true;
        }


        if (!isShowLoaded){
            showDatabase();
            showDatabaseTop();
            isShowLoaded = true;
        }



//
//        MyOpenHelper mdb = new MyOpenHelper(this.getContext(), "PodcastList.db", null, 2);//打开数据库
//        SQLiteDatabase db = mdb.getWritableDatabase();
////        清空Podcast数据表
//        db.execSQL("delete from Podcast");
//        db.execSQL("delete from Subscribe");
//        db.execSQL("delete from TopPodcast");
//        db.execSQL("delete from Episode");
//        db.execSQL("delete from Creator");




//        删除数据表
//        db.execSQL("drop table Podcast");
//        db.execSQL("drop table Subscribe");
//        db.execSQL("drop table TopPodcast");

        progressBar1.setVisibility(View.GONE);
        progressBar2.setVisibility(View.GONE);



        toolbar = view.findViewById(R.id.home_bar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle("Home");
        }

//
        new setTitleCenter().setTitleCenter(toolbar);// 初始化ToolBar
//        new KnowledgeDatabase().setKnowledgeDatabase();// 初始化数据库
        // 绑定按钮以及事件


        search = (EditText) view.findViewById(R.id.Search);
        search.setFocusable(false);//失去焦点
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PodcastSearchActivity.class);
                startActivity(intent);
            }
        });

//        获取viewmodel并与fragment绑定

        // 初始化权限
        initPermission();
        banners();

//        sendRequest();
        return view;


    }

    private void showDatabase(){
        MyOpenHelper mdb = new MyOpenHelper(this.getContext(), "PodcastList.db", null, 2);//打开数据库

        String sqlSelect = "SELECT * FROM Podcast";
        SQLiteDatabase sd = mdb.getReadableDatabase();//获取数据库
        Cursor cursor=sd.rawQuery(sqlSelect,new String[]{});

        while(cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String title=cursor.getString(cursor.getColumnIndex("title"));
            String description=cursor.getString(cursor.getColumnIndex("description"));
            String podcast_url=cursor.getString(cursor.getColumnIndex("podcast_url"));
//            将数据库中的数据加入到PodcastList中
            PodcastList podcast=new PodcastList(id,title,podcast_url,description);
            if(podcast.getId()<=3)
            PodcastList.add(podcast);
        }
        cursor.close();

    }

    private void showDatabaseTop(){
        MyOpenHelper mdb = new MyOpenHelper(this.getContext(), "PodcastList.db", null, 2);//打开数据库

        String sqlSelect = "SELECT * FROM TopPodcast";
        SQLiteDatabase sd = mdb.getReadableDatabase();//获取数据库
        Cursor cursor=sd.rawQuery(sqlSelect,new String[]{});

        while(cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String title=cursor.getString(cursor.getColumnIndex("title"));
            String description=cursor.getString(cursor.getColumnIndex("description"));
            String podcast_url=cursor.getString(cursor.getColumnIndex("podcast_url"));
//            将数据库中的数据加入到PodcastList中
            PodcastList podcast=new PodcastList(id,title,podcast_url,description);
            TopPodcastList.add(podcast);
        }
        cursor.close();

    }

    private void Creator(){
        MyOpenHelper mdb = new MyOpenHelper(this.getContext(), "PodcastList.db", null, 2);//打开数据库
        SQLiteDatabase db = mdb.getWritableDatabase();
        ContentValues values=new ContentValues();//获取ContentValues对象
//        知行小酒馆
        values.put("id","1");
        values.put("creatorName","有知有行");
        String imgurl1_1 = "https://image.xyzcdn.net/FgB16RE8wTj6eKMglFjszvG2i3Rl.jpg@small";
        values.put("creatorImage",imgurl1_1);
        values.put("podcastId","1");
        db.insert("Creator",null,values);//插入第一条数据
        values.clear();

        values.put("id","2");
        values.put("creatorName","雨白");
        String imgurl1_2 = "https://image.xyzcdn.net/FsIRR9SioGRQmqGbjKzqVZ_AevU1.jpg@small";
        values.put("creatorImage",imgurl1_2);
        values.put("podcastId","1");
        db.insert("Creator",null,values);//插入第一条数据
        values.clear();

        values.put("id","3");
        values.put("creatorName","仝仝");
        String imgurl1_3 = "https://image.xyzcdn.net/Fomv050fX8fiekmCSuffXGDMieyp.jpg@small";
        values.put("creatorImage",imgurl1_3);
        values.put("podcastId","1");
        db.insert("Creator",null,values);//插入第一条数据
        values.clear();

        values.put("id","4");
        values.put("creatorName","一知羊");
        String imgurl1_4 = "https://image.xyzcdn.net/FkQj5rMu1lHlIJ30lRYoGoOO08j0.jpg@small";
        values.put("creatorImage",imgurl1_4);
        values.put("podcastId","1");
        db.insert("Creator",null,values);//插入第一条数据
        values.clear();

        values.put("id","5");
        values.put("creatorName","星星废话多");
        String imgurl1_5 = "https://image.xyzcdn.net/Fj-APyOivhhd-q0F6AAz3tiJfEgf.jpg@small";
        values.put("creatorImage",imgurl1_5);
        values.put("podcastId","1");
        db.insert("Creator",null,values);//插入第一条数据
        values.clear();

//        凹凸电波
        values.put("id","6");
        values.put("creatorName","荒野女巫TAKO酱");
        String imgurl2_1 = "https://image.xyzcdn.net/FtudKEZJVBSR5LsknhopVQ9jDhpr.jpg@small";
        values.put("creatorImage",imgurl2_1);
        values.put("podcastId","2");
        db.insert("Creator",null,values);//插入第一条数据
        values.clear();

        values.put("id","7");
        values.put("creatorName","散装靓仔刘刘子");
        String imgurl2_2 = "https://image.xyzcdn.net/Fggs2rzwhcEgTGH4Ai8vTdJU4H2F.jpg@small";
        values.put("creatorImage",imgurl2_2);
        values.put("podcastId","2");
        db.insert("Creator",null,values);//插入第一条数据
        values.clear();

        values.put("id","8");
        values.put("creatorName","微辣瓜子");
        String imgurl2_3 = "https://image.xyzcdn.net/FhwCNViAFKo9M1f5slx90t76Wxpx.jpg@small";
        values.put("creatorImage",imgurl2_3);
        values.put("podcastId","2");
        db.insert("Creator",null,values);//插入第一条数据
        values.clear();

//round table china
        values.put("id","9");
        values.put("creatorName","英语超声波");
        String imgurl3_1 = "https://image.xyzcdn.net/FnJkE5RZ4he6raXhwwglNDHDgLco.jpg@small";
        values.put("creatorImage",imgurl3_1);
        values.put("podcastId","3");
        db.insert("Creator",null,values);//插入第一条数据
        values.clear();

//        文化有限
        values.put("id","10");
        values.put("creatorName","杨大壹");
        String imgurl4_1 = "https://image.xyzcdn.net/Fkjwevi2YBH9pgSTusu4QhbdGQEU@small";
        values.put("creatorImage",imgurl4_1);
        values.put("podcastId","4");
        db.insert("Creator",null,values);//插入第一条数据
        values.clear();

        values.put("id","11");
        values.put("creatorName","文化有限的超哥");
        String imgurl4_2 = "https://image.xyzcdn.net/Fl5alvYi4ai0L_RlaYAxNm9qWAhY@small";
        values.put("creatorImage",imgurl4_2);
        values.put("podcastId","4");
        db.insert("Creator",null,values);//插入第一条数据
        values.clear();


        values.put("id","12");
        values.put("creatorName","星光");
        String imgurl4_3 = "https://image.xyzcdn.net/Fq4fm5frKRiqUp-aUPt-zO8R6wVd.jpg@small";
        values.put("creatorImage",imgurl4_3);
        values.put("podcastId","4");
        db.insert("Creator",null,values);//插入第一条数据
        values.clear();

//        放学以后
        values.put("id","13");
        values.put("creatorName","莫不谷");
        String imgurl5_1 = "https://image.xyzcdn.net/Fhe2W2ift40GvstsJGEIHAMaEpqp.jpg@small";
        values.put("creatorImage",imgurl5_1);
        values.put("podcastId","5");
        db.insert("Creator",null,values);//插入第一条数据
        values.clear();

        values.put("id","14");
        values.put("creatorName","霸王花木兰");
        String imgurl5_2 = "https://image.xyzcdn.net/Fu9HSJxtvv33H0nrYgXewWHj2K3s.jpg@small";
        values.put("creatorImage",imgurl5_2);
        values.put("podcastId","5");
        db.insert("Creator",null,values);//插入第一条数据
        values.clear();

        values.put("id","15");
        values.put("creatorName","金种罩_放学以后");
        String imgurl5_3 = "https://image.xyzcdn.net/Fhe2W2ift40GvstsJGEIHAMaEpqp.jpg@small";
        values.put("creatorImage",imgurl5_3);
        values.put("podcastId","5");
        db.insert("Creator",null,values);//插入第一条数据
        values.clear();

//        忽左忽右
        values.put("id","16");
        values.put("creatorName","JustPod");
        String imgurl6_1 = "https://image.xyzcdn.net/FscEdqh1XWgXuJc6mgW5NFEnKay2.jpg@small";
        values.put("creatorImage",imgurl6_1);
        values.put("podcastId","6");
        db.insert("Creator",null,values);//插入第一条数据
        values.clear();



    }

    private void RecommendPodcast(){
//        打开数据库
        MyOpenHelper mdb = new MyOpenHelper(this.getContext(), "PodcastList.db", null, 2);//打开数据库
        SQLiteDatabase db = mdb.getWritableDatabase();
        ContentValues values=new ContentValues();//获取ContentValues对象
        values.put("id","1");
        values.put("title","知行小酒馆");
        values.put("description","《知行小酒馆》是有知有行出品的一档分享投资与生活的播客节目。我们关注投资理财，更关注怎样更好地生活。在我们看来，投资成功，是我们变成一个更好的人之后，自然的结果。\n");
        String imgurl1 = "https://bts-image.xyzcdn.net/aHR0cHM6Ly9pbWFnZS54eXpjZG4ubmV0L0ZzbzZaUEhTaTYyZVpKT0xob3JjcXB4OFRFd3YuanBn.jpg@small";
        values.put("podcast_url",imgurl1);
        db.insert("Podcast",null,values);//插入第一条数据
        values.clear();

        values.put("id","2");
        values.put("title","凹凸电波");
        values.put("description","《凹凸电波》是一档打破传统认知的两性杂谈类脱口秀节目。由TAKO、黄瓜酱和刘总共同主持。希望将更多正确的，有趣的，必要的科普带给更多的当代男女。同时剖析当中有趣的情感问题，分享每个男孩女孩共有的爆笑成长经历……别着急慢慢来");
        String imgurl2 = "https://bts-image.xyzcdn.net/aHR0cHM6Ly9pbWFnZS54eXpjZG4ubmV0L0ZuYmM1cEJZeFRUU0lkV2pDbGpiMHdCRGRCMU0=@small";
        values.put("podcast_url",imgurl2);
        db.insert("Podcast",null,values);//插入第2条数据
        values.clear();

        values.put("id","3");
        values.put("title","Round Table China");
        values.put("description","Round Table is a premier English radio/podcast show, straight from Beijing.Hear what's buzzing on the Internet and the main streets, see the latest lifestyle trends, and feel the pulse of life in China, Round Table is your golden ticket. With dynamic cultural exchanges between hosts from diverse backgrounds, we take you on a journey deep into modern China.");
        String imgurl3 = "https://bts-image.xyzcdn.net/aHR0cHM6Ly9yYWRpby1yZXMuY2d0bi5jb20vaW1hZ2UvMjMxMC8xNjk3NzkzMjQxOTM5LmpwZw==.jpg@small" ;
        values.put("podcast_url",imgurl3);
        db.insert("Podcast",null,values);

        values.put("id","4");
        values.put("title", "文化有限");
        values.put("description", "「文化有限」是一档泛文化播客，每周二更新，和你分享最打动我们的作品。主播大壹、星光和超哥，是三位来自互联网和文化行业的从业者。在这个可以把“知识”做成产品售卖的时代，很多人说通过几个视频、几条音频、几篇文章，就能让人认知升级、人格跃迁。和他们相比，「文化有限」除了能给你解解闷儿以外，百无一用。我们也知道，自己对这个世界乃至我们自己，知之甚少。好在，我们希望通过这档播客提醒自己，永远对这个世界保持好奇，保持谦卑。");
        String imgurl4 = "https://bts-image.xyzcdn.net/aHR0cHM6Ly9mZGZzLnhtY2RuLmNvbS9ncm91cDg2L00wOS85My8xNS93S2c1Smw3b1ZDN2p3ek95QUFLUEJqZjRmQUEwNzguanBn.jpg@small";
        values.put("podcast_url", imgurl4);
        db.insert("Podcast", null, values);//插入第一条数据
        values.clear();

        values.put("id","5");
        values.put("title", "放学以后 After School");
        values.put("description", "和你分享学习、工作和生活的日常，也共同探寻日常之外的可能性。当我们从名义上的学校毕业以后，我们进入了更加复杂的日常生活。生活面前，我们都是终身学习者(life-long learner)：学着如何和生活交手，如何安放和成为自己。《放学以后》希望是一个这样的所在：它通过文字和声音，借由文章和播客，将分享的慰藉，共同前行的力量，以及“去创造”的自我敦促传递出去。和可能性携手同行！" );
        String imgurl5 = "https://bts-image.xyzcdn.net/aHR0cHM6Ly9kM3Qzb3pmdG1kbWgzaS5jbG91ZGZyb250Lm5ldC9zdGFnaW5nL3BvZGNhc3RfdXBsb2FkZWRfbm9sb2dvLzIxNjc5MTY4Lzg2Njc0ZTYwYTJiM2Q5OTcuanBlZw==.jpeg@small";
        values.put("podcast_url", imgurl5);
        db.insert("Podcast", null, values);//插入第2条数据
        values.clear();

        values.put("id","6");
        values.put("title", "忽左忽右");
        values.put("description", "「忽左忽右」是一档文化沙龙类播客节目，试图为中文播客听众提供基于经验视角的话题和内容。本节目由JustPod出品。曾荣获苹果播客2019年度最佳播客。");
        String imgurl6 = "https://bts-image.xyzcdn.net/aHR0cHM6Ly9qdXN0cG9kbWVkaWEuY29tL3BpYy9sZWZ0cmlnaHRsb2dvLnBuZw==.png@small";
        values.put("podcast_url", imgurl6);
        db.insert("Podcast", null, values);

    }

    private void TopPodcast() {
//        打开数据库
        MyOpenHelper mdb = new MyOpenHelper(this.getContext(), "PodcastList.db", null, 2);//打开数据库
        SQLiteDatabase db = mdb.getWritableDatabase();

        ContentValues values = new ContentValues();//获取ContentValues对象
        values.put("id","1");
        values.put("title", "文化有限");
        values.put("description", "「文化有限」是一档泛文化播客，每周二更新，和你分享最打动我们的作品。主播大壹、星光和超哥，是三位来自互联网和文化行业的从业者。在这个可以把“知识”做成产品售卖的时代，很多人说通过几个视频、几条音频、几篇文章，就能让人认知升级、人格跃迁。和他们相比，「文化有限」除了能给你解解闷儿以外，百无一用。我们也知道，自己对这个世界乃至我们自己，知之甚少。好在，我们希望通过这档播客提醒自己，永远对这个世界保持好奇，保持谦卑。");
        String imgurl1 = "https://bts-image.xyzcdn.net/aHR0cHM6Ly9mZGZzLnhtY2RuLmNvbS9ncm91cDg2L00wOS85My8xNS93S2c1Smw3b1ZDN2p3ek95QUFLUEJqZjRmQUEwNzguanBn.jpg@small";
        values.put("podcast_url", imgurl1);
        db.insert("TopPodcast", null, values);//插入第一条数据
        values.clear();

        values.put("id","2");
        values.put("title", "放学以后 After School");
        values.put("description", "和你分享学习、工作和生活的日常，也共同探寻日常之外的可能性。当我们从名义上的学校毕业以后，我们进入了更加复杂的日常生活。生活面前，我们都是终身学习者(life-long learner)：学着如何和生活交手，如何安放和成为自己。《放学以后》希望是一个这样的所在：它通过文字和声音，借由文章和播客，将分享的慰藉，共同前行的力量，以及“去创造”的自我敦促传递出去。和可能性携手同行！" );
        String imgurl2 = "https://bts-image.xyzcdn.net/aHR0cHM6Ly9kM3Qzb3pmdG1kbWgzaS5jbG91ZGZyb250Lm5ldC9zdGFnaW5nL3BvZGNhc3RfdXBsb2FkZWRfbm9sb2dvLzIxNjc5MTY4Lzg2Njc0ZTYwYTJiM2Q5OTcuanBlZw==.jpeg@small";
        values.put("podcast_url", imgurl2);
        db.insert("TopPodcast", null, values);//插入第2条数据
        values.clear();

        values.put("id","3");
        values.put("title", "忽左忽右");
        values.put("description", "「忽左忽右」是一档文化沙龙类播客节目，试图为中文播客听众提供基于经验视角的话题和内容。本节目由JustPod出品。曾荣获苹果播客2019年度最佳播客。");
        String imgurl3 = "https://bts-image.xyzcdn.net/aHR0cHM6Ly9qdXN0cG9kbWVkaWEuY29tL3BpYy9sZWZ0cmlnaHRsb2dvLnBuZw==.png@small";
        values.put("podcast_url", imgurl3);
        db.insert("TopPodcast", null, values);
    }


    private void EpisodeData() {
//        打开数据库
        MyOpenHelper mdb = new MyOpenHelper(this.getContext(), "PodcastList.db", null, 2);//打开数据库
        SQLiteDatabase db = mdb.getWritableDatabase();

        ContentValues values = new ContentValues();//获取ContentValues对象

        values.put("id","1");
        values.put("title", "E129");
        String imgurl1_1 ="https://bts-image.xyzcdn.net/aHR0cHM6Ly9pbWFnZS54eXpjZG4ubmV0L0ZvdDFDMjhwdko3Q29iZmVCN2JwQS1faTEwOW0ucG5n.png@small";
        values.put("image_url", imgurl1_1);
        String audio_url1_1 = "https://media.xyzcdn.net/loUxe7vKG98ggUwTb7gFyjKURzEg.m4a";
        values.put("audio_url", audio_url1_1);
        values.put("description", "我，长期投资 A 股，真的能赚钱吗？这里有一份有效期 30 年以上的答卷");
        values.put("podcastId",1);
        db.insert("Episode", null, values);
        values.clear();

        values.put("id","2");
        values.put("title", "E128");
        String imgurl1_2 ="https://bts-image.xyzcdn.net/aHR0cHM6Ly9pbWFnZS54eXpjZG4ubmV0L0ZzbzZaUEhTaTYyZVpKT0xob3JjcXB4OFRFd3YuanBn.jpg@small";
        values.put("image_url", imgurl1_2);
        String audio_url1_2 = "https://media.xyzcdn.net/ln_6ev6FidC_dwOL2Fv5vUcuRsLB.m4a";
        values.put("audio_url", audio_url1_2);
        values.put("description", "很难，但没上班难！这间 10 平方的植物店，我还要再开 100 年（附职场人到老板的思维转换心得");
        values.put("podcastId",1);
        db.insert("Episode", null, values);
        values.clear();

        values.put("id","3");
        values.put("title", "海龟汤");
        String imgurl2_1 ="https://bts-image.xyzcdn.net/aHR0cHM6Ly9pbWFnZS54eXpjZG4ubmV0L0ZrdHVhZEdJRTE4TFpTMzNZSXhzLS1BMEVLbVgucG5n.png@small";
        values.put("image_url", imgurl2_1);
        String audio_url2_1 = "https://media.xyzcdn.net/luwLdu7reaBv940jF6h42KBL2BgO.m4a";
        values.put("audio_url", audio_url2_1);
        values.put("description", "“一人不进庙二人不观井”，我终于明白了……");
        values.put("podcastId",2);
        db.insert("Episode", null, values);
        values.clear();

        values.put("id","4");
        values.put("title", "Round Table China");
        String imgurl3_1 ="https://bts-image.xyzcdn.net/aHR0cHM6Ly9yYWRpby1yZXMuY2d0bi5jb20vaW1hZ2UvMjMxMC8xNjk3NzkzMjQxOTM5LmpwZw==.jpg@small";
        values.put("image_url", imgurl3_1);
        String audio_url3_1 = "https://media.xyzcdn.net/5e2aadff418a84a046540982/ljpOYZB7Xpc-sSDJzwI5SkAu6GYR.mp3";
        values.put("audio_url", audio_url3_1);
        values.put("description", "China's top 10 scientific advances of 2023");
        values.put("podcastId",3);
        db.insert("Episode", null, values);
        values.clear();


        values.put("id","5");
        values.put("title", "Vol.219");
        String imgurl4 = "https://bts-image.xyzcdn.net/aHR0cHM6Ly9mZGZzLnhtY2RuLmNvbS9zdG9yYWdlcy8xZjY2LWF1ZGlvZnJlZWhpZ2hxcHMvODMvQ0EvQ0t3UklKRUVfTGs4QUFFMmxRRGNUV00wLmpwZWc=.jpeg@small";
        values.put("image_url", imgurl4);
        String audio_url4_1 = "https://24098c34222000230000000000000095.qnqcdn.net:22443/qn-ZdRU1JNtlIiFWIBAkVCUkI1EnGmQUMT4.audio.xmcdn.com/storages/6ad1-audiofreehighqps/FA/33/GKwRIRwJsN3VAuo05wKtcGNs-aacv2-48K.m4a";
        values.put("audio_url", audio_url4_1);
        values.put("description", "孔雀菩提：不知道往哪走的时候，我就抬头看天");
        values.put("podcastId",4);
        values.put("download",0);
        db.insert("Episode", null, values);//插入第一条数据
        values.clear();

        values.put("id","6");
        values.put("title", "Vol.218");
        values.put("image_url", imgurl4);
        String audio_url4_2 = "https://24098c54b01000330000000100000030.qnqcdn.net:22443/qn-CRND8UgWOIiFWIBAkVCUkI1EnGmQUMT4.audio.xmcdn.com/storages/7c78-audiofreehighqps/20/40/GKwRIW4JqH9YAwM4vgKqJwy3-aacv2-48K.m4a";
        values.put("audio_url", audio_url4_2);
        values.put("description", "都柏林人：如何在“瘫痪”的生活里灵光乍现？");
        values.put("podcastId",4);
        db.insert("Episode", null, values);
        values.clear();

        values.put("id","7");
        values.put("title", "Vol.217");
        values.put("image_url", imgurl4);
        String audio_url4_3 = "https://1882136135.qnqcdn.net:22443/qn-k5JBOOjrvIiFWIBAkVCUkI1EnGmQUMT4.audio.xmcdn.com/storages/60c8-audiofreehighqps/60/C2/GKwRIaIJnqa3Adh-IgKndX64-aacv2-48K.m4a";
        values.put("audio_url", audio_url4_3);
        values.put("description", "情人节特辑：爱是最小单位的共产主义");
        values.put("podcastId",4);
        db.insert("Episode", null, values);

//        放学以后
        values.put("id","8");
        values.put("title", "40");
        String imgurl5_1 = "https://bts-image.xyzcdn.net/aHR0cHM6Ly9kM3Qzb3pmdG1kbWgzaS5jbG91ZGZyb250Lm5ldC9zdGFnaW5nL3BvZGNhc3RfdXBsb2FkZWRfZXBpc29kZS8yMTY3OTE2OC8yMTY3OTE2OC0xNzA4ODc2NzM1NzczLTJkMjQ0MDQ1YzdjMmEuanBn.jpg@small";
        values.put("image_url", imgurl5_1);
        String audio_url5_1 = "https://media.xyzcdn.net/6024c3e5bc1c2d25787e74a5/lotAEWBGfQ-qEQQ-5XbP5o4Tm_ZY.mp3";
        values.put("audio_url", audio_url5_1);
        values.put("description", "收集“活着也很值得”的瞬间，让生命力相互点燃");
        values.put("podcastId",5);
        values.put("download",0);
        db.insert("Episode", null, values);//插入第一条数据
        values.clear();

        values.put("id","9");
        values.put("title", "39");
        String imgurl5_2 = "https://bts-image.xyzcdn.net/aHR0cHM6Ly9kM3Qzb3pmdG1kbWgzaS5jbG91ZGZyb250Lm5ldC9zdGFnaW5nL3BvZGNhc3RfdXBsb2FkZWRfZXBpc29kZS8yMTY3OTE2OC8yMTY3OTE2OC0xNzAzNTA2NjU4MDE1LTljY2ZjNjQwZWM4NTcuanBn.jpg@small";
        values.put("image_url", imgurl5_2);
        String audio_url5_2 = "https://media.xyzcdn.net/6024c3e5bc1c2d25787e74a5/lvmFYZ__ghrHgBNBWX1Mi5UN2IqO.mp4a";
        values.put("audio_url", audio_url5_2);
        values.put("description", "啊哈！对生活的洞察，对自我的接纳");
        values.put("podcastId",5);
        db.insert("Episode", null, values);
        values.clear();

        values.put("id","10");
        values.put("title", "番外");
        String imgurl6_1 = "https://bts-image.xyzcdn.net/aHR0cHM6Ly9qdXN0cG9kbWVkaWEuY29tL3BpYy9sZWZ0cmlnaHRsb2dvLnBuZw==.png@small";
        values.put("image_url", imgurl6_1);
        String audio_url6_1 = "https://media.xyzcdn.net/5e4ee557418a84a0466737b7/llQUa-FOL1z3KDm_iVWtUCS_inxN.mp3";
        values.put("audio_url", audio_url6_1);
        values.put("description", "《周处除三害》背后的台湾黑道往事");
        values.put("podcastId",6);
        db.insert("Episode", null, values);
        values.clear();

        values.put("id","11");
        values.put("title", "309");
        String imgurl6_2 = "https://bts-image.xyzcdn.net/aHR0cHM6Ly9qdXN0cG9kbWVkaWEuY29tL3BpYy9sZWZ0cmlnaHRsb2dvLnBuZw==.png@small";
        values.put("image_url", imgurl6_2);
        String audio_url6_2 = "https://media.xyzcdn.net/5e4ee557418a84a0466737b7/ljk4kGhSk0t4jamdq_D5UWN6FxSD.mp3";
        values.put("audio_url", audio_url6_2);
        values.put("description", "不止网红：从纳瓦利内看当代俄国的另一面");
        values.put("podcastId",6);
        db.insert("Episode", null, values);





    }


    private void banners() {
        List<CoverItem> coverItems = new ArrayList<>();
        coverItems.add(new CoverItem(R.drawable.cover1));
        coverItems.add(new CoverItem(R.drawable.cover2));
        coverItems.add(new CoverItem(R.drawable.cover3));
        coverItems.add(new CoverItem(R.drawable.cover4));
        viewPager2.setAdapter(new CoverAdapter(viewPager2,coverItems));
        viewPager2.setClipToPadding(false);
//        设置页面预加载数量
        viewPager2.setOffscreenPageLimit(4);
        viewPager2.setClipChildren(false);
//        防止过度滚动
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
//        页面左右边距
        compositePageTransformer.addTransformer(new MarginPageTransformer(25));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1- Math.abs(position);
//                设置Y轴缩放比例，基础值为0.5,再根据位置动态调整
                page.setScaleY(0.5f+r*0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                coverhandler.removeCallbacks(coverRunnable);
            }
        });


    }

    private Runnable coverRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };




    private void initPermission() {

        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE

        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), perm)) {
                toApplyList.add(perm);
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), toApplyList.toArray(tmpList), 123);
        }
    }
    @Override
    public void onEvent(String s, String s1, byte[] bytes, int i, int i1) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }




}
