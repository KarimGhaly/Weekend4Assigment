package com.example.admin.weekend4assigment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.weekend4assigment.model.BooksClass;

import java.util.List;

/**
 * Created by Admin on 9/24/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    Context context;
    List<BooksClass> booksList;

    public RVAdapter(Context context, List<BooksClass> booksList) {
        this.context = context;
        this.booksList = booksList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view  = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BooksClass b = booksList.get(position);
        holder.Title.setText(b.getTitle());
        Glide.with(context).load(b.getImageURL()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Title;
        ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.txtTitle);
            img = (ImageView) itemView.findViewById(R.id.imgView);
        }
    }
}
