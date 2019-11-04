package com.example.addimgtorv;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_move_to_activity)
    Button btnSelectImages;
    @BindView(R.id.btn_select_two)
    Button btnSelectTwo;
    int GALERY_CODE = 1;
    int CAMERA_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_move_to_activity)
    void getStorageGalery() {
        startActivity(new Intent(MainActivity.this, GaleryActivity.class));
    }

    @OnClick(R.id.btn_select_two)
    void selectTwo() {
       startActivity(new Intent(MainActivity.this, GaleryTwoActivity.class));
    }

}
