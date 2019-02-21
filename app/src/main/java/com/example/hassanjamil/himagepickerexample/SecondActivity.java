package com.example.hassanjamil.himagepickerexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.himagepickerlibrary.hImagePicker.ClassIImagesPick;
import com.example.himagepickerlibrary.hImagePicker.HImagePicker;
import com.example.himagepickerlibrary.hImagePicker.ImagePickManager;

import models.ConfigIPicker;

public class SecondActivity extends AppCompatActivity implements ClassIImagesPick.ImagePick {

    ImageView ivPickedImage;
    int REQ_CODE_CAMERA = 1331;
    int REQ_CODE_GALLERY = 1333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ivPickedImage = findViewById(R.id.ivPickedImage);
    }

    public void onPickClick(View view) {
        HImagePicker
                .getInstance()
                .config(new ConfigIPicker(REQ_CODE_CAMERA, REQ_CODE_GALLERY,
                        SecondActivity.this, null, null,
                        null, null, this))
                .load();
    }

    @Override
    public void onImagesPicked(int requestCode, int resultCode, Bundle bundle) {
        ivPickedImage.setImageURI(Uri.parse(bundle.getString(ImagePickManager.KEY_FILE_URI)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        HImagePicker.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HImagePicker.getInstance().onDestroy();
    }
}