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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soundworld.Database.PodcastList;
import com.example.soundworld.PodcastDetailActivity;
import com.example.soundworld.PodcastPlayerActivity;
import com.example.soundworld.R;

import java.util.List;

public class SubscribeAdapter extends RecyclerView.Adapter <SubscribeAdapter.ViewHolder> {

    Context context;
    private List<PodcastList> subpodcastList;


    public SubscribeAdapter(List<PodcastList> subpodcastList,Context context) {
//        super();

        this.subpodcastList=subpodcastList;
        this.context = context;
    }

    @NonNull
    @Override
    public SubscribeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscribe,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscribeAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(subpodcastList.get(position).getImage_url()).into(holder.sub_episode_image);

        holder.sub_episode_title.setText(subpodcastList.get(position).getTitle());
        holder.sub_episode_description.setText(subpodcastList.get(position).getDescription());

        holder.itemView.setOnClickListener(v ->  {
            Intent intent = new Intent(holder.itemView.getContext(), PodcastDetailActivity.class);
            intent.putExtra("id", subpodcastList.get(position).getId());
            context.startActivity(intent);
        });

//        holder.player.setOnClickListener(v ->  {
//            Intent intent = new Intent(holder.itemView.getContext(), PodcastPlayerActivity.class);
//            intent.putExtra("id", subpodcastList.get(position).getId());
//            context.startActivity(intent);
//        });

    }

    @Override
    public int getItemCount() {
        return subpodcastList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView sub_episode_image;
        TextView sub_episode_title;
        TextView sub_episode_description;
//        Button player;



        public ViewHolder(View itemView) {
            super(itemView);

            sub_episode_image= itemView.findViewById(R.id.sub_episode_cover);
            sub_episode_title = itemView.findViewById(R.id.sub_episode_title);
            sub_episode_description = itemView.findViewById(R.id.sub_episode_description);
//            player = itemView.findViewById(R.id.button_player);

//            player.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });

        }
    }

    public void clearData(){
        subpodcastList.clear();
        notifyDataSetChanged();
    }


}
