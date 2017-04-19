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
import com.example.ahmed.projectlms.Adapter.DownloadableAdapter;
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
 * Created by Mohab on 4/8/2017.
 */

public class DownlaodableFragment extends Fragment implements DownloadableAdapter.DownloadListener
{
    View view;
    RecyclerView recyclerViewDownloadable;
    DownloadableAdapter downloadableAdapter;
    List<DownloadableModel> downloadableList = new ArrayList<>();
    public static final String LOG_TAG = DownlaodableFragment.class.getSimpleName();
    DownloadableModel downloadable = new DownloadableModel();
    Class_model extras= new Class_model();

    public Class_model getExtras() {
        return extras;
    }

    public void setExtras(Class_model extras) {
        this.extras = extras;
    }
    String pclassId = "185";
    private long enqueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_downloadable, container, false);
            no_storage_permission();
            recyclerViewDownloadable = (RecyclerView) view.findViewById(R.id.recycler_view_downloadable);
            downloadableAdapter = new DownloadableAdapter(downloadableList);
            downloadableAdapter.setListener(this);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerViewDownloadable.setLayoutManager(layoutManager);
            recyclerViewDownloadable.setAdapter(downloadableAdapter);
            View uoload = view.findViewById(R.id.ip_fab);
            uoload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    start_upload();
                }
            });
            downloadableData();
        }
        return view;
    }

    private void downloadableData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URL + "downloadable_student.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(LOG_TAG, "onResponse Downloadable " + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String responseString = jsonObject.getString("response");
                    if (responseString.equals("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            DownloadableModel downloadable = new DownloadableModel();
                            downloadable.setDownloadbleFileName(jsonObject1.getString("fname"));
                            downloadable.setDownloadableDescription(jsonObject1.getString("fdesc"));
                            downloadable.setDownloadableUploadedBy(jsonObject1.getString("uploaded_by"));
                            downloadable.setDownloadableDateUpload(jsonObject1.getString("fdatein"));
                            downloadable.setDownloadableFileLoc(jsonObject1.getString("floc"));

                            downloadableList.add(downloadable);
                        }
                        downloadableAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "onResponse Exception : " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("class_id",AppController.getInstance().getPrefManager().getUser().getClass_id());
                return  params;
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
    public void onCardViewDownloadClicked(DownloadableModel downloadable)
    {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},Config.MY_PERMISSIONS_REQUEST_STORAGE);
        }else {
            download_file(downloadable);

        }


    }



    public void start_upload() {
        Intent intent = new Intent(getActivity() , UploadActivity.class);
        intent.putExtra("id" , "0");
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
                    download_file(downloadable);
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

    private void download_file(DownloadableModel downloadable) {
        Toast.makeText(getContext(), "Download", Toast.LENGTH_LONG).show();
        String fileUrl = Config.BASE_URL0 + downloadable.getDownloadableFileLoc();
        Uri uri = Uri
                .parse(fileUrl);

        Log.e(LOG_TAG, "d file : " +fileUrl);

        DownloadManager.Request req=new DownloadManager.Request(uri);

        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(downloadable.getDownloadbleFileName())
                .setDescription(downloadable.getDownloadableDescription())
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                        downloadable.getDownloadbleFileName());
        DownloadManager downloadManager =(DownloadManager) getActivity().getSystemService(getActivity().DOWNLOAD_SERVICE);
        enqueue = downloadManager.enqueue(req);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 55 ){
            if (resultCode==getActivity().RESULT_OK){
                String file_loc = data.getExtras().getString("file_loc");
                String file_name = data.getExtras().getString("file_name");
                String file_Desc = data.getExtras().getString("file_Desc");
                upload_ass(file_loc , file_name , file_Desc );
            }
        }
    }

    private void upload_ass(final String file_loc , final String file_name , final String file_desc)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URL + "add_downloadable.php", new Response.Listener<String>() {
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
                  params.put("uploaded_by", AppController.getInstance().getPrefManager().getUser().getFirstName()+ AppController.getInstance().getPrefManager().getUser().getLastName());
                  params.put("class_id", AppController.getInstance().getPrefManager().getUser().getClass_id());
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

}
