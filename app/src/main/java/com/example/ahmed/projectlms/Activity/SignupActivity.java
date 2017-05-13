package com.example.ahmed.projectlms.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ahmed.projectlms.Models.Class_model;
import com.example.ahmed.projectlms.Models.UserModel;
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
 * Created by ahmed on 4/3/2017.
 */

public class SignupActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {

    EditText studentID,firstName,lastName,passwordText,confirmPassword , email;
    Button signUP;
    String pstudentID,pfirstName,plastName,ppassword,ppasswordConfirm  ;
    TextInputLayout studentIDLayout,firstNameLayout,lastNameLayout,passwordLayout,confirmPasswordLayout , email_layout;
    private String TAG = SignupActivity.class.getSimpleName();
    ArrayList<String> classes = new ArrayList<String>();
    ArrayList<String> classes_id = new ArrayList<String>();
    ArrayAdapter adapter ;
    Spinner spinner;
    private String class_txt = "" , class_id_choosed="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
//

        //initialization
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        studentID = (EditText) findViewById(R.id.student_id);
        firstName = (EditText) findViewById(R.id.student_first_name);
        lastName = (EditText)  findViewById(R.id.student_last_name);
        passwordText = (EditText) findViewById(R.id.student_password);
        confirmPassword = (EditText) findViewById(R.id.student_confirm_password);
        email = (EditText) findViewById(R.id.email);

        signUP = (Button) findViewById(R.id.button_signup);

        email_layout = (TextInputLayout) findViewById(R.id.email_layout);
        studentIDLayout = (TextInputLayout) findViewById(R.id.layout_student_id);
        firstNameLayout = (TextInputLayout) findViewById(R.id.input_layout_first_name);
        lastNameLayout = (TextInputLayout) findViewById(R.id.input_layout_last_name);
        passwordLayout = (TextInputLayout) findViewById(R.id.input_layout_password);
        confirmPasswordLayout = (TextInputLayout) findViewById(R.id.input_layout_confirm_password);

         spinner = (Spinner) findViewById(R.id.class_spiner);
         adapter = new ArrayAdapter(getBaseContext(), R.layout.simple_spinner_item, classes);
         spinner.setOnItemSelectedListener(this);
        adapter.setDropDownViewResource(R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        getClassSpinner();


        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.loadingSpinner).setVisibility(View.VISIBLE);
                register();
            }
        });
        toolbar_action_setup(getString(R.string.sign_up));
    }

    private void register()
    {

        if (!validateEditText(studentID, studentIDLayout)) {
            return;
        }
        if (!validateEditText(firstName, firstNameLayout)) {
                return;
            }
        if (!validateEditText(lastName, lastNameLayout)) {
            return;
        }
        if (!validateEditText(email, email_layout)) {
            return;
        }

        if(!validatePass())
            {
                return;
            }
        if (!validate_sp(spinner, class_txt)) {
            return;

        }


        pstudentID= studentID.getText().toString();
        pfirstName= firstName.getText().toString();
        plastName=lastName.getText().toString();
        ppassword= passwordText.getText().toString();
        ppasswordConfirm=confirmPassword.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URL + "signup.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "signup response" + response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String res = obj.getString("response");
                    if (res.equals("success")) {
//                        JSONObject userobj = obj.getJSONObject("data");
//                        UserModel user = new UserModel();
//                        user.setStudent_id(userobj.getString("student_id"));
//                        user.setFirstName(userobj.getString("firstname"));
//                        user.setLastName(userobj.getString("lastname"));
//                        user.setClass_id(userobj.getString("class_id"));
//                        user.setPassword(userobj.getString("password"));
//                        user.setUserName(userobj.getString("username"));
//                        user.setStatus(userobj.getString("status"));
//                        user.setStudent_img(userobj.getString("location"));

                        supportFinishAfterTransition();
                        /*Intent intent = new Intent(new Intent(getApplicationContext(), HomeActivity.class));
                        ComponentName cn = intent.getComponent();
                        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                        startActivity(mainIntent);
                        finish();*/
                    } else {
                        findViewById(R.id.loadingSpinner).setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), getString(R.string.login_err), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.d(TAG, "json error" + e.getMessage());
                    {

                        findViewById(R.id.loadingSpinner).setVisibility(View.GONE);
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
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();
                }

                findViewById(R.id.loadingSpinner).setVisibility(View.GONE);
            }
        })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", pstudentID);
                params.put("firstname",pfirstName);
                params.put("lastname",plastName);
                params.put("password",ppassword);
                params.put("email",email.getText().toString());
                /*params.put("confirmpassword",ppasswordConfirm);*/
                params.put("class_id",class_id_choosed);

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
        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    private void getClassSpinner()
    {


        StringRequest stringRequest  = new StringRequest(Request.Method.POST,Config.BASE_URL + "class.php",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Log.e("signup", "Signup response" + response);
                    JSONObject obj = new JSONObject(response);
                    String res = obj.getString("response");
                    if (res.equals("success")) {
                        JSONArray classData = obj.getJSONArray("data") ;
                        for (int i= (0); i<classData.length() ; i++){
                            JSONObject temp = classData.getJSONObject(i) ;

                            classes.add(temp.getString("class_name"));
                            classes_id.add(temp.getString("class_id"));


                        }
                        adapter.notifyDataSetChanged();


                    } else {

                        Toast.makeText(getApplicationContext(), getString(R.string.class_error), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "json error" + e.getMessage());

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    String message =   "please check connection";
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();
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

            case R.id.class_spiner:
                class_txt = adapterView.getSelectedItem().toString();
                class_id_choosed = classes_id.get(classes.lastIndexOf(class_txt));
                Log.e("class id :", "hello" +class_id_choosed );
                break;

        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // Validating Pass
    private boolean validateEditText(EditText editText , TextInputLayout textInputLayout) {
        String txt = editText.getText().toString().trim();

        if (txt.equals(null)||txt.trim().isEmpty()) {
            textInputLayout.setError(getString(R.string.incorrect_data));
            editText.requestFocus();
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
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
    private boolean validatePass() {
        String Pass = passwordText.getText().toString().trim();
        String confirmPass = confirmPassword.getText().toString().trim();

        if (!Pass.equals(confirmPass) ) {
            incorrectPass();
            return false;
        } else {
            passwordLayout.setErrorEnabled(false);
        }

        return true;
    }

    private void incorrectPass() {
        passwordLayout.setError(getString(R.string.password_not_match));
        requestFocus(passwordText);
    }
    private void requestFocus(View view) {
        Log.d("requestFocus",view.requestFocus()+"");
        //foucus on view
        view.requestFocus();


    }

    void toolbar_action_setup(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==android.R.id.home )
            onBackPressed();
        return true;
    }

}
