package com.example.addimgtorv;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GaleryAdapter extends RecyclerView.Adapter<GaleryAdapter.GaleryViewHolder> {
    private Context mContext;
    private ArrayList<Galery> items;
    private OnItemClick onItemClick;

    public GaleryAdapter(Context mContext, ArrayList<Galery> items) {
        this.mContext = mContext;
        this.items = items;
    }

    public interface OnItemClick {
        void getPosition(Galery data); //pass any things
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public GaleryAdapter.GaleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GaleryViewHolder(View.inflate(mContext, R.layout.item_galery, parent));
    }

    @Override
    public void onBindViewHolder(@NonNull GaleryAdapter.GaleryViewHolder holder, int position) {
        final Galery data = items.get(position);

        holder.tvNameImages.setText(data.getName());
        holder.ivImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.getPosition(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class GaleryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_images)
        ImageView ivImages;
        @BindView(R.id.tv_name_images)
        TextView tvNameImages;

        public GaleryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }

    }
}
