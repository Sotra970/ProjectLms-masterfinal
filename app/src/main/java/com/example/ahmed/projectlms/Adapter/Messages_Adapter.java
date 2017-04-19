package com.example.ahmed.projectlms.Adapter;

import android.content.Context;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.ahmed.projectlms.Models.Message_model;
import com.example.ahmed.projectlms.R;

import java.util.ArrayList;


/**
 * Created by lenovo on 2/23/2017.
 */

public class Messages_Adapter extends RecyclerView.Adapter<Messages_Adapter.myViewHolder> {

    ArrayList<Message_model> data;
    LayoutInflater inflater ;
    Context context ;
    public Messages_Adapter(Context context, ArrayList<Message_model> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context ;
    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.messages,parent,false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Messages_Adapter.myViewHolder holder, int position) {
        if (position==0){
            float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources().getDisplayMetrics());

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            params.setMargins(params.leftMargin, (int) pixels, params.rightMargin, params.bottomMargin);
            holder.itemView.setLayoutParams(params);
        }
        final Message_model current = data.get(position) ;
        holder.message.setText(current.getMessage());
        holder.name.setText(current.getName());
        holder.date.setText(current.getDate());

        SimpleTarget target = new SimpleTarget<GlideBitmapDrawable>() {
            @Override
            public void onResourceReady(GlideBitmapDrawable bitmap, GlideAnimation glideAnimation) {
                // do something with the bitmap
                // for demonstration purposes, let's just set it to an ImageView
                Log.e("slider","endLoading");
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), bitmap.getBitmap());
                circularBitmapDrawable.setCircular(true);
                holder.picture.setImageDrawable( circularBitmapDrawable );
            }
        };

     try{   Glide.with(context).load(current.getImg()).centerCrop().into(target);}catch (Exception e){}
        Log.e("slider","img url "+ data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView name , message , date;
        ImageView picture ;
        public myViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.mess_item_creator_name);
            date = (TextView) itemView.findViewById(R.id.mess_item_time);
            message = (TextView) itemView.findViewById(R.id.mess_item_desc);
            picture = (ImageView) itemView.findViewById(R.id.mess_item_creator_img);
        }
    }
}
