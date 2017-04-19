package com.example.ahmed.projectlms.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmed.projectlms.R;
import com.example.ahmed.projectlms.app.AndroidMultiPartEntity;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

/**
 * Created by Mohab on 4/9/2017.
 */

public class UploadActivity extends AppCompatActivity
{

    final int FileManger_code=307;
    public static int READ_EXTERNAL_STORAGE_PERMISSION_CODE=200;
    public static String READ_EXTERNAL_STORAGE="android.permission.READ_EXTERNAL_STORAGE";
    View btt ;
    TextView txtPercentage;
    ProgressBar progressBar ;
    View progressBarView ;
    private String uploaded_file_name;
    private ArrayList<String> filePaths =  new ArrayList<>();


    EditText editTextUploadFileName, editTextUploadDescription;
    TextInputLayout editTextUploadFileNameLayout, editTextUploadDescriptionLayout;
    CardView cardViewUploadBtn;
    View imageButtonAttach;
    private String fileName_from_user;
    private String fileDesc_from_user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        editTextUploadFileName = (EditText) findViewById(R.id.edittext_upload_filename);
        editTextUploadFileNameLayout = (TextInputLayout) findViewById(R.id.edittext_upload_filename_layout);
        editTextUploadDescriptionLayout = (TextInputLayout) findViewById(R.id.edittext_upload_desc_layout);
        editTextUploadDescription = (EditText) findViewById(R.id.edittext_upload_description);
        imageButtonAttach =  findViewById(R.id.imagebutton_attach);

        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBarView = findViewById(R.id.progressBarView);

        imageButtonAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEditText(editTextUploadFileName,editTextUploadFileNameLayout)){
                    return;
                }
                if (!validateEditText(editTextUploadDescription,editTextUploadDescriptionLayout)){
                    return;
                }

                fileName_from_user = editTextUploadFileName.getText().toString().trim();
                fileDesc_from_user = editTextUploadDescription.getText().toString().trim();
                pickFile();
            }
        });



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
    void pickFile() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_PERMISSION_CODE
            );

            return;
        }
        FilePickerBuilder
                .getInstance().setMaxCount(1)
                .setActivityTheme(R.style.AppTheme)
                .setSelectedFiles(filePaths)
                .pickDocument(this);

//        launchPicker();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode)
        {
            case FilePickerConst.REQUEST_CODE_PHOTO:
                if(resultCode== Activity.RESULT_OK && data!=null)
                {
//                    photoPaths = new ArrayList<>();
//                    photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_PHOTOS));
                }
                break;
            case FilePickerConst.REQUEST_CODE_DOC:
                if(resultCode== Activity.RESULT_OK && data!=null)
                {
//                    docPaths = new ArrayList<>();
//                    docPaths.addAll();
                    new upload_file_class().execute( data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS).get(0));
                    Log.e("path"  , data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS).get(0));
                }
                break;
            case FileManger_code :{
                if (resultCode == RESULT_OK){
                    Uri uri =  data.getData();
                    File file = new File(uri.getPath());
                    File parent = new File(file.getParent()) ;
                    Log.e("path" , data.toString() +"   "   + "  " + data.getData().getPath());
                    Log.e("path2" ,file.getParent() +"  " + file.getName()  + "  " +  parent.getAbsolutePath());


                    Uri selected = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Log.d("Uri", selected + "");
                    Log.d("filePathColumn", MediaStore.Images.Media.DATA + "");

                    Cursor cursor = getContentResolver().query(selected,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    Log.d("picturePath", picturePath + "");

                }
            }
            break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchPicker();
            }
        }
    }


    void launchPicker() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {

            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FileManger_code);
        } catch (ActivityNotFoundException e) {
            //alert user that file manager not working
            Toast.makeText(this, "file manger not working ", Toast.LENGTH_SHORT).show();
        }
    }


    public class upload_file_class extends AsyncTask<String, Integer, String> {
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

            uploadFile = new File(filePath);
            fileName = uploadFile.getName();
            response = uploadFile(uploadFile);


            return    response ;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(String result) {
            Log.e("result",result);
            showAlert(result);
            progressBarView.setVisibility(View.GONE);
        }

        /**
         * Method to show alert dialog
         * */
        protected void showAlert(String message) {
            if (ok){
                String filePathName = new File(filePath).getName();
                Log.e("category","filename  "+fileName +"file path   " + filePathName);

            }
            AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
            builder.setMessage(message).setTitle("Response from Servers")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // do nothing
                            Intent intent = new Intent();
                            intent.putExtra("file_loc",uploaded_file_name);
                            intent.putExtra("file_name",fileName_from_user);
                            intent.putExtra("file_Desc",fileDesc_from_user);
                            intent.putExtra("id",getIntent().getExtras().getString("id"));
                            setResult(RESULT_OK ,intent);
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();


        }

        @SuppressWarnings("deprecation")
        protected String uploadFile(File sourceFile) {
            String responseString = null;
            uploaded_file_name = currentDateFormat()+sourceFile.getName() ;
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
                entity.addPart("FileNme",new StringBody(uploaded_file_name, ContentType.TEXT_PLAIN));


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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }


    void toolbar_action_setup(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final View notificationbtn = findViewById(R.id.main_toolbar_noti);
        final View menu_action = findViewById(R.id.main_toolbar_inbox);
        TextView page_title = (TextView) findViewById(R.id.main_toolbar_title);
        page_title.setText(title);
        notificationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notificationbtn == view) {
                    Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                    startActivity(intent);
                }
            }
        });
        menu_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menu_action == view) {
                    startActivity(new Intent(getApplicationContext(), MessActivity.class));


                }
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==android.R.id.home )
            onBackPressed();
        return true;
    }

}