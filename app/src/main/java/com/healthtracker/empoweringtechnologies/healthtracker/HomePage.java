package com.healthtracker.empoweringtechnologies.healthtracker;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import android.provider.MediaStore;
import java.util.Date;
import java.util.Locale;
import android.location.Criteria;


public class HomePage extends AppCompatActivity {
    private SimpleDateFormat dateFormatter;
    public static final String ProfilePictureFolder = "MyProfilePicture";
    public String mCurrentPhotoPath;
    public static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void TrackBP(View view)
    {
        Intent intent = new Intent(this, ShowBPData.class);
        startActivity(intent);
    }

    public void GoHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void GoLipidData(View view)
    {
        Intent intent = new Intent(this, ShowLipidProfile.class);
        startActivity(intent);
    }

    public void GoWeightData(View view)
    {
        Intent intent = new Intent(this, ShowWeightProfile.class);
        startActivity(intent);
    }

    public void GoProfile(View view)
    {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }


    public void ShowLatestHealthData(View view)
    {
        Intent intent = new Intent(this, LatestHealthData.class);
        startActivity(intent);
    }

    public void ShowKidneyFunction(View view)
    {
        Intent intent = new Intent(this, ShowKidneyFunction.class);
        startActivity(intent);
    }




    public void ShowThyroidData(View view)
    {
        Intent intent = new Intent(this, ShowThyroidProfile.class);
        startActivity(intent);
    }

    public void ShowSugarData(View view)
    {
        Intent intent = new Intent(this, ShowSugarProfile.class);
        startActivity(intent);
    }

    /* method to take a picture of volunteering and save to gallery */
    public void UploadProfilePicture(View view) {
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
        try {

            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = "file:" + image.getAbsolutePath();

        } catch (Exception ex) {
            ex.getMessage();
        }
        return image;
    }

    /* method to add picture to gallery */
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }




}

