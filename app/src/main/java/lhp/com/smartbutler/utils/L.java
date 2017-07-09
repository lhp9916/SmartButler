package lhp.com.smartbutler.utils;

import android.util.Log;

/**
 * Log封装类
 */

public class L {
    //调试开关
    private static final boolean DEBUG = true;
    private static final String TAG = "Smartbutler";

    public static void d(String text) {
        if (DEBUG) {
            Log.d(TAG, text);
        }
    }

    public static void i(String text) {
        if (DEBUG) {
            Log.i(TAG, text);
        }
    }

    public static void w(String text) {
        if (DEBUG) {
            Log.w(TAG, text);
        }
    }

    public static void e(String text) {
        if (DEBUG) {
            Log.e(TAG, text);
        }
    }
}
