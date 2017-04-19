package com.example.ahmed.projectlms.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.ahmed.projectlms.Adapter.OverviewAdapter;
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
 * A simple {@link Fragment} subclass.
 */
public class OverViewFragment extends Fragment {

    RecyclerView recyclerView;
    public OverviewAdapter overviewAdapter;
    ArrayList<Class_model> item_model = new ArrayList<>();

    View res ;

    private String TAG = OverViewFragment.class.getSimpleName();
    Notification_model extrasNotif = new Notification_model();



    Class_model extras= new Class_model();

    public Class_model getExtras() {
        return extras;
    }

    public void setExtras(Class_model extras) {
        this.extras = extras;
    }


    public Notification_model getExtrasNotif() {


        return extrasNotif;
    }

    public void setExtrasNotif(Notification_model extrasNotif) {
        this.extrasNotif = extrasNotif;
    }

    public OverViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (res == null) {
            // Inflate the layout for this fragment
            res = inflater.inflate(R.layout.fragment_over_view, container, false);

            recyclerView = (RecyclerView) res.findViewById(R.id.overview_item);

            overviewAdapter = new OverviewAdapter(getContext(), item_model);
            recyclerView.setAdapter(overviewAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

            getClassMate();
        }
       return  res  ;
    }



    public void getClassMate()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URL + "my_classmates.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Log.e("overview", "overview classmates response" + response);
                    JSONObject obj = new JSONObject(response);
                    String res = obj.getString("response");
                    if (res.equals("success")) {
                        JSONArray classData = obj.getJSONArray("data") ;
                        for (int i= (0); i<classData.length() ; i++){
                            JSONObject temp = classData.getJSONObject(i) ;
                            Class_model class_model = new Class_model();

                            class_model.setClassMateFirstName(temp.getString("firstname"));
                            class_model.setGetClassMateLastName(temp.getString("lastname"));
                            class_model.setGetClassMateImg(temp.getString("location"));

                            item_model.add(class_model);

                        }
                        overviewAdapter.notifyDataSetChanged();
                    } else {

                        Toast.makeText(getContext(), getString(R.string.NO_CLASSMATES), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.d(TAG, "json error" + e.getMessage());
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
                params.put("teacher_class_id", extras.getTeacherClassID());

                //params.put("token", FirebaseInstanceId.getInstance().getToken());

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
}
