package com.example.soundworld.adapter;
/*
 * @author: Dearseven
 * @description:
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.soundworld.Database.CoverItem;
import com.example.soundworld.R;

import java.util.List;

public class CoverAdapter extends RecyclerView.Adapter<CoverAdapter.TopPodcastViewHolder>{

    private Context context;


    private ViewPager2 viewPager2;
    private List<CoverItem> coverItems;

    public CoverAdapter(ViewPager2 viewPager2, List<CoverItem> coverItems) {
        this.viewPager2 = viewPager2;
        this.coverItems = coverItems;
    }

//    绑定对应的layout布局文件
    @NonNull
    @Override
    public CoverAdapter.TopPodcastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new TopPodcastViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_podcast_cover,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopPodcastViewHolder holder, int position) {
        holder.setImageView(coverItems.get(position));
        if(position==coverItems.size()-2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return coverItems.size();
    }

//    绑定布局文件中对应的控件
    public class TopPodcastViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public TopPodcastViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cover);
        }
        void setImageView(CoverItem topItems){
            RequestOptions requestOptions = new RequestOptions();
//            对图片设置裁剪居中
            requestOptions = requestOptions.transforms(new CenterCrop(),new RoundedCorners(60));
            Glide.with(context).load(topItems.getImage()).apply(requestOptions).into(imageView);


        }
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            coverItems.addAll(coverItems);
            notifyDataSetChanged();
        }
    };
}
