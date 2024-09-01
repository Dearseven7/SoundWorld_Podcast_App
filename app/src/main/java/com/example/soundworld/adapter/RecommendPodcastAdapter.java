package com.example.soundworld.adapter;
/*
 * @author: Dearseven
 * @description:
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soundworld.Database.PodcastList;
import com.example.soundworld.PodcastDetailActivity;
import com.example.soundworld.R;

import java.util.List;

public class RecommendPodcastAdapter extends RecyclerView.Adapter<RecommendPodcastAdapter.ViewHolder> {
//    podcast的数据绑定
    PodcastList podcast_items;
    Context context;
    private List<PodcastList> mpodcastList;
    public RecommendPodcastAdapter(List<PodcastList> mpodcastList ){
        this.mpodcastList = mpodcastList;
    }

    @NonNull
    @Override
    public RecommendPodcastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
//        绑定对应的listpodcast
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_podcast,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendPodcastAdapter.ViewHolder holder, int position) {
        holder.title.setText(mpodcastList.get(position).getTitle());
        holder.description.setText(mpodcastList.get(position).getDescription());
//
//        RequestOptions requestOptions = new RequestOptions();
////        设置圆角图片
//        requestOptions = requestOptions.transform(new CenterCrop(),new RoundedCorners(30));
        Glide.with(context).load(mpodcastList.get(position).getImage_url()).into(holder.image);


//        点击跳转至podcast详细界面

        holder.itemView.setOnClickListener(v ->  {
                Intent intent = new Intent(holder.itemView.getContext(), PodcastDetailActivity.class);
                intent.putExtra("id", mpodcastList.get(position).getId());
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




}
