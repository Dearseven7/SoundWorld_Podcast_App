package com.example.soundworld;
/*
 * @author: Dearseven
 * @description:
 */

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Animatable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.soundworld.Database.EpisodeList;
import com.example.soundworld.Database.MyOpenHelper;
import com.example.soundworld.Database.PodcastList;

import java.io.IOException;

public class PodcastPlayerActivity extends AppCompatActivity {
    MyOpenHelper myOpenHelper;


    private int mepisodeID;
    Button playButton;

    SeekBar startBar,endBar;
    TextView startNumber,endNumber,epititle;

    ImageView episodeCover,backdetail,addfavourite,addstar,adddownload;
    EpisodeList playepisodeList;


    private boolean isFirstclickAddlove = true;
    private boolean isFirstclickAddstar = true;


    //    MediaPlayer podcast;
    Animatable animation;
    int PodcastTotalTime;
    MediaPlayer podcast = new MediaPlayer();


    protected void onCreate(Bundle savedInstanceState) {
//        设置没有标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_podcast);
        Intent intent = getIntent();
        mepisodeID = intent.getIntExtra("episodeID", 0); // defaultValue是你指定的默认值


//        一一绑定相关的控件
        episodeCover = findViewById(R.id.episodeCover2);
        startNumber = findViewById(R.id.startNumber);
        endNumber = findViewById(R.id.endNumber);
        epititle = findViewById(R.id.epi_title);

        playButton = findViewById(R.id.buttonStart);
        startBar = findViewById(R.id.playerBar);
        backdetail = findViewById(R.id.backdetail);
        addfavourite = findViewById(R.id.add_favourite);
        addstar = findViewById(R.id.addstar);

        adddownload = findViewById(R.id.play_download);

        adddownload.setOnClickListener(v ->  {
            Toast.makeText(this, "该单集将加入下载列表！", Toast.LENGTH_SHORT).show();
        });
        MyOpenHelper mdb = new MyOpenHelper(this, "PodcastList.db", null, 2);//打开数据库

        EpisodeList findepisode = mdb.getDataByIdEpi(mepisodeID);
        boolean islove = findepisode.getLove();
        boolean isstar = findepisode.getStar();
        if(findepisode != null && islove == false ){
            addfavourite.setImageResource(R.drawable.baseline_favorite_border_24);
        }else {
            addfavourite.setImageResource(R.drawable.baseline_favorite_24);
        }

        if(findepisode != null && isstar == false ){
            addstar.setImageResource(R.drawable.baseline_star_border_24);
        }else {
            addstar.setImageResource(R.drawable.baseline_star_24);
        }

        addstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isstar ==true){
                    if(isFirstclickAddstar) {
                        ContentValues updatedValues = new ContentValues();
                        updatedValues.put("star", 0);
                        mdb.updateEpisode(mepisodeID, updatedValues);
                        addstar.setImageResource(R.drawable.baseline_star_border_24);
                        Toast.makeText(PodcastPlayerActivity.this, "已取消收藏该单集～", Toast.LENGTH_SHORT).show();
                        isFirstclickAddstar = false;
                    }else{
                        ContentValues updatedValues = new ContentValues();
                        updatedValues.put("star",1);
                        mdb.updateEpisode(mepisodeID,updatedValues);
                        addstar.setImageResource(R.drawable.baseline_star_24);
                        Toast.makeText(PodcastPlayerActivity.this, "欢迎收藏该单集播客！", Toast.LENGTH_SHORT).show();
                        isFirstclickAddstar =true;
                    }

                }else if (isstar ==false) {
                    if( isFirstclickAddstar ){
                        ContentValues updatedValues = new ContentValues();
                        updatedValues.put("star", 1);
                        mdb.updateEpisode(mepisodeID, updatedValues);
                        addstar.setImageResource(R.drawable.baseline_star_24);
                        Toast.makeText(PodcastPlayerActivity.this, "欢迎收藏该单集播客！", Toast.LENGTH_SHORT).show();
                        isFirstclickAddstar = false;
                    }else {
                        ContentValues updatedValues = new ContentValues();
                        updatedValues.put("star", 0);
                        mdb.updateEpisode(mepisodeID, updatedValues);
                        addstar.setImageResource(R.drawable.baseline_star_border_24);
                        Toast.makeText(PodcastPlayerActivity.this, "已取消收藏该单集～", Toast.LENGTH_SHORT).show();
                        isFirstclickAddstar = true;
                    }
                }

            }
        });


        addfavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (islove ==true){
                    if(isFirstclickAddlove) {
                        ContentValues updatedValues = new ContentValues();
                        updatedValues.put("love", 0);
                        mdb.updateEpisode(mepisodeID, updatedValues);
                        addfavourite.setImageResource(R.drawable.baseline_favorite_border_24);
                        Toast.makeText(PodcastPlayerActivity.this, "已取消喜欢该单集～", Toast.LENGTH_SHORT).show();
                        isFirstclickAddlove = false;
                    }else{
                        ContentValues updatedValues = new ContentValues();
                        updatedValues.put("love",1);
                        mdb.updateEpisode(mepisodeID,updatedValues);
                        addfavourite.setImageResource(R.drawable.baseline_favorite_24);
                        Toast.makeText(PodcastPlayerActivity.this, "欢迎添加至喜欢，您可以在个人主页查看喜欢的播客单集！", Toast.LENGTH_SHORT).show();
                        isFirstclickAddlove =true;
                    }

                }else if (islove ==false) {
                    if( isFirstclickAddlove ){
                        ContentValues updatedValues = new ContentValues();
                        updatedValues.put("love", 1);
                        mdb.updateEpisode(mepisodeID, updatedValues);
                        addfavourite.setImageResource(R.drawable.baseline_favorite_24);
                        Toast.makeText(PodcastPlayerActivity.this, "欢迎添加至喜欢，您可以在个人主页查看喜欢的播客单集！", Toast.LENGTH_SHORT).show();
                    isFirstclickAddlove = false;
                    }else {
                        ContentValues updatedValues = new ContentValues();
                        updatedValues.put("love", 0);
                        mdb.updateEpisode(mepisodeID, updatedValues);
                        addfavourite.setImageResource(R.drawable.baseline_favorite_border_24);
                        Toast.makeText(PodcastPlayerActivity.this, "已取消喜欢该单集～", Toast.LENGTH_SHORT).show();
                        isFirstclickAddlove = true;
                    }
                }

            }
        });

        backdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });




        myOpenHelper = new MyOpenHelper(this, "PodcastList.db", null, 2);
        SQLiteDatabase db = myOpenHelper.getReadableDatabase();


        EpisodeList playepisodeList = myOpenHelper.getDataByIdEpi(mepisodeID);

        if(mepisodeID == 0){
            Log.d("YourActivity", "ID NULL");

        }

        if (playepisodeList != null) {

            int retrievedId = playepisodeList.getId();
            String description1 = playepisodeList.getDescription();
            // 使用检索到的数据进行操作
            Log.d("YourActivity", "ID: " + retrievedId + ", Data: " + description1);
        }



        //        数据库查询根据id判断并获取播客
        EpisodeList episodeList1 = new EpisodeList(mepisodeID,playepisodeList.getTitle(),playepisodeList.getCover(),playepisodeList.getUrl(),playepisodeList.getDescription(),playepisodeList.getPodcastId(),playepisodeList.getStar(),playepisodeList.getDownload(),playepisodeList.getLove());

        epititle.setText(episodeList1.getDescription());
        Glide.with(this).load(episodeList1.getCover()).into(episodeCover);

//        网络url播放音频
        podcast.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            podcast.setDataSource(episodeList1.getUrl());
//            podcast.setDataSource("https://461429074.qnqcdn.net:22443/qn-RBvDJ8MSsIiFWIBAkVCUkI1EnGmQUMT4.audio.xmcdn.com/storages/7c78-audiofreehighqps/20/40/GKwRIW4JqH9YAwM4vgKqJwy3-aacv2-48K.m4a");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            podcast.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



//        podcast = MediaPlayer.create(this,R.raw.wenhuayouxian);
        podcast.setLooping(true);
        podcast.seekTo(0);
        podcast.setVolume(0.5f,0.5f);
        PodcastTotalTime = podcast.getDuration();
        startBar.setMax(PodcastTotalTime);
        startBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    podcast.seekTo(progress);
                    startBar.setProgress(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Up date song time line
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (podcast != null){
                    try {
                        Message message = new Message();
                        message.what = podcast.getCurrentPosition();
                        handler.sendMessage(message);
                        Thread.sleep(1000);
                    }catch (InterruptedException ignored){
                    }
                }
            }

        }).start();


    }




//    initdata



//    播放按钮事件
    public void PlayButton(View view){
        if (!podcast.isPlaying()){
            //Stopped
            podcast.start();
            playButton.setBackgroundResource(R.drawable.baseline_pause_24);
        }else {
            //Played
            podcast.pause();
            episodeCover.clearAnimation();
            playButton.setBackgroundResource(R.drawable.baseline_play_arrow_24);
        }
    }


//    时间进度条处理
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @SuppressLint("SetText18n")
        @Override
        public void handleMessage(Message message){
//            获取后台线程的时间消息
            int SeekBarPosition = message.what;
            //获取当前的位置
            startBar.setProgress(SeekBarPosition);

            //时间格式化，将毫秒表示成时间显示
            String Time = createTimeText(SeekBarPosition);
            startNumber.setText(Time);
            //剩余的时间
            String remainingTime = createTimeText(PodcastTotalTime - SeekBarPosition);
            endNumber.setText("-" +remainingTime);
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler2 = new Handler(){
        @SuppressLint("SetText18n")
        @Override
        public void handleMessage(Message message){
            int SeekBarPosition = message.what;

            //获取当前的位置
            startBar.setProgress(SeekBarPosition);

            //Update Labels
            String Time = createTimeText2(SeekBarPosition);
            startNumber.setText(Time);
            //剩余的时间
            String remainingTime = createTimeText2(PodcastTotalTime - SeekBarPosition);
            endNumber.setText("-" +remainingTime);
        }
    };

    public void back15Sec(View view){
        if (podcast != null) {
            int position = podcast.getCurrentPosition();
            if (position > 15000) {
                position -= 15000;
                Toast.makeText(PodcastPlayerActivity.this, "⏪后退15s", Toast.LENGTH_SHORT).show();
            } else {
                position = 0;
            }
            podcast.seekTo(position);
        }
    }

    public void forward15Sec(View view){
        if (podcast != null){
        int position = podcast.getCurrentPosition();
            position+=15000;
            Toast.makeText(PodcastPlayerActivity.this, "⏩快进15s", Toast.LENGTH_SHORT).show();
            podcast.seekTo(position);
        }
    }



    public String createTimeText(int time){
        String timeText;
//        设置分钟
        int min = time / 1000 /60;
//        秒钟
        int sec = time / 1000 % 60;
//        将整数型的时间数字转化为保留两位数
        String minStr = String.format("%2d",min).replace(" ","0");

        String secStr = String.format("%2d",sec).replace(" ","0");
        timeText = minStr + ":"+secStr;
        return timeText;

    }



    public String createTimeText2(int time){
        int time2;
        time2 = time +15;
        String timeText;
//        设置分钟
        int min = time2 / 1000 /60;
//        秒钟
        int sec = time2 / 1000 % 60;
//        将整数型的时间数字转化为保留两位数
        String minStr = String.format("%2d",min).replace(" ","0");

        String secStr = String.format("%2d",sec).replace(" ","0");
        timeText = minStr + ":"+secStr;
        return timeText;

    }
}
