package com.example.addimgtorv;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GaleryActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.rv_images)
    RecyclerView mRvImages;
    private GaleryAdapter mGaleryAdapter;
    private ArrayList<Galery> mGaleryItems;
    private Galery mGalery;
    Button btnSelectImage;
    int PICK_PHOTO_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery);
        ButterKnife.bind(this);
        btnSelectImage = findViewById(R.id.btn_select_image);
        btnSelectImage.setOnClickListener(this);
        mGaleryItems = new ArrayList<>();

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.ACTION_PICK, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PHOTO_CODE);
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        String[] filePath = {MediaStore.Images.Media.DATA};
        if (requestCode == PICK_PHOTO_CODE && resultCode == RESULT_OK && (data != null ? data.getData() : null) != null) {

            ClipData mClipData = data.getClipData();
            if (data.getClipData()!= null) {



                for (int i = 0; i < (mClipData != null ? mClipData.getItemCount() : 0); i++) {
                    ClipData.Item item = mClipData.getItemAt(i);
                    String picPath = getRealPathFromURI(getApplicationContext(), item.getUri());
                    File f = new File(picPath);
                    mGalery.setImage(f.getAbsolutePath());
//                    mGalery.setName(f.getName());
                    mGaleryItems.add(mGalery);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(GaleryActivity.this, RecyclerView.VERTICAL, false);
                    mRvImages.setLayoutManager(layoutManager);
                    mGaleryAdapter = new GaleryAdapter(this, mGaleryItems);
                    mRvImages.setAdapter(mGaleryAdapter);



                }
            }
        }
    }

    public Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }
}
