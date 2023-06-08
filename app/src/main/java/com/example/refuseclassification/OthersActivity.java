package com.example.refuseclassification;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.refuseclassification.Database.Knowledge;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class OthersActivity extends BaseActivity {

    private Toolbar toolbar;
    RecyclerView recyclerView;
    List<Knowledge> knowledges = new ArrayList<>();
    OthersActivity.MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        toolbar = (Toolbar) findViewById(R.id.dry_toolbar);
        toolbar.setTitle("其他垃圾");
        new setTitleCenter().setTitleCenter(toolbar);
        // 编写列表内容
        recyclerView = findViewById(R.id.dry_recyclerView);
        knowledges = LitePal.where("kind = ?", "其他垃圾").find(Knowledge.class);
        myAdapter = new OthersActivity.MyAdapter();
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(OthersActivity.this);
        recyclerView.setLayoutManager(manager);
    }

    class MyAdapter extends RecyclerView.Adapter<OthersActivity.MyViewHolder> {

        @NonNull
        @Override
        public OthersActivity.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(OthersActivity.this, R.layout.item_recyclerview, null);
            OthersActivity.MyViewHolder myViewHolder = new OthersActivity.MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull OthersActivity.MyViewHolder holder, int position) {
            Knowledge knowledge = knowledges.get(position);
            holder.name.setText(knowledge.getName());
            //holder.kind.setText((knowledge.getKind()));
        }

        @Override
        public int getItemCount() {
            return knowledges.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView kind;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            kind = itemView.findViewById(R.id.kind);
        }
    }
}
