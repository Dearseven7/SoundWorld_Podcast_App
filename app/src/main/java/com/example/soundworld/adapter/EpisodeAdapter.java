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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soundworld.Database.EpisodeList;
import com.example.soundworld.PodcastPlayerActivity;
import com.example.soundworld.R;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder>{
    private List<EpisodeList> playepisodeList;
    Context context;

    public EpisodeAdapter(List<EpisodeList> playepisodeList) {
        this.playepisodeList = playepisodeList;
    }

    @NonNull
    @Override
    public EpisodeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episode,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(playepisodeList.get(position).getCover()).into(holder.episode_image);
        holder.episode_title.setText(playepisodeList.get(position).getTitle());
        holder.episode_description.setText(playepisodeList.get(position).getDescription());



        holder.player.setOnClickListener(v ->  {
            Intent intent = new Intent(holder.player.getContext(), PodcastPlayerActivity.class);
//            intent.putExtra("episodeID", playepisodeList.size());
            intent.putExtra("episodeID", playepisodeList.get(position).getId() );
            context.startActivity(intent);
        });

        holder.addstar.setOnClickListener(v ->  {
            Toast.makeText(this.context, "已点亮该单集！", Toast.LENGTH_SHORT).show();

        });


    }

    @Override
    public int getItemCount() {
        return playepisodeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView episode_image,addstar;
        TextView episode_title;
        TextView episode_description;
        Button player;



        public ViewHolder(View itemView) {
            super(itemView);

            episode_image = itemView.findViewById(R.id.episode_cover);
            episode_title = itemView.findViewById(R.id.episode_title);
            episode_description = itemView.findViewById(R.id.episode_description);
            player = itemView.findViewById(R.id.button_player);
            addstar =itemView.findViewById(R.id.epistar);



        }
    }

    public void clearData(){
        playepisodeList.clear();
        notifyDataSetChanged();
    }
}
