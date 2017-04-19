package com.example.ahmed.projectlms.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmed.projectlms.Models.DownloadableModel;
import com.example.ahmed.projectlms.R;

import java.util.List;

/**
 * Created by Mohab on 4/8/2017.
 */

public class DownloadableAdapter extends RecyclerView.Adapter<DownloadableAdapter.DownloadableViewHolder>
{

    List<DownloadableModel> downloadableList;
    private DownloadListener listener;

    public void setListener(DownloadListener listener)
    {
        this.listener = listener;
    }

    public DownloadableAdapter(List<DownloadableModel> downloadableList)
    {
        this.downloadableList = downloadableList;
    }

    @Override
    public DownloadableViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.downloadable_row, parent, false);
        return new DownloadableViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DownloadableViewHolder holder, int position)
    {
        final DownloadableModel downloadable = downloadableList.get(position);

        holder.textViewDownloadableFileName.setText(downloadable.getDownloadbleFileName());
        holder.textViewDownloadableDescription.setText(downloadable.getDownloadableDescription());
        holder.textViewDownloadableUploadedBy.setText(downloadable.getDownloadableUploadedBy());
        holder.textViewDownloadableDate.setText(downloadable.getDownloadableDateUpload());

        holder.cardViewDownloadableDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCardViewDownloadClicked(downloadable);
            }
        });
    }

    @Override
    public int getItemCount() {
        return downloadableList.size();
    }

    public class DownloadableViewHolder extends RecyclerView.ViewHolder
    {
        TextView  textViewDownloadableFileName, textViewDownloadableDescription,
                textViewDownloadableUploadedBy, textViewDownloadableDate;
        CardView cardViewDownloadableUpload, cardViewDownloadableDownload;

        public DownloadableViewHolder(View itemView)
        {
            super(itemView);
            textViewDownloadableFileName = (TextView) itemView.findViewById(R.id.textview_downloadable_file_name);
            textViewDownloadableDescription = (TextView) itemView.findViewById(R.id.textview_downloadable_description);
            textViewDownloadableUploadedBy = (TextView) itemView.findViewById(R.id.textview_downloadable_uploaded_by);
            textViewDownloadableDate = (TextView) itemView.findViewById(R.id.textview_downloadable_date);

            cardViewDownloadableUpload = (CardView) itemView.findViewById(R.id.cardview_downloadable_upload);
            cardViewDownloadableDownload = (CardView) itemView.findViewById(R.id.cardview_downloadable_download);



        }
    }

    public interface DownloadListener
    {
         void onCardViewDownloadClicked(DownloadableModel downloadable);
    }


}
