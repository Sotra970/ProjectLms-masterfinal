package com.example.ahmed.projectlms.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ahmed.projectlms.Adapter.ProgressAssignmentAdapter;
import com.example.ahmed.projectlms.Models.AssignmentModel;
import com.example.ahmed.projectlms.Models.Class_model;
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
 * Created by Mohab on 4/8/2017.
 */

public class TeacherMessageFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    EditText teacherMessageText;
    Button sendBtn;
    ArrayList<String> teacher = new ArrayList<String>();
    ArrayList<String> teacher_id = new ArrayList<String>();
    String pteacher_id,pmessagecontent,student_id,recieverName,senderName;
    ArrayAdapter adapter ;
    private String teacher_txt = "" , teacher_id_choosed="";



    public static final String LOG_TAG = TeacherMessageFragment.class.getSimpleName();
    Class_model extras = new Class_model() ;
    public Class_model getExtras() {
        return extras;
    }

    public void setExtras(Class_model extras) {
        this.extras = extras;
    }
    View view;
    private String TAG = TeacherMessageFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_teacher_message,  container, false);

        spinner = (Spinner) view.findViewById(R.id.teacher_spiner);
        adapter = new ArrayAdapter(getContext(),R.layout.simple_spinner_item,teacher);
        spinner.setOnItemSelectedListener(this);
        adapter.setDropDownViewResource(R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);


        teacherMessageText = (EditText) view.findViewById(R.id.message_text);
        sendBtn = (Button) view.findViewById(R.id.send_btn);
        getTeacherSpinner();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sendBtn == view)
                {
                    sendMessageTeacher();
                }
            }
        });
        return view;
    }

    // Validating Pass
    private boolean validateEditText(EditText editText) {
        String txt = editText.getText().toString().trim();

        if (txt.equals(null)||txt.trim().isEmpty()) {
            editText.setError(getString(R.string.incorrect_data));
            editText.requestFocus();
            return false;
        } else {
            editText.setError(null);
        }

        return true;
    }


    boolean validate_sp(Spinner spinner, String txt ) {
        TextView textView = ((TextView) spinner.getChildAt(0));
        Log.e("txtview" , textView.getText().toString());
        Log.e("txt" , txt);
        if ( txt.trim().isEmpty()  ){
            return false;
        }
        return true;
    }
    public void sendMessageTeacher()
    {

        if (!validateEditText(teacherMessageText)) {
            return;
        }

        if (!validate_sp(spinner, teacher_txt)) {
            return;

        }

        view.findViewById(R.id.loadingSpinners).setVisibility(View.VISIBLE);


        pmessagecontent = teacherMessageText.getText().toString();

        StringRequest stringRequest  = new StringRequest(Request.Method.POST,Config.BASE_URL + "message_send.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        view.findViewById(R.id.loadingSpinners).setVisibility(View.GONE);
                        getActivity().setResult(Activity.RESULT_OK);
                        getActivity().supportFinishAfterTransition();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                view.findViewById(R.id.loadingSpinners).setVisibility(View.GONE);
                String message =   "please check connection";
                Toast.makeText(getContext(), "" + message, Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("teacher_id", teacher_id_choosed);
                params.put("content",pmessagecontent);
                params.put("student_id",AppController.getInstance().getPrefManager().getUser().getStudent_id());
                params.put("receiver_name",teacher_txt);
                params.put("sender_name",AppController.getInstance().getPrefManager().getUser().getFirstName() + AppController.getInstance().getPrefManager().getUser().getLastName());

                Log.e(TAG, "params: " + params.toString());
                return params;
            }
        }
                ;

        int socketTimeout = 3000; // 10 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                10,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(stringRequest);

    }


    private void getTeacherSpinner()
    {


        StringRequest stringRequest  = new StringRequest(Request.Method.POST,Config.BASE_URL + "getTeacher.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.e(TAG, response);
                            JSONObject obj = new JSONObject(response);
                            String res = obj.getString("response");
                            if (res.equals("success")) {
                                JSONArray classData = obj.getJSONArray("data") ;
                                for (int i= (0); i<classData.length() ; i++){
                                    JSONObject temp = classData.getJSONObject(i) ;

                                    teacher.add(temp.getString("firstname") + " " +temp.getString("lastname"));
                                    teacher_id.add(temp.getString("teacher_id"));



                                }
                                adapter.notifyDataSetChanged();


                            } else {

                                Toast.makeText(getContext(), getString(R.string.class_error), Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            Log.e(TAG, "json error" + e.getMessage());

                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message =   "please check connection";
                Toast.makeText(getContext(), "" + message, Toast.LENGTH_LONG).show();
            }
        })
                ;

        int socketTimeout = 3000; // 10 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                10,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(stringRequest);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {

            case R.id.teacher_spiner:
                teacher_txt = adapterView.getSelectedItem().toString();
                teacher_id_choosed= teacher_id.get(teacher.lastIndexOf(teacher_txt));
                Log.e("teacher id :", "hello" +teacher_id_choosed );
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
