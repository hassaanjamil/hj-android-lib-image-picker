package com.example.hassanjamil.himagepickerexample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.esafirm.imagepicker.model.Image;
import com.example.himagepickerlibrary.hImagePicker.ClassIImagesPick;
import com.example.himagepickerlibrary.hImagePicker.HImagePicker;

import java.util.ArrayList;

import models.ConfigIPicker;

public class ExampleFragment extends Fragment implements ClassIImagesPick.ImagePick {

    private TextView tvPickedImages;
    private ArrayList<Image> mImages = new ArrayList<>();
    private int limit = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.example_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvPickedImages = view.findViewById(R.id.tvPickedImages);
        view.findViewById(R.id.btnPick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickClick();
            }
        });
    }

    private void onPickClick() {
        HImagePicker.getInstance()
                .config(new ConfigIPicker(this)
                        //.setDialogTitle("Pick Image From")
                        //.setDialogStrCamera("Camera")
                        //.setDialogStrGallery("Gallery")
                        .setLayoutDirection("rtl")
                        .setListener(this)
                        .setLimit(limit)
                        .setShowCamera(false)
                        .include(mImages)
                        .setDirPath(getCustomDirectoryPath(getContext())))
                .load();
    }

    private String getCustomDirectoryPath(@NonNull Context context) {
        String externalStoragePath = ((android.os.Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED)))
                ? android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
                : context.getFilesDir().getAbsolutePath();
        return externalStoragePath + "/" + context.getString(R.string.app_name);
    }

    @Override
    public void onImagesPicked(int requestCode, int resultCode, ArrayList<Image> images,
                               boolean isRequestFromGallery) {
        if (mImages != null && isRequestFromGallery) {
            mImages.clear();
            mImages.addAll(images);
        } else if (mImages != null) {
            mImages.add(images.get(0));
        }


        StringBuilder p = new StringBuilder();
        for (Image image : mImages) {
            p.append("\n").append(image.getPath());
        }
        tvPickedImages.setText(p);
    }

    @Override
    public void onCancelled() {
    }

    @Override
    public void onDismissed(boolean optionSelected) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        HImagePicker.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        HImagePicker.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        HImagePicker.getInstance().onDestroy();
    }
}