package com.ulnamsong.wishlist.Module;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Ulnamsong on 2016. 12. 9..
 */

public class TypefaceUtil {
    public static Typeface typeface    = null;
    public static Typeface typeface_m  = null;
    public static Typeface typeface_b  = null;

    public static void loadTypeface(Context mContext) {
        if(typeface == null) {
            typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/NanumSquareOTFRegular.otf");
        }

        if(typeface_m == null) {
            typeface_m = Typeface.createFromAsset(mContext.getAssets(), "fonts/NanumSquareOTFBold.otf");
        }

        if(typeface_b == null) {
            typeface_b = Typeface.createFromAsset(mContext.getAssets(), "fonts/NanumSquareOTFExtraBold.otf");
        }
    }
}
