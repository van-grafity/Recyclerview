package com.example.addimgtorv;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GaleryActivity extends AppCompatActivity {
    @BindView(R.id.rv_images)
    RecyclerView mRvImages;
    private GaleryAdapter mGaleryAdapter;
    private MainActivity mMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery);
        ButterKnife.bind(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mMainActivity.OPEN_DOCUMENT_CODE && resultCode == RESULT_OK) {

        }
    }
}
