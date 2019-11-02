package com.example.addimgtorv;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GaleryActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.rv_images)
    RecyclerView mRvImages;
    private GaleryAdapter mGaleryAdapter;
    private ArrayList<Galery> mGaleryItems = new ArrayList<>();
    private Galery mGalery;
    Button btnSelectImage;
    int PICK_PHOTO_CODE = 1;
    int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery);
        ButterKnife.bind(this);
        btnSelectImage = findViewById(R.id.btn_select_image);
        btnSelectImage.setOnClickListener(this);

        mGaleryItems = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(GaleryActivity.this, LinearLayoutManager.VERTICAL, false);
        mRvImages.setLayoutManager(layoutManager);

        mGaleryAdapter = new GaleryAdapter(this, mGaleryItems);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
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
        if (data.getClipData() != null) {
            ClipData mClipData = data.getClipData();
            for (int i = 0; i < mClipData.getItemCount(); i++) {
                ClipData.Item item = mClipData.getItemAt(i);
                String picPath = getRealPathFromURI(getApplicationContext(), item.getUri());
                File f = new File(picPath);
//                    mItemModel.setName(f.getName());
                mGalery.setImage(item.getUri());
                mGaleryItems.add(mGalery);
                mRvImages.setAdapter(mGaleryAdapter);
            }

        }
    }
}
