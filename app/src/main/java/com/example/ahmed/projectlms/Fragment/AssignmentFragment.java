package com.example.ahmed.projectlms.Fragment;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ahmed.projectlms.Activity.MessActivity;
import com.example.ahmed.projectlms.Activity.NotificationActivity;
import com.example.ahmed.projectlms.Activity.UploadActivity;
import com.example.ahmed.projectlms.Adapter.AssignmentAdapter;
import com.example.ahmed.projectlms.Models.AssignmentModel;
import com.example.ahmed.projectlms.Models.Class_model;
import com.example.ahmed.projectlms.Models.DownloadableModel;
import com.example.ahmed.projectlms.R;
import com.example.ahmed.projectlms.app.AppController;
import com.example.ahmed.projectlms.app.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohab on 4/7/2017.
 */

public class AssignmentFragment extends Fragment implements AssignmentAdapter.DownloadListener
{
    View view;
    private RecyclerView recyclerViewAssignment;
    private AssignmentAdapter assignmentAdapter;
    private List<AssignmentModel> assignmentList = new ArrayList<>();
    public static final String LOG_TAG = AssignmentFragment.class.getSimpleName();
    String pClassId = "95";
    Class_model extras= new Class_model();

    public Class_model getExtras() {
        return extras;
    }

    public void setExtras(Class_model extras) {
        this.extras = extras;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (view == null) {
        view = inflater.inflate(R.layout.fragment_assignment, container,false);

        recyclerViewAssignment = (RecyclerView) view.findViewById(R.id.recycler_view_assignment);
        assignmentAdapter = new AssignmentAdapter(assignmentList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerViewAssignment.setLayoutManager(layoutManager);
        assignmentAdapter.setDownloadListener(this);
        recyclerViewAssignment.setAdapter(assignmentAdapter);
        no_storage_permission();
        assignmentData();
        }
        return view;
    }
    void toolbar_action_setup(String title) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final View notificationbtn = view.findViewById(R.id.main_toolbar_noti);
        final View menu_action = view.findViewById(R.id.main_toolbar_inbox);
        TextView page_title = (TextView) view.findViewById(R.id.main_toolbar_title);
        page_title.setText(title);
        notificationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notificationbtn == view) {
                    Intent intent = new Intent(getContext(), NotificationActivity.class);
                    startActivity(intent);
                }
            }
        });
        menu_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menu_action == view) {
                    startActivity(new Intent(getContext(), MessActivity.class));


                }
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==android.R.id.home )
            getActivity().onBackPressed();
        return true;
    }
    private void assignmentData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URL + "assignment_student.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(LOG_TAG, "onResponse log1 : " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String responseString = jsonObject.getString("response");
                    if (responseString.equals("success"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            AssignmentModel assignment = new AssignmentModel();

                            assignment.setFileName(jsonObject1.getString("fname"));
                            assignment.setAssDescription(jsonObject1.getString("fdesc"));
                            assignment.setDateUpload(jsonObject1.getString("fdatein"));
                            assignment.setFile_loc(jsonObject1.getString("floc"));
                            assignment.setId(jsonObject1.getString("assignment_id"));

                            assignmentList.add(assignment);
                        }
                        assignmentAdapter.notifyDataSetChanged();
                    }
                }
                catch (JSONException e)
                {
                    Log.e(LOG_TAG, "onResponse Exception : " + response);
                }
            }
        }, new Response.ErrorListener()
            {
            @Override
            public void onErrorResponse(VolleyError error)
            {
            Toast.makeText(getContext() , "no internet connection" , Toast.LENGTH_LONG).show();
            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("class_id", extras.getTeacherClassID());
                Log.e(LOG_TAG, "params : " + params.toString());

                return params;
            }

        };

        int socketTimeout = 10000; // 10 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                10,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);
        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);

    }
    @Override
    public void onCardViewDownloadClicked(AssignmentModel downloadable)
    {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},Config.MY_PERMISSIONS_REQUEST_STORAGE);
        }else {
            download_file(downloadable);

        }


    }

    @Override
    public void onCardViewUploaddClicked(AssignmentModel downloadable) {
        Intent intent = new Intent(getActivity() , UploadActivity.class);
        intent.putExtra("id" , downloadable.getId());
        startActivityForResult(intent,55);
    }


    private void no_storage_permission() {
        // Here, thisActivity is the current activity
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},Config.MY_PERMISSIONS_REQUEST_STORAGE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Config.MY_PERMISSIONS_REQUEST_STORAGE: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permisson needed", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void download_file(AssignmentModel downloadable) {
        Toast.makeText(getContext(), "Download", Toast.LENGTH_LONG).show();
        String fileUrl = Config.BASE_URL0 + downloadable.getFile_loc();
        Uri uri = Uri
                .parse(fileUrl);

        Log.e(LOG_TAG, "d file : " +fileUrl);

        DownloadManager.Request req=new DownloadManager.Request(uri);

        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(downloadable.getFileName())
                .setDescription(downloadable.getAssDescription())
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                        downloadable.getFileName());
        DownloadManager downloadManager =(DownloadManager) getActivity().getSystemService(getActivity().DOWNLOAD_SERVICE);
       downloadManager.enqueue(req);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 55 ){
            if (resultCode==getActivity().RESULT_OK){
                String file_loc = data.getExtras().getString("file_loc");
                String file_name = data.getExtras().getString("file_name");
                String file_Desc = data.getExtras().getString("file_Desc");
                String ass_id = data.getExtras().getString("id");
                upload_ass(file_loc , file_name , file_Desc , ass_id);
            }
        }
    }

    private void upload_ass(final String file_loc , final String file_name , final String file_desc , final String ass_id)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URL + "add_assignment.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(LOG_TAG, "onResponse log1 : " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String responseString = jsonObject.getString("response");
                    if (responseString.equals("sucsses"))
                    {
                        Toast.makeText(getContext() , "Assignment went to admin  " , Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(getContext() , "something went wrong " , Toast.LENGTH_LONG).show();

                    }
                }
                catch (JSONException e)
                {
                    Log.e(LOG_TAG, "onResponse Exception : " + response);
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getContext() , "no internet connection" , Toast.LENGTH_LONG).show();
            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("student_id", AppController.getInstance().getPrefManager().getUser().getStudent_id());
                params.put("file_loc", file_loc);
                params.put("file_name", file_name);
                params.put("file_desc", file_desc);
                params.put("ass_id", ass_id);
                params.put("teacher_class_id", extras.getTeacherClassID());
                return params;
            }

        };

        int socketTimeout = 10000; // 10 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                10,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);
        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

}
