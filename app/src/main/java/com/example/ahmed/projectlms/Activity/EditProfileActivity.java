package com.example.ahmed.projectlms.Activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.bumptech.glide.Glide;
import com.example.ahmed.projectlms.Models.UserModel;
import com.example.ahmed.projectlms.R;
import com.example.ahmed.projectlms.app.AndroidMultiPartEntity;
import com.example.ahmed.projectlms.app.AppController;
import com.example.ahmed.projectlms.app.Config;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by ahmed on 4/3/2017.
 */

public class EditProfileActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {


    ImageView uploaded_img ;
    String uploaded_img_name = "" ;
    TextView txtPercentage;
    ProgressBar progressBar ;
    View add_img , progressBarView ;
    protected int RESULT_LOAD_IMAGE=1;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 262 ;


    EditText studentID,firstName,lastName,passwordText,confirmPassword , email;
    Button signUP;
    String pstudentID,pfirstName,plastName,ppassword,ppasswordConfirm  ;
    TextInputLayout studentIDLayout,firstNameLayout,lastNameLayout,passwordLayout,confirmPasswordLayout , email_layout;
    private String TAG = EditProfileActivity.class.getSimpleName();
    ArrayList<String> classes = new ArrayList<String>();
    ArrayList<String> classes_id = new ArrayList<String>();
    ArrayAdapter adapter ;
    Spinner spinner;
    private String class_txt = "" , class_id_choosed="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);
//

        //initialization
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState == null){
            no_storage_permission();
        }
        add_img = findViewById(R.id.change_img);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_img();
            }
        });
        uploaded_img = (ImageView)  findViewById(R.id.avatar);

        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBarView = findViewById(R.id.progressBarView);

        firstName = (EditText) findViewById(R.id.student_first_name);
        lastName = (EditText)  findViewById(R.id.student_last_name);
        passwordText = (EditText) findViewById(R.id.student_password);
        confirmPassword = (EditText) findViewById(R.id.student_confirm_password);
        email = (EditText) findViewById(R.id.email);


        firstName.setText(AppController.getInstance().getPrefManager().getUser().getFirstName());
        lastName.setText(AppController.getInstance().getPrefManager().getUser().getLastName());
        passwordText.setText(AppController.getInstance().getPrefManager().getUser().getPassword());
        confirmPassword.setText(AppController.getInstance().getPrefManager().getUser().getPassword());
        email.setText(AppController.getInstance().getPrefManager().getUser().getEmail());

        Glide.with(getApplicationContext()).load(Config.img_url+AppController.getInstance().getPrefManager().getUser().getStudent_img()).crossFade().into(uploaded_img);





        signUP = (Button) findViewById(R.id.button_signup);

        email_layout = (TextInputLayout) findViewById(R.id.email_layout);
        studentIDLayout = (TextInputLayout) findViewById(R.id.layout_student_id);
        firstNameLayout = (TextInputLayout) findViewById(R.id.input_layout_first_name);
        lastNameLayout = (TextInputLayout) findViewById(R.id.input_layout_last_name);
        passwordLayout = (TextInputLayout) findViewById(R.id.input_layout_password);
        confirmPasswordLayout = (TextInputLayout) findViewById(R.id.input_layout_confirm_password);



        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                findViewById(R.id.loadingSpinner).setVisibility(View.VISIBLE);
                register();
            }
        });
        toolbar_action_setup(getString(R.string.edit));
    }

    private void register()
    {
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

        final Map<String, String> params = new HashMap<>();

        if (validateImg()) {

            params.put("img","uploads/"+uploaded_img_name);
        }

        pfirstName= firstName.getText().toString();
        plastName=lastName.getText().toString();
        ppassword= passwordText.getText().toString();

        params.put("id",AppController.getInstance().getPrefManager().getUser().getStudent_id());
        params.put("firstname",pfirstName);
        params.put("lastname",plastName);
        params.put("password",ppassword);
        params.put("email",email.getText().toString());
        params.put("class_id",AppController.getInstance().getPrefManager().getUser().getClass_id());


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URL + "edit_profile.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "signup response" + response);
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
                        user.setEmail(userobj.getString("email"));

                        AppController.getInstance().getPrefManager().storeUser(user);
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

                        findViewById(R.id.loadingSpinner).setVisibility(View.GONE);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                findViewById(R.id.loadingSpinner).setVisibility(View.GONE);
                if (error instanceof TimeoutError) {
                }
                if (error instanceof NoConnectionError) {

                    String message =   "please check connection";
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();

                }
            }
        })
        {

            @Override
            protected Map<String, String> getParams() {
                Log.e(TAG , params.toString());
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


    void get_img(){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /// select from gallery section
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data!=null){

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Log.e("Uri",selectedImage + "");
            Log.e("filePathColumn",MediaStore.Images.Media.DATA + "");

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);

            cursor.close();

            Log.e("picturePath",picturePath + "");
            new ResizeImage().execute(picturePath);

        }
    }




    public class ResizeImage extends AsyncTask<String, Integer, String> {
        protected String  filePath,fileName ;
        long totalSize = 0;
        Boolean ok = false ;
        Bitmap resized_bitmap ;


        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBar.setVisibility(View.VISIBLE);
            progressBarView.setVisibility(View.VISIBLE);
            // updating progress bar value
            progressBar.setProgress(progress[0]);

            // updating percentage value
            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        // Decode image in background.
        @Override
        protected String doInBackground(String... params) {
            filePath = params[0];
            File uploadFile = null ;
            String response = null;
            try {
                resized_bitmap =  decodeSampledBitmapFromPath(filePath, 360, 360) ;
                uploadFile = bitmaptofile(resized_bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (uploadFile == null){
                Log.e("category","file"+"null");
                uploadFile = new File(filePath);
            }
            fileName = uploadFile.getName();
            response = uploadFile(uploadFile);


            return    response ;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(String result) {
            showAlert(result);
            progressBarView.setVisibility(View.GONE);
        }

        protected File  bitmaptofile(Bitmap bitmap) throws IOException {
            File outputDir = getApplicationContext().getCacheDir(); // context being the Activity pointer
            File outputFile = File.createTempFile(currentDateFormat(),".jpg" , outputDir);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return outputFile;
        }
        /**
         * Method to show alert dialog
         * */
        protected void showAlert(String message) {
            if (ok){
                String filePathName = new File(filePath).getName();
                Log.e("category","filename  "+fileName +"file path   " + filePathName);
                uploaded_img_name = fileName;
                uploaded_img.setImageBitmap(resized_bitmap);
                validateImg();
            }
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(EditProfileActivity.this);
            builder.setMessage(message).setTitle("Response from Servers")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // do nothing
                        }
                    });
            android.app.AlertDialog alert = builder.create();
            alert.show();


        }

        @SuppressWarnings("deprecation")
        protected String uploadFile(File sourceFile) {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.BASE_URL0+"admin/"+"upload.php");
            Log.e("category","url"+httppost.getURI());
            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                Log.e("transferd" , num +"");
                                int precentage = (int) ((num / (float) totalSize) * 100);
                                if (precentage<99)
                                    publishProgress(precentage);

                            }
                        });


                // Adding file data to http body
                entity.addPart("file", new FileBody(sourceFile));
                entity.addPart("FileNme",new StringBody(sourceFile.getName(), ContentType.TEXT_PLAIN));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                    ok=true ;
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

    }

    public static String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.ENGLISH);
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }
    public static Bitmap decodeSampledBitmapFromPath( String FilePath,
                                                      int reqWidth, int reqHeight) {


        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile( FilePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile( FilePath, options);
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.e("sapleSize" , inSampleSize + "");
        return inSampleSize;
    }
    protected boolean validateImg() {
        if ( uploaded_img_name.equals("null") || uploaded_img_name.isEmpty() ){
            return false ;
        }else {
        }
        return true ;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    //  no_gps_permition();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(EditProfileActivity.this, "Permission needed to run your app", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    private void no_storage_permission() {
        // Here, thisActivity is the current activity
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {



            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE);


        }
    }

}
