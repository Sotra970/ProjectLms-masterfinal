package com.example.ahmed.projectlms.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ahmed.projectlms.Activity.ClassActivity;
import com.example.ahmed.projectlms.Activity.MessActivity;
import com.example.ahmed.projectlms.Activity.NotificationActivity;
import com.example.ahmed.projectlms.Activity.OverviewActivity;
import com.example.ahmed.projectlms.Adapter.Notification_Adapter;
import com.example.ahmed.projectlms.Models.Class_model;
import com.example.ahmed.projectlms.Models.Notification_model;
import com.example.ahmed.projectlms.R;
import com.example.ahmed.projectlms.app.AppController;
import com.example.ahmed.projectlms.app.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2/22/2017.
 */

public class NotificationFragment extends Fragment implements Notification_Adapter.Listener {

    RecyclerView recyclerView;
    public Notification_Adapter adapter;
    ArrayList<Notification_model> item_model=new ArrayList<>();
    View layout;

    private String TAG = NotificationFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       if(layout==null) {
           layout = inflater.inflate(R.layout.fragment_notification, container, false);
           recyclerView = (RecyclerView) layout.findViewById(R.id.noti_item);
           adapter = new Notification_Adapter(getActivity(), item_model);
           recyclerView.setAdapter(adapter);
           adapter.setListener(this);
           recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));




           final View menu_action = layout.findViewById(R.id.main_toolbar_inbox);
           TextView page_title = (TextView) layout.findViewById(R.id.main_toolbar_title);
           page_title.setText(R.string.home);

           menu_action.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if(menu_action == view) {
                       startActivity(new Intent(getContext(), MessActivity.class));


                   }
               }
           });
           getNotification();
       }

        return layout;
    }

    public void getNotification()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URL + "student_notification.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Log.e("Notification", "Notification response" + response);
                    JSONObject obj = new JSONObject(response);
                    String res = obj.getString("response");
                    if (res.equals("success")) {
                        JSONArray classData = obj.getJSONArray("data") ;
                        for (int i= (0); i<classData.length() ; i++){
                            JSONObject temp = classData.getJSONObject(i) ;
                            Notification_model notification_model = new Notification_model();
                            notification_model.setClassName(temp.getString("class_name"));
                            notification_model.setSubjectCode(temp.getString("subject_code"));
                            notification_model.setLastName(temp.getString("lastname"));
                            notification_model.setFirstName(temp.getString("firstname"));
                            notification_model.setNotificationData(temp.getString("notification"));
                            notification_model.setTeacherClassID(temp.getString("teacher_class_id"));
                            notification_model.setNotificationType(temp.getString("link"));
                            notification_model.setDateOfNotification(temp.getString("date_of_notification"));

                            Class_model class_model = new Class_model();

                            class_model.setClassName(temp.getString("class_name"));
                            class_model.setClassID(temp.getString("class_id"));
                            class_model.setSubjectCode(temp.getString("subject_code"));
                            class_model.setTeacherClassID(temp.getString("teacher_class_id"));
                            class_model.setTeacherfirstName(temp.getString("firstname"));
                            class_model.setTeacherlastName(temp.getString("lastname"));

                            notification_model.setClass_model(class_model);

                            item_model.add(notification_model);


                        }
                        adapter.notifyDataSetChanged();


                    } else {

                        Toast.makeText(getContext(), getString(R.string.login_err), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "json error" + e.getMessage());
                    {

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                }
                if (error instanceof NoConnectionError) {

                    String message =   "please check connection";
                    Toast.makeText(getContext(), "" + message, Toast.LENGTH_LONG).show();
                }
            }
        })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("student_id", AppController.getInstance().getPrefManager().getUser().getStudent_id());

                Log.e(TAG, "params: " + params.toString());
                return params;
            }
        };
        int socketTimeout = 10000; // 10 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                10,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);


        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public void onNotificationClicked(Notification_model notification_model) {
            Context context = getContext();
            Intent intent = new Intent(context, ClassActivity.class);
            intent.putExtra(ClassActivity.EXTRAS_NOTIFICATION, notification_model);
            intent.putExtra(ClassActivity.EXTRAS_CLASS, notification_model.getClass_model());

        startActivity(intent);
    }
}


