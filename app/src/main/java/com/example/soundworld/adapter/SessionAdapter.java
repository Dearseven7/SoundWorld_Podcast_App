package com.example.soundworld.adapter;
/*
 * @author: Dearseven
 * @description:
 */

import android.content.ContentQueryMap;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundworld.Database.Session;
import com.example.soundworld.R;

import java.util.ArrayList;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.viewholder>{
    ArrayList<Session> list;
    Context context;



    public SessionAdapter(ArrayList<Session> list,Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_session,parent,false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Session session = list.get(position);
        holder.sessionImg.setImageResource(session.getSession());
        holder.title.setText(session.getSession_title());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView sessionImg;
        TextView title;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            sessionImg = itemView.findViewById(R.id.session);
            title = itemView.findViewById(R.id.session_title);
        }
    }

    public void clearData(){
        list.clear();
        notifyDataSetChanged();
    }
}

