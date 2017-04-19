package com.example.ahmed.projectlms.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.ahmed.projectlms.Models.Class_model;
import com.example.ahmed.projectlms.Models.Message_model;
import com.example.ahmed.projectlms.R;
import com.example.ahmed.projectlms.app.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 4/6/2017.
 */

public class Home_adapter extends RecyclerView.Adapter<Home_adapter.myViewHolder> {

    private ArrayList<Class_model> class_list;
    LayoutInflater inflater ;
    Context context ;
    private Listener mListener;

    public Home_adapter(Context context, ArrayList<Class_model> class_list)
    {

        inflater = LayoutInflater.from(context);
        this.class_list = class_list;
        this.context = context ;
    }

    public void setListener(Listener listener)
    {
        mListener = listener;
    }

    @Override
    public Home_adapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.home_item,parent,false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Home_adapter.myViewHolder holder, int position) {

        Class_model current = class_list.get(position) ;


        Log.e("clasess_adapter" , Config.img_url0 + class_list.get(position).getClassimg()) ;

        Glide.with(context)
                .load(Config.img_url0 + class_list.get(position).getClassimg())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .override(300,250)
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.classPicture);

        holder.teacherfirstName.setText(current.getTeacherfirstName());
        holder.teacherlastName.setText(current.getTeacherlastName());
        holder.className.setText(current.getClassName());
        holder.classCode.setText(current.getSubjectCode());
    }

    @Override
    public int getItemCount() {
        return class_list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView className, teacherfirstName,teacherlastName,classCode;
        ImageView classPicture;


        public myViewHolder(View itemView) {
            super(itemView);

            className = (TextView) itemView.findViewById(R.id.class_text);
            teacherfirstName = (TextView) itemView.findViewById(R.id.teacher_first_name);
            teacherlastName = (TextView) itemView.findViewById(R.id.teacher_last_name);
            classCode = (TextView) itemView.findViewById(R.id.class_code_text);
            classPicture = (ImageView) itemView.findViewById(R.id.class_img);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null)
                    {
                        Class_model class_model= class_list.get(getAdapterPosition());
                        mListener.onClassClicked(class_model);
                    }
                }
            });


        }
    }



    public interface Listener
    {
        void onClassClicked(Class_model class_model);
    }
}
