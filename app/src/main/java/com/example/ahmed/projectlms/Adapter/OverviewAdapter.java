package com.example.ahmed.projectlms.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ahmed.projectlms.Models.Class_model;
import com.example.ahmed.projectlms.R;
import com.example.ahmed.projectlms.app.Config;

import java.util.ArrayList;

/**
 * Created by ahmed on 4/6/2017.
 */

public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.myViewHolder> {

    ArrayList<Class_model> data;
    LayoutInflater inflater ;
    Context context ;
    public OverviewAdapter(Context context, ArrayList<Class_model> data)
    {

        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context ;
    }

    @Override
    public OverviewAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.overview_item,parent,false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(OverviewAdapter.myViewHolder holder, int position) {
            holder.classMateImg.setImageDrawable(null);
        Class_model current = data.get(position) ;


        holder.classMateFirstName.setText(current.getClassMateFirstName());
        holder.classMateLastName.setText(current.getGetClassMateLastName());
        Glide.with(context).
                load(Config.img_url+current.getGetClassMateImg()
                ).centerCrop().crossFade().into(holder.classMateImg);
        /*holder.classMateName.setText(current.getClassMateName());*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView classMateFirstName,classMateLastName;
        ImageView classMateImg;


        public myViewHolder(View itemView) {
            super(itemView);

            classMateImg = (ImageView) itemView.findViewById(R.id.classmate_img);
            classMateFirstName = (TextView) itemView.findViewById(R.id.classmate_first_name_text);
            classMateLastName = (TextView) itemView.findViewById(R.id.classmate_last_name_text);

        }
    }
}
