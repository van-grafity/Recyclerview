package com.example.addimgtorv;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

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

public class GaleryTwoActivity extends AppCompatActivity {

    String IMAGE_DIRECTORY = "/Zenwel";
    int GALERY_CODE = 1;
    int CAMERA_CODE = 2;
    @BindView(R.id.imageView1)
    ImageView imageView1;
    @BindView(R.id.imageView2)
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery_two);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_select)
    void showPicker(){
        showPictureDialog();
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALERY_CODE) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    saveImage(bitmap);
//                    String path = saveImage(bitmap);
                    Toast.makeText(GaleryTwoActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageView1.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(GaleryTwoActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA_CODE) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageView1.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(GaleryTwoActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
//
//        if (requestCode == GALERY_CODE) {
//            if (data != null) {
//                Uri contentURI = data.getData();
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//                    String path = saveImage(bitmap);
//                    Toast.makeText(GaleryTwoActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
//                    imageView2.setImageBitmap(bitmap);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Toast.makeText(GaleryTwoActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        } else if (requestCode == CAMERA_CODE) {
//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            imageView2.setImageBitmap(thumbnail);
//            saveImage(thumbnail);
//            Toast.makeText(GaleryTwoActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
//        }
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALERY_CODE);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CODE);
    }



}
