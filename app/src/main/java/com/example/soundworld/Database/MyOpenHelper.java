package com.example.soundworld.Database;
/*
 * @author: Dearseven
 * @description:
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.security.spec.ECPoint;

public class MyOpenHelper extends SQLiteOpenHelper {



    /*integer 整型，real 浮点型，primary key 主键，autoincrement 自增长，text 文本类型，blob 二进制数，*/
//    创建数据库podcast表
        public static final String CREATE_Podcast="create table Podcast("
                +"id integer primary key autoincrement,"
                +"title text,"
                +"description text,"
                +"podcast_url text,"
                +"subscribe integer default 0,"
                +"love integer default 0)";



    public static final String CREATE_Subscribe ="create table Subscribe("
            +"id integer primary key autoincrement,"
            +"title text,"
            +"description text,"
            +"podcast_url text,"
            +"subscribe integer default 0,"
            +"love integer default 0)";


        public static final String CREATE_Episode="create table Episode("
            +"id integer primary key autoincrement,"
            +"title text,"
            +"image_url text,"
            +"audio_url text,"
            +"description text,"
            +"podcastId integer,"
            +"star integer default 0,"
            +"download integer default 0,"
            +"love integer default 0)";

    public static final String CREATE_Creator="create table Creator("
            +"id integer primary key autoincrement,"
            +"creatorName text,"
            +"creatorImage text,"
            +"podcastId integer)";

    public static final String CREATE_TopPodcast="create table TopPodcast("
            +"id integer primary key autoincrement,"
            +"title text,"
            +"description text,"
            +"podcast_url text,"
            +"subscribe integer default 0,"
            +"love integer default 0)";



    private Context mContext;

        public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            mContext=context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_Podcast);
            sqLiteDatabase.execSQL(CREATE_Subscribe);
            sqLiteDatabase.execSQL(CREATE_Episode);
            sqLiteDatabase.execSQL(CREATE_TopPodcast);
            sqLiteDatabase.execSQL(CREATE_Creator);


            Toast.makeText(mContext,"create success!", Toast.LENGTH_SHORT).show();
        }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists Podcast");
        sqLiteDatabase.execSQL("drop table if exists Subscribe");
        sqLiteDatabase.execSQL("drop table if exists Episode");
        sqLiteDatabase.execSQL("drop table if exists TopPodcast");
        sqLiteDatabase.execSQL("drop table if exists Creator");
        onCreate(sqLiteDatabase);
    }


    public Creator getCreator(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id","creatorName","creatorImage","podcastId"};
        String selection = "podcastId" + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                "Creator",
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Creator result = null;
        if (cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("creatorName");
            int imageIndex = cursor.getColumnIndex("description");
            int podcastIdIndex = cursor.getColumnIndex("podcastId");


            int retrievedId = cursor.getInt(idIndex);
            String retrievedName = cursor.getString(nameIndex);
            String retrievedImage = cursor.getString(imageIndex);
            int retrievedPodcastId = cursor.getInt(podcastIdIndex);
            result = new Creator(retrievedId, retrievedName, retrievedImage, retrievedPodcastId);
        }
        cursor.close();
        db.close();
        return result;
    }

    public PodcastList getDataById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id","title","description","podcast_url","subscribe","love"};
        String selection = "id" + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                "Podcast",
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        PodcastList result = null;
        if (cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex("id");
            int titleIndex = cursor.getColumnIndex("title");
            int descriptionIndex = cursor.getColumnIndex("description");
            int urlIndex = cursor.getColumnIndex("podcast_url");


            int retrievedId = cursor.getInt(idIndex);
            String retrievedTitle = cursor.getString(titleIndex);
            String retrievedDescription = cursor.getString(descriptionIndex);
            String retrievedUrl = cursor.getString(urlIndex);

            int subscribeIndex = cursor.getColumnIndex("subscribe");
            Boolean retrievedSubscribe = cursor.getInt(subscribeIndex)>0;
            int loveIndex = cursor.getColumnIndex("love");
            Boolean retrievedLove = cursor.getInt(loveIndex)>0;

            result = new PodcastList(retrievedId, retrievedTitle, retrievedDescription, retrievedUrl,retrievedSubscribe,retrievedLove);

        }
        cursor.close();
        db.close();
        return result;
        }


    public EpisodeList getDataByIdEpi(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id","title","image_url","audio_url","description","podcastId","star","download","love"};
        String selection = "id" + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                "Episode",
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        EpisodeList result = null;
        if (cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex("id");
            int titleIndex = cursor.getColumnIndex("title");
            int descriptionIndex = cursor.getColumnIndex("description");
            int audiourlIndex = cursor.getColumnIndex("audio_url");
            int imageurlIndex = cursor.getColumnIndex("image_url");
            int podcastidIndex = cursor.getColumnIndex("podcastId");
            int starIndex = cursor.getColumnIndex("star");
            int downloadIndex = cursor.getColumnIndex("download");
            int loveIndex = cursor.getColumnIndex("love");

            int retrievedId = cursor.getInt(idIndex);
            String retrievedTitle = cursor.getString(titleIndex);
            String retrievedDescription = cursor.getString(descriptionIndex);
            String retrievedimageUrl = cursor.getString(imageurlIndex);
            String retrievedaudioUrl = cursor.getString(audiourlIndex);
            int retrievedPodcastid = cursor.getInt(podcastidIndex);
            Boolean retrievedStar = cursor.getInt(starIndex)>0;
            Boolean retrievedDownload = cursor.getInt(downloadIndex)>0;
            Boolean retrievedLove = cursor.getInt(loveIndex)>0;

            result = new EpisodeList(retrievedId, retrievedTitle, retrievedimageUrl, retrievedaudioUrl,retrievedDescription,retrievedPodcastid,retrievedStar,retrievedDownload,retrievedLove);

        }
        cursor.close();
        db.close();
        return result;
    }


    public PodcastList getDataByIdTop(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id","title","description","podcast_url"};
        String selection = "id" + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                "TopPodcast",
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        PodcastList result = null;
        if (cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex("id");
            int titleIndex = cursor.getColumnIndex("title");
            int descriptionIndex = cursor.getColumnIndex("description");
            int urlIndex = cursor.getColumnIndex("podcast_url");

            int retrievedId = cursor.getInt(idIndex);
            String retrievedTitle = cursor.getString(titleIndex);
            String retrievedDescription = cursor.getString(descriptionIndex);
            String retrievedUrl = cursor.getString(urlIndex);

            result = new PodcastList(retrievedId, retrievedTitle, retrievedDescription, retrievedUrl);

        }
        cursor.close();
        db.close();
        return result;
    }

//添加到订阅到播客
    public PodcastList getDataByIdSub(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id","title","description","podcast_url"};
        String selection = "id" + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                "Subscribe",
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        PodcastList result = null;
        if (cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex("id");
            int titleIndex = cursor.getColumnIndex("title");
            int descriptionIndex = cursor.getColumnIndex("description");
            int urlIndex = cursor.getColumnIndex("podcast_url");

            int retrievedId = cursor.getInt(idIndex);
            String retrievedTitle = cursor.getString(titleIndex);
            String retrievedDescription = cursor.getString(descriptionIndex);
            String retrievedUrl = cursor.getString(urlIndex);

            result = new PodcastList(retrievedId, retrievedTitle, retrievedDescription, retrievedUrl);

        }
        cursor.close();
        db.close();
        return result;
    }

//    插入数据至订阅
    public long insertDataSub(PodcastList podcastList) {
        SQLiteDatabase sdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // 将DataRow对象的字段值放入ContentValues
//        values.put("id", podcastList.getId());
        values.put("title", podcastList.getTitle());
        values.put("description", podcastList.getDescription());
        values.put("url", podcastList.getImage_url());

        // 插入数据
        long newRowId = sdb.insert("Subscribe", null, values);
        sdb.close();
        return newRowId;
    }



    public Cursor getAllPodcast() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + "Podcast", null);
    }


    public PodcastList getDataByTitle(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id","title","description","podcast_url","subscribe","love"};
        String selection = "title" + " = ?";
        String[] selectionArgs = {String.valueOf(title)};

        Cursor cursor = db.query(
                "Podcast",
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        PodcastList result = null;
        if (cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex("id");
            int titleIndex = cursor.getColumnIndex("title");
            int descriptionIndex = cursor.getColumnIndex("description");
            int urlIndex = cursor.getColumnIndex("podcast_url");

            int retrievedId = cursor.getInt(idIndex);
            String retrievedTitle = cursor.getString(titleIndex);
            String retrievedDescription = cursor.getString(descriptionIndex);
            String retrievedUrl = cursor.getString(urlIndex);

            int subscribeIndex = cursor.getColumnIndex("subscribe");
            Boolean retrievedSubscribe = cursor.getInt(subscribeIndex)>0;
            int loveIndex = cursor.getColumnIndex("love");
            Boolean retrievedLove = cursor.getInt(loveIndex)>0;

            result = new PodcastList(retrievedId, retrievedTitle, retrievedDescription, retrievedUrl,retrievedSubscribe,retrievedLove);


        }
        cursor.close();
        db.close();
        return result;
    }


//    public Cursor searchDataByTitle(String column, String value) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns = {"id", "title","description"};
//        String selection = column + "=?";
//        String[] selectionArgs = {value};
//
//        return db.query("Podcast", columns, selection, selectionArgs, null, null, null);
//    }
//


    public PodcastList getDataByTitleSub(String title) {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id","title","description","podcast_url","subscribe","love"};
        String selection = "title" + " = ?";
        String[] selectionArgs = {String.valueOf(title)};

        Cursor cursor = db.query(
                "Subscribe",
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        PodcastList result = null;
        if (cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex("id");
            int titleIndex = cursor.getColumnIndex("title");
            int descriptionIndex = cursor.getColumnIndex("description");
            int urlIndex = cursor.getColumnIndex("podcast_url");

            int retrievedId = cursor.getInt(idIndex);
            String retrievedTitle = cursor.getString(titleIndex);
            String retrievedDescription = cursor.getString(descriptionIndex);
            String retrievedUrl = cursor.getString(urlIndex);
            int subscribeIndex = cursor.getColumnIndex("subscribe");
            Boolean retrievedSubscribe = cursor.getInt(subscribeIndex)>0;
            int loveIndex = cursor.getColumnIndex("love");
            Boolean retrievedLove = cursor.getInt(loveIndex)>0;

            result = new PodcastList(retrievedId, retrievedTitle, retrievedDescription, retrievedUrl,retrievedSubscribe,retrievedLove);


        }
        cursor.close();
        db.close();
        return result;

    }


    public int updatePodcast(int id, ContentValues updatedValues) {
        SQLiteDatabase db = this.getWritableDatabase();

        // 更新数据
        int rowsAffected = db.update("Podcast", updatedValues, "id" + " = ?", new String[]{String.valueOf(id)});

        db.close();
        return rowsAffected;
    }

    public int updateEpisode(int id, ContentValues updatedValues) {
        SQLiteDatabase db = this.getWritableDatabase();

        // 更新数据
        int rowsAffected = db.update("Episode", updatedValues, "id" + " = ?", new String[]{String.valueOf(id)});

        db.close();
        return rowsAffected;
    }


}

