package com.example.addimgtorv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GaleryAdapter extends RecyclerView.Adapter<GaleryAdapter.GaleryViewHolder> {
    private static final String TAG = "GaleryAdapter";
    private Context mContext;
    private ArrayList<Galery> items;


    public GaleryAdapter(Context mContext, ArrayList<Galery> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @NonNull
    @Override
    public GaleryAdapter.GaleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_galery, parent, false);
        return new GaleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GaleryAdapter.GaleryViewHolder holder, int position) {
        final Galery data = items.get(position);
        Glide.with(mContext)
                .load(data.getImage())
                .into(holder.imgThumb);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

//    private void setBitmap(final ImageView iv, final int id) {
//
//        new AsyncTask<Void, Void, Bitmap>() {
//
//            @Override
//            protected Bitmap doInBackground(Void... params) {
//                return MediaStore.Images.Thumbnails.getThumbnail(mContext.getContentResolver(), id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
//            }
//
//            @Override
//            protected void onPostExecute(Bitmap result) {
//                super.onPostExecute(result);
//                iv.setImageBitmap(result);
//            }
//        }.execute();
//    }

    static class GaleryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgThumb)
        ImageView imgThumb;
        @BindView(R.id.chkImage)
        CheckBox chkImage;

        public GaleryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }

    }
}
