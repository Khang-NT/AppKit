package com.mstage.appkit.util;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Color;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
            if (!TextUtils.isEmpty(font.getFontFamily())) {
                textView.setTypeface(font.getTypeface(textView.getContext()));
            } else if (!TextUtils.isEmpty(font.getFontUrl())) {
                font.getTypeFaceOnline(textView.getContext(), textView::setTypeface);
            }
            textView.setTextColor(Color.parseColor(font.getFontColor()));
            if (!TextUtils.isEmpty(font.getFontSize())) {
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

        Context context = imageView.getContext();
        if (placeHolder instanceof Drawable) {
            Picasso.with(context)
                    .load(url)
                    .placeholder(((Drawable) placeHolder))
                    .into(imageView);
        } else if (placeHolder instanceof String) {
            Picasso.with(context)
                    .load(String.valueOf(placeHolder))
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Picasso.with(context)
                                    .load(url)
                                    .placeholder(imageView.getDrawable())
                                    .into(imageView);
                        }

                        @Override
                        public void onError() {
                            Picasso.with(context)
                                    .load(url)
                                    .into(imageView);
                        }
                    });
        } else {
            Picasso.with(context)
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

    @BindingAdapter("bindMarginBottom")
    public static void bindMarginBottom(View view, String marginStr) {
        int margin = (int) Util.parsePoint(view.getContext(), marginStr, Integer.MAX_VALUE);
        if (margin != Integer.MAX_VALUE && view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams lp = ((ViewGroup.MarginLayoutParams) view.getLayoutParams());
            lp.bottomMargin = margin;
            view.setLayoutParams(lp);
        } else {
            Timber.e("Can't set margin (%s) for view: " + view);
        }
    }
}
