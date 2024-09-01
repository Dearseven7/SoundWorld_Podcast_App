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
import com.example.soundworld.PodcastDetailActivity;
import com.example.soundworld.PodcastPlayerActivity;
import com.example.soundworld.R;
import com.example.soundworld.meActivity.FavouriteActivity;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder>{
    private List<EpisodeList> favpisodeList;
    Context context;

    public FavouriteAdapter(List<EpisodeList> favepisodeList) {
        this.favpisodeList = favepisodeList;
    }

    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(favpisodeList.get(position).getCover()).into(holder.fepisode_image);
        holder.fepisode_title.setText(favpisodeList.get(position).getTitle());
        holder.fepisode_description.setText(favpisodeList.get(position).getDescription());


        holder.fplayer.setOnClickListener(v ->  {
            Intent intent = new Intent(holder.fplayer.getContext(), PodcastPlayerActivity.class);
//            intent.putExtra("episodeID", playepisodeList.size());
            intent.putExtra("episodeID", favpisodeList.get(position).getId() );
            context.startActivity(intent);
        });

        holder.fav_spark.setOnClickListener(v ->  {
            Toast.makeText(this.context, "已点亮该单集！", Toast.LENGTH_SHORT).show();

        });

    }

    @Override
    public int getItemCount() {
        return favpisodeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fepisode_image,fav_spark;
        TextView fepisode_title;
        TextView fepisode_description;
        Button fplayer;



        public ViewHolder(View itemView) {
            super(itemView);

            fepisode_image = itemView.findViewById(R.id.favepisode_cover);
            fepisode_title = itemView.findViewById(R.id.favepisode_title);
            fepisode_description = itemView.findViewById(R.id.favepisode_description);
            fplayer = itemView.findViewById(R.id.favbutton_player);
            fav_spark = itemView.findViewById(R.id.favspark);


        }
    }

    public void clearData(){
        favpisodeList.clear();
        notifyDataSetChanged();
    }
}
