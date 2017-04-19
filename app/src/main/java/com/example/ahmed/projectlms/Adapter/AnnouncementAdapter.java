package com.example.ahmed.projectlms.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmed.projectlms.Models.AnnouncementModel;
import com.example.ahmed.projectlms.R;

import java.util.List;

/**
 * Created by Mohab on 4/4/2017.
 */

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder>
{
    private List<AnnouncementModel> announcementList;
    public AnnouncementAdapter(List<AnnouncementModel> announcementList)
    {
        this.announcementList = announcementList;
    }

    @Override
    public AnnouncementAdapter.AnnouncementViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.announcement_row, parent, false);
        return new AnnouncementViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AnnouncementAdapter.AnnouncementViewHolder holder, int position)
    {
        AnnouncementModel announcement = announcementList.get(position);
        holder.textViewAnnouncement.setText(announcement.getAnnouncementContent());
        holder.textViewAnnouncementDate.setText(announcement.getAnnouncementDate());

    }

    @Override
    public int getItemCount() {
        return announcementList.size();
    }

    public class AnnouncementViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewAnnouncementTitle,textViewAnnouncementDate, textViewAnnouncement;
        ImageView imageViewAnnouncement;

        public AnnouncementViewHolder(View itemView)
        {
            super(itemView);
            //textViewAnnouncementTitle = (TextView) itemView.findViewById(R.id.textview_announcement_title);
            textViewAnnouncementDate = (TextView) itemView.findViewById(R.id.textview_announcement_date);
            imageViewAnnouncement = (ImageView) itemView.findViewById(R.id.imageview_announcement);
            textViewAnnouncement = (TextView) itemView.findViewById(R.id.textview_announcement);
        }
    }
}
