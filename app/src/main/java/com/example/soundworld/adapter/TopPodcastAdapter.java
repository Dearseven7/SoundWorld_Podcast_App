package com.example.soundworld.adapter;
/*
 * @author: Dearseven
 * @description:
 */

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soundworld.Database.MyOpenHelper;
import com.example.soundworld.Database.PodcastList;
import com.example.soundworld.PodcastDetailActivity;
import com.example.soundworld.R;

import java.util.List;

public class TopPodcastAdapter extends RecyclerView.Adapter<TopPodcastAdapter.ViewHolder> {
//    podcast的数据绑定
    PodcastList podcast_items;
    Context context;
    private List<PodcastList> mpodcastList;
    public TopPodcastAdapter(List<PodcastList> mpodcastList ){
        this.mpodcastList = mpodcastList;
    }

    @NonNull
    @Override
    public TopPodcastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
//        绑定对应的listpodcast
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_podcast,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPodcastAdapter.ViewHolder holder, int position) {
        holder.title.setText(mpodcastList.get(position).getTitle());
        holder.description.setText(mpodcastList.get(position).getDescription());
//
//        RequestOptions requestOptions = new RequestOptions();
////        设置圆角图片
//        requestOptions = requestOptions.transform(new CenterCrop(),new RoundedCorners(30));
        Glide.with(context).load(mpodcastList.get(position).getImage_url()).into(holder.image);
        MyOpenHelper dbHelper = new MyOpenHelper(this.context, "PodcastList.db", null, 2);//打开数据库


//        点击跳转至podcast详细界面

        holder.itemView.setOnClickListener(v ->  {
                Intent intent = new Intent(holder.itemView.getContext(), PodcastDetailActivity.class);

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            PodcastList mPodcast = dbHelper.getDataByTitle(mpodcastList.get(position).getTitle());
            int topid = mPodcast.getId();
                intent.putExtra("topid", topid);
                context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return mpodcastList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,description;
        ImageView image;
        public ViewHolder(@NonNull View view) {
            super(view);
            title = itemView.findViewById(R.id.podcast_title);
            image = itemView.findViewById(R.id.podcast_image);
            description = itemView.findViewById(R.id.description);

        }
    }


    public void clearData(){
        mpodcastList.clear();
        notifyDataSetChanged();
    }

}
