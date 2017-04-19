package com.example.ahmed.projectlms.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmed.projectlms.Models.AssignmentModel;
import com.example.ahmed.projectlms.R;

import java.util.List;

/**
 * Created by Mohab on 4/8/2017.
 */

public class ProgressAssignmentAdapter extends RecyclerView.Adapter<ProgressAssignmentAdapter.ProgressAssignmentViewHolder>
{
    private List<AssignmentModel> assignmentList;

    public ProgressAssignmentAdapter(List<AssignmentModel> assignmentList)
    {
        this.assignmentList = assignmentList;
    }

    @Override
    public ProgressAssignmentAdapter.ProgressAssignmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.progress_assignment_row,parent, false);
        return new ProgressAssignmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProgressAssignmentAdapter.ProgressAssignmentViewHolder holder, int position)
    {
        AssignmentModel assignment = assignmentList.get(position);
        holder.textViewAssignmentFileNameProgress.setText(assignment.getFileName());
        holder.textViewAssignmentDateUploadProgress.setText(assignment.getDateUpload());
        holder.textViewAssignmentGradeProgress.setText(assignment.getAssGrade());
        holder.textViewAssignmentDescProgress.setText(assignment.getAssDescription());
    }

    @Override
    public int getItemCount()
    {
        return assignmentList.size();
    }

    public class ProgressAssignmentViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewAssignmentFileNameProgress, textViewAssignmentDateUploadProgress,
                textViewAssignmentGradeProgress, textViewAssignmentDescProgress;

        public ProgressAssignmentViewHolder(View itemView)
        {
            super(itemView);
            textViewAssignmentFileNameProgress = (TextView) itemView.findViewById(R.id.textview_progress_assignment_file_name);
            textViewAssignmentDateUploadProgress = (TextView) itemView.findViewById(R.id.textview_progress_assignment_date_upload);
            textViewAssignmentGradeProgress = (TextView) itemView.findViewById(R.id.textview_progress_assignment_grade);
            textViewAssignmentDescProgress = (TextView) itemView.findViewById(R.id.textview_progress_assignment_desc);
        }
    }
}
