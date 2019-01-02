package com.ulnamsong.todolist.Module;

import android.content.Context;
import android.graphics.Typeface;

import com.ulnamsong.todolist.R;

/**
 * Created by Ulnamsong on 2016. 12. 9..
 */

public class TypefaceUtil {
    public static Typeface typeface    = null;
    public static Typeface typeface_m  = null;
    public static Typeface typeface_b  = null;

    public static void loadTypeface(Context mContext) {
        if(typeface == null) {
            typeface = Typeface.createFromAsset(mContext.getAssets(), mContext.getString(R.string.main_font));
        }

        if(typeface_m == null) {
            typeface_m = Typeface.createFromAsset(mContext.getAssets(), mContext.getString(R.string.bold_font));
        }

        if(typeface_b == null) {
            typeface_b = Typeface.createFromAsset(mContext.getAssets(), mContext.getString(R.string.ex_bold_font));
        }
    }
}
