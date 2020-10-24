package com.healthtracker.empoweringtechnologies.healthtracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.Manifest;

public class Profile extends AppCompatActivity {

    public EditText profilename;
    public EditText gender;
    public EditText dob;
    public ImageView profilepicture;
    //public EditText profilepicture;
    public static final String ProfilePictureFolder = "MyProfilePicture";
    public String mCurrentPhotoPath;
    public static final int REQUEST_TAKE_PHOTO = 1;
    public static int Write_permissions = 0;
    private static final String TAG = "Profile";

    public MyDBHandler dbHandler;


    static final int DATE_DIALOG_ID = 0;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profilename = (EditText) findViewById(R.id.txtName);
        gender = (EditText) findViewById(R.id.txtGender);
        dob = (EditText) findViewById(R.id.txtDOB);
        profilepicture = (ImageView) findViewById(R.id.profilepicture);

        dob.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(v == dob) {
                    dob.setError(null);
                    showDialog(DATE_DIALOG_ID);
                }

                return false;
            }
        });

        dbHandler = new MyDBHandler(this,null,null,1);

        if(profilename.getText().toString().length() == 0) {
            Cursor cursor = dbHandler.FetchProfileData("1");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                profilename.setText(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_NAME)));
                gender.setText(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_GENDER)));
                dob.setText(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_DOB)));
                // show profile picture
                ShowProfilePicture(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_PICTUREPATH)));
            }
        }
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,mDateSetListener,  cyear, cmonth, cday);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        // onDateSet method
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date_selected = String.valueOf(monthOfYear+1)+" /"+String.valueOf(dayOfMonth)+" /"+String.valueOf(year);
            //Toast.makeText(getApplicationContext(), "Selected Date is ="+date_selected, Toast.LENGTH_SHORT).show();
            dob.setText(date_selected);
        }
    };
    public void ShowProfilePicture(String filename)
    {
        // show profile picture
        if (filename!= null && !filename.isEmpty()) {
            File imgFile = new File(filename);
            if (imgFile.exists()) {

                Uri uri = Uri.fromFile(imgFile);
                ImageView myImage = (ImageView) findViewById(R.id.profilepicture);
                myImage.setImageURI(uri);
            }
        }
    }
    public void GoHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void ProfilePicture(View view)
    {
        UploadProfilePicture();

    }
    /* method to add or save activity data */
    public void RecordProfileData(View view) {

        ProfileData profiledata = new ProfileData(profilename.getText().toString(), gender.getText().toString(),dob.getText().toString(),mCurrentPhotoPath);
        /* validations on user iput data */
        if (profilename.getText().toString().length() == 0) {
            profilename.setError("Name is required!");
            return;
        }

        if (gender.getText().toString().length() == 0) {
            gender.setError("Gender is required!");
            return;
        }
        if (dob.getText().toString().length() == 0) {
            dob.setError("Date of Birth is required!");
            return;
        }
        dbHandler.SaveProfile(profiledata);

    }

    /* method to take a picture of volunteering and save to gallery */
    public void UploadProfilePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);


                //File dest = new File(Environment.DIRECTORY_PICTURES + "/profilePicture.jpg");

                try {
                    dbHandler.UpdateProfilePicturePath(photoFile.toString());
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                /* add the picture to gallery */
                galleryAddPic();


            }
        }
    }

    /* method to create the picture file location */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //String imageFileName = "JPEG_" + timeStamp + "_";
        String imageFileName = "ProfilePicture";

        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES );
        File image = null;
        /*
        image = new File(storageDir + "/ProfilePicture.jpg");

        if(!image.exists())
        {
            image.createNewFile();
        }
        */

        if(isStoragePermissionGranted() ||  (Write_permissions == 1)) {

            try {

                image = File.createTempFile(
                        imageFileName,
                        ".jpg",
                        storageDir
                );
                // Save a file: path for use with ACTION_VIEW intents
                mCurrentPhotoPath = "file:" + image.getAbsolutePath();

            } catch (Exception ex) {
                ex.getMessage();
            }

        }
        return image;
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
            Write_permissions = 1;
        }
    }


    /* method to add picture to gallery */
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
}
