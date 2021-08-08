package com.example.capiter.app.helper;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.example.capiter.R;
import com.example.capiter.app.GlideApp;

public class ImageViewHelper {
    @BindingAdapter(value = {"app:imageUrl", "app:placeholder", "app:error"}, requireAll = false)
    public static void setImageUrl(ImageView imageView, String url, Drawable placeholder, Drawable error) {
        if (url == null) {
            imageView.setImageDrawable(placeholder == null ? ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_arrow_back) : placeholder);
        } else {
            GlideApp.with(imageView).load(url)
                    //.transition(DrawableTransitionOptions.withCrossFade(100))
                    .placeholder(placeholder == null ? ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_arrow_back) : placeholder)
                    .centerInside()
                    .error(error)
                    .into(imageView);
        }
    }
}
