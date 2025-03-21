package com.sabbarish.androidjcomp.adapter;

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
import com.sabbarish.androidjcomp.DetailActivity;
import com.sabbarish.androidjcomp.R;
import com.sabbarish.androidjcomp.domain.BestSell;

import java.util.List;

public class BestSellAdapter extends RecyclerView.Adapter<BestSellAdapter.ViewHolder> {
    Context context;
    List<BestSell> mBestSelllist;
    public BestSellAdapter(Context context, List<BestSell> mBestSelllist) {
        this.context = context;
        this.mBestSelllist = mBestSelllist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_bestsell_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mName.setText(mBestSelllist.get(position).getName());
        holder.mPrice.setText(mBestSelllist.get(position).getPrice()+"₹");
        Glide.with(context).load(mBestSelllist.get(position).getImg_url()).into(holder.mImage);

        holder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, DetailActivity.class);
                intent.putExtra("detail",mBestSelllist.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mBestSelllist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImage;
        TextView mName;
        TextView mPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.bs_img);
            mName = itemView.findViewById(R.id.bs_name);
            mPrice = itemView.findViewById(R.id.bs_cost);
        }
    }
}
