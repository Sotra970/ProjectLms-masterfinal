package com.example.ahmed.projectlms.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ahmed.projectlms.Models.UserModel;
import com.example.ahmed.projectlms.R;
import com.example.ahmed.projectlms.app.AppController;
import com.example.ahmed.projectlms.app.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ahmed on 4/4/2017.
 */

public class LoginActivity extends AppCompatActivity  {
    EditText studentID,passwordText;
    Button signIn,register;
    private String TAG = LoginActivity.class.getSimpleName();
    String pname,ppass;
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutPass;
    private String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialization

        inputLayoutName = (TextInputLayout) findViewById(R.id.layout_student_id);
        inputLayoutPass = (TextInputLayout) findViewById(R.id.input_layout_password);
        studentID = (EditText) findViewById(R.id.student_id);
        passwordText = (EditText) findViewById(R.id.student_password);

        signIn = (Button) findViewById(R.id.signin_button);
        register = (Button) findViewById(R.id.signup_button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( getApplicationContext() , SignupActivity.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             login();

            }
        });
    }

    public void login()
    {

            if (!validateName()) {
                return;
            }

            if (!validatePass()) {
                return;
            }
            pname = studentID.getText().toString();
            ppass = passwordText.getText().toString();



        findViewById(R.id.loadingSpinner).setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URL + "login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login response" + response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String res = obj.getString("response");
                    if (res.equals("success")) {
                        JSONObject userobj = obj.getJSONObject("data");
                        UserModel user = new UserModel();

                        user.setStudent_id(userobj.getString("student_id"));
                        user.setFirstName(userobj.getString("firstname"));
                        user.setLastName(userobj.getString("lastname"));
                        user.setClass_id(userobj.getString("class_id"));
                        user.setPassword(userobj.getString("password"));
                        user.setUserName(userobj.getString("username"));
                        user.setStatus(userobj.getString("status"));
                        user.setStudent_img(userobj.getString("location"));


                        AppController.getInstance().getPrefManager().storeUser(user);
                        findViewById(R.id.loadingSpinner).setVisibility(View.GONE);
                        Intent intent = new Intent(new Intent(getApplicationContext(), StudentHomeActivity.class));
                        ComponentName cn = intent.getComponent();
                        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                        startActivity(mainIntent);
                        finish();
                    } else {
                        findViewById(R.id.loadingSpinner).setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), getString(R.string.login_err), Toast.LENGTH_LONG).show();
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
                        findViewById(R.id.loadingSpinner).setVisibility(View.GONE);
                        String message =   "please check connection";
                        Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();
                    }
                }
        })
            {
                //sending your email and pass
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", pname);
                    params.put("password",ppass);

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
    private boolean validateName() {
        if (studentID.getText().toString().trim().isEmpty()) {
            incorrectUserName();
            return false;
        } else {

            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private void incorrectUserName() {
        inputLayoutName.setError(getString(R.string.Enter_valid_Username));
        requestFocus(studentID);
    }

    // Validating Pass
    private boolean validatePass() {
        String Pass = passwordText.getText().toString().trim();

        if (Pass.isEmpty()) {
            incorrectPass();
            return false;
        } else {
            inputLayoutPass.setErrorEnabled(false);
        }

        return true;
    }

    private void incorrectPass() {
        inputLayoutPass.setError(getString(R.string.Enter_valid_PASS));
        requestFocus(passwordText);
    }
    private void requestFocus(View view) {
        Log.d("requestFocus",view.requestFocus()+"");
        //foucus on view
        view.requestFocus();


    }
}

