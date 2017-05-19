package com.mstage.appkit.util;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mstage.appkit.model.Background;
import com.mstage.appkit.model.Font;
import com.mstage.appkit.model.StatusBarConfig;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Khang NT on 5/4/17.
 * Email: khang.neon.1997@gmail.com
 */

public class BindingAdapters {

    @BindingAdapter("bindBackground")
    public static void bindBackground(View view, Background background) {
        if (background != null) view.setBackground(background.asDrawable());
    }

    @BindingAdapter("bindFont")
    public static void bindFont(TextView textView, Font font) {
        if (font != null) {
            textView.setTypeface(font.getTypeface(textView.getContext()));
            textView.setTextColor(Color.parseColor(font.getFontColor()));
            if (font.getFontSize().endsWith("%")) {
                float originalSize = textView.getTextSize();
                float scale = Float.parseFloat(font.getFontSize().replace("%", "").trim());
                originalSize *= (scale / 100);
                textView.setTextSize(originalSize);
            } else {
                float ptSize = Float.parseFloat(font.getFontSize().replace("pt", "").trim());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, ptSize);
            }
        }
    }

    @BindingAdapter("bindStatusBar")
    public static void bindStatusBar(View fakeStatusBar, StatusBarConfig statusBarConfig) {
        if (statusBarConfig != null) {
            fakeStatusBar.setVisibility(statusBarConfig.isShow() ? View.VISIBLE : View.GONE);
            fakeStatusBar.setBackgroundColor(Color.parseColor(statusBarConfig.getColor()));
        }
    }

    @BindingAdapter(value = {"bindImage", "bindPlaceHolder"}, requireAll = false)
    public static void bindImage(ImageView imageView, String url, Object placeHolder) {
        if (TextUtils.isEmpty(url)) return;

        if (placeHolder instanceof Drawable) {
            Picasso.with(imageView.getContext())
                    .load(url)
                    .placeholder(((Drawable) placeHolder))
                    .into(imageView);
        } else if (placeHolder instanceof String) {
            Single.<Bitmap>create(emitter -> emitter.onSuccess(Picasso.with(imageView.getContext())
                    .load((String) placeHolder)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .get()))
                    .subscribeOn(Schedulers.io())
                    .subscribe(bitmap -> {
                        Picasso.with(imageView.getContext())
                                .load(url)
                                .placeholder(new BitmapDrawable(imageView.getResources(), bitmap))
                                .into(imageView);
                    }, throwable -> {
                        Timber.d(throwable, "Error while loading place holder: %s", placeHolder);
                        Picasso.with(imageView.getContext())
                                .load(url)
                                .into(imageView);
                    });
        } else {
            Picasso.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
        }
    }


    @SuppressWarnings("WrongConstant")
    @BindingAdapter("android:layout_gravity")
    public static void bindLayoutGravity(ViewGroup viewGroup, String gravityStr){
        ViewGroup.LayoutParams lp = viewGroup.getLayoutParams();
        int gravity = Util.parseGravity(gravityStr);
        if (lp instanceof LinearLayout.LayoutParams) ((LinearLayout.LayoutParams) lp).gravity = gravity;
        if (lp instanceof FrameLayout.LayoutParams) ((FrameLayout.LayoutParams) lp).gravity = gravity;
        if (lp instanceof ActionBar.LayoutParams) ((ActionBar.LayoutParams) lp).gravity = gravity;
        viewGroup.setLayoutParams(lp);
    }
}
