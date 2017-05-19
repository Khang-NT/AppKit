package com.mstage.appkit.util;

import android.view.Gravity;

/**
 * Created by Khang NT on 5/5/17.
 * Email: khang.neon.1997@gmail.com
 */

public class Util {

//    public static boolean isOnline(Context context) {
//        return NetworkUtil.getConnectivityStatus(context) != NetworkUtil.TYPE_NOT_CONNECTED;
//    }

    public static int parseGravity(String value) {
        if ("center".equalsIgnoreCase(value)) return Gravity.CENTER;
        if ("right".equalsIgnoreCase(value)) return Gravity.END;
        return Gravity.START;
    }
}
