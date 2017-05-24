package com.mstage.appkit.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;

import timber.log.Timber;

/**
 * Created by Khang NT on 5/5/17.
 * Email: khang.neon.1997@gmail.com
 */

public class Util {

    public static int parseGravity(String value) {
        if ("center".equalsIgnoreCase(value)) return Gravity.CENTER;
        if ("right".equalsIgnoreCase(value)) return Gravity.END;
        return Gravity.START;
    }

    public static float parsePoint(Context context, String value, float defaultValue) {
        try {
            if (TextUtils.isEmpty(value)) return defaultValue;
            if (value.contains("pt")) value = value.replace("pt", "");
            value = value.trim();
            float ptSize = Float.parseFloat(value);
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, ptSize,
                    context.getResources().getDisplayMetrics());
        } catch (Throwable ignore) {
            Timber.d(ignore);
            return defaultValue;
        }
    }
}
