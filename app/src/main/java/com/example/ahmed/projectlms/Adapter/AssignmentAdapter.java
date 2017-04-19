package com.example.ahmed.projectlms.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmed.projectlms.Models.AssignmentModel;
import com.example.ahmed.projectlms.Models.DownloadableModel;
import com.example.ahmed.projectlms.R;

import java.util.List;

/**
 * Created by Mohab on 4/7/2017.
 */

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder>
{
    private List<AssignmentModel> assignmentList;

    DownloadListener downloadListener  ;

    public List<AssignmentModel> getAssignmentList() {
        return assignmentList;
    }

    public void setAssignmentList(List<AssignmentModel> assignmentList) {
        this.assignmentList = assignmentList;
    }

    public DownloadListener getDownloadListener() {
        return downloadListener;
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    public AssignmentAdapter(List<AssignmentModel> assignmentList)
    {
        this.assignmentList = assignmentList;
    }

    @Override
    public AssignmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_row,parent, false);
        return new AssignmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssignmentViewHolder holder, int position)
    {
        final AssignmentModel assignment = assignmentList.get(position);
        holder.textViewAssignmentFileName.setText(assignment.getFileName());
        holder.textViewAssignmentDateUpload.setText(assignment.getDateUpload());
        holder.textViewAssignmentDesc.setText(assignment.getAssDescription());

        holder.cardViewDownloadAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadListener.onCardViewDownloadClicked(assignment);
            }
        });
        holder.cardViewUploadAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadListener.onCardViewUploaddClicked(assignment);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return assignmentList.size();
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewAssignmentFileName, textViewAssignmentDateUpload, textViewAssignmentDesc;
        CardView cardViewUploadAssignment, cardViewDownloadAssignment;

        public AssignmentViewHolder(View itemView)
        {
            super(itemView);
            textViewAssignmentFileName = (TextView) itemView.findViewById(R.id.textview_assignment_file_name);
            textViewAssignmentDateUpload = (TextView) itemView.findViewById(R.id.textview_assignment_date_upload);
            textViewAssignmentDesc = (TextView) itemView.findViewById(R.id.textview_assignment_desc);

            cardViewUploadAssignment = (CardView) itemView.findViewById(R.id.cardview_upload_assignment);
            cardViewDownloadAssignment = (CardView) itemView.findViewById(R.id.cardview_download_assignment);

        }
    }

    public interface DownloadListener
    {
        void onCardViewDownloadClicked(AssignmentModel downloadable);
        void onCardViewUploaddClicked(AssignmentModel downloadable);
    }

}
