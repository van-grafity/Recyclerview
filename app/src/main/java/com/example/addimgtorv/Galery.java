package com.example.addimgtorv;

import android.net.Uri;
import android.widget.CheckBox;

public class Galery {
    Uri image;
    CheckBox chcbox;

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public CheckBox getChcbox() {
        return chcbox;
    }

    public void setChcbox(CheckBox chcbox) {
        this.chcbox = chcbox;
    }
}
