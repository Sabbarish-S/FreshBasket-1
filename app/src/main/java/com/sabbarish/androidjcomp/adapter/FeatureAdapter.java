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
import com.sabbarish.androidjcomp.domain.Feature;

import java.util.List;

public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.ViewHolder> {
    Context  context;
    List<Feature> mFeaturelist;
    public FeatureAdapter(Context context, List<Feature> mFeaturelist) {
        this.context = context;
        this.mFeaturelist = mFeaturelist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_feature_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        holder.mFetCost.setText(mFeaturelist.get(position).getPrice()+"₹");
        holder.mFetName.setText(mFeaturelist.get(position).getName());
        Glide.with(context).load(mFeaturelist.get(position).getImg_url()).into(holder.mFetImage);
        holder.mFetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detail", mFeaturelist.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFeaturelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mFetImage;
        TextView mFetCost;
        TextView mFetName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mFetImage=itemView.findViewById(R.id.f_img);
            mFetCost=itemView.findViewById(R.id.f_cost);
            mFetName=itemView.findViewById(R.id.f_name);
        }
    }
}
