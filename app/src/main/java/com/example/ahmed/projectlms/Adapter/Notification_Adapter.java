package com.example.ahmed.projectlms.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmed.projectlms.Models.Notification_model;
import com.example.ahmed.projectlms.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 2/23/2017.
 */

public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.myViewHolder> {

    ArrayList<Notification_model> data;
    LayoutInflater inflater ;
    private Listener mListener;

    public Notification_Adapter(Context context, ArrayList<Notification_model> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
    }
    public void setListener(Listener listener)
    {
        mListener = listener;
    }
    @Override
    public Notification_Adapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.noti_item,parent,false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Notification_Adapter.myViewHolder holder, int position) {
        Notification_model current = data.get(position) ;

        holder.teacherFirstName.setText(current.getFirstName() );
        holder.teacherLastName.setText(current.getLastName());
        holder.notificationData.setText(current.getNotificationData().trim());
        holder.className.setText(current.getClassName());
        holder.date.setText(current.getDateOfNotification());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class myViewHolder extends RecyclerView.ViewHolder{
        TextView teacherFirstName,teacherLastName,notificationData,className,date;
        public myViewHolder(View itemView) {
            super(itemView);
            teacherFirstName = (TextView) itemView.findViewById(R.id.teacher_first_name);
            teacherLastName = (TextView) itemView.findViewById(R.id.teacher_last_name);
            notificationData = (TextView) itemView.findViewById(R.id.teacher_noti_data);
            className = (TextView) itemView.findViewById(R.id.class_name);
            date = (TextView) itemView.findViewById(R.id.noti_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null)
                    {
                        Notification_model notification_model = data.get(getAdapterPosition());
                        mListener.onNotificationClicked(notification_model);
                    }
                }
            });
        }
    }
    public interface Listener
    {
        void onNotificationClicked(Notification_model notification_model);
    }
}

