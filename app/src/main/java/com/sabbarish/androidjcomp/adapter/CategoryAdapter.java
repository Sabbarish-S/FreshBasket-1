package com.sabbarish.androidjcomp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sabbarish.androidjcomp.ItemsActivity;
import com.sabbarish.androidjcomp.R;
import com.sabbarish.androidjcomp.domain.Category;

import java.util.List;

//Passing the data from homefragment to CategoryAdapter and from .this to Display
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context;
    private List<Category> mCategorylist;
    public CategoryAdapter(Context context, List<Category> mCategorylist) {
        this.context = context;
        this.mCategorylist = mCategorylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Glide.with(context).load(mCategorylist.get(position).getImg_url()).into(holder.mTypeImg);
        holder.mTypeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemsActivity.class);
                intent.putExtra("type",mCategorylist.get(position).getType());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCategorylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mTypeImg;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            mTypeImg=itemView.findViewById(R.id.category_img);
        }
    }

}
