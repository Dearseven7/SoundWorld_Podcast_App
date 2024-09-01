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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soundworld.Database.Creator;
import com.example.soundworld.PodcastDetailActivity;
import com.example.soundworld.R;

import java.util.List;

public class CreatorAdapter extends RecyclerView.Adapter<CreatorAdapter.ViewHolder> {
    private List<Creator> mcreator;

    Context context;

    public CreatorAdapter(List<Creator> mcreator) {
        this.mcreator = mcreator;
    }

    @NonNull
    @Override
    public CreatorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_creator,parent,false);

        return new ViewHolder(inflate);
    }

    @Override

    public void onBindViewHolder(@NonNull CreatorAdapter.ViewHolder holder, int position) {
//        holder.creatorImge.setImageResource(mcreator.get(position).getImages());
        Glide.with(context).load(mcreator.get(position).getCreatorImage()).into(holder.creatorImge);
        holder.creatorName.setText(mcreator.get(position).getCreatorName());
        holder.itemView.setOnClickListener(v ->  {
            Toast.makeText(context, mcreator.get(position).getCreatorName(), Toast.LENGTH_SHORT).show();

        });
    }

    @Override
    public int getItemCount() {
        return mcreator.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView creatorImge;
        TextView creatorName;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            creatorName = itemView.findViewById(R.id.creator_name);
            creatorImge = itemView.findViewById(R.id.creatorImg);

        }
    }
}
