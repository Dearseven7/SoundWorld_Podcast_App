package com.example.refuseclassification.adapter;

import static com.example.refuseclassification.R.id.imagecommodity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.refuseclassification.Database.Item;
import com.example.refuseclassification.R;

import java.util.List;

//将数据和类进行绑定
//代表一个界面
public class RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder>{

//    获取界面activity
    private LayoutInflater mInflate;
    private Context mContext;
//    数据传输
    private List<Item> mData;

    public RecyclerAdapter(Context context, LayoutInflater mInflate, List<Item> mData) {
        this.mContext=context;
        this.mInflate = mInflate;
        this.mData = mData;
    }

//    创建绑定界面

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
// 不需要检查是否复用，因为只要进入此方法，必然没有复用
// 因为 RecyclerView 通过 Holder 检查复用
//        定义相应的界面，动态获取
        View view=mInflate.inflate(R.layout.item_credit,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder; }

//    创建绑定数据--来自于item
    @Override
    public void onBindViewHolder( MyViewHolder myViewHolder, int position   ) {
//        获取对应数据
        myViewHolder.tv.setText(mData.get(position).getTitle());
        myViewHolder.mImageview.setImageResource(mData.get(position).getImg());
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    //添加数据
    public void addItem(Item data, int position) {
        mData.add(position, data);
        notifyItemInserted(position);
    }
    //删除数据
    public void removeItem(Item data) {
        int position = mData.indexOf(data);
        mData.remove(position);
        notifyItemRemoved(position);
    }


}



//定义相应的界面
//获取界面中的图片和标题
//分别设置点击跳转的事件
class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tv;
    ImageView mImageview;

    public MyViewHolder(View itemView) {
        super(itemView);
        // 通常ViewHolder的构造，就是用于获取控件视图的
//        项目中对应的图片和标题
        tv = itemView.findViewById(R.id.textcredit);
        mImageview = (ImageView) itemView.findViewById(R.id.imagecommodity);
        // TODO 后续处理点击(image)事件的操作
        mImageview.setOnClickListener(this);
    }

    //    上述View.OnClickListener抽象方法的实现
    @Override
    public void onClick(View v) {
//        定义位置
        int position = getAdapterPosition();
        Context context = mImageview.getContext();
//        Toast.makeText(context,"This is item "+position,Toast.LENGTH_SHORT).show();
        Toast.makeText(context,tv.getText(),Toast.LENGTH_SHORT).show();


        switch (position) {
            case 0:
//            进行跳转
//                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
//                intent1.putExtra("userId",userid.getText().toString());//传递参数
//                startActivity(intent1);//启动另一 activity
//                Intent intent = new Intent(context, DetailSpot.class);
//                context.startActivity(intent);
                break;
            default:
                Toast.makeText(context,"该功能尚在开发中……",Toast.LENGTH_SHORT).show();
        }
    }
}