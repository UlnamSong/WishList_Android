package com.ulnamsong.wishlist.DataBase;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.ulnamsong.wishlist.R;

/**
 * Created by Ulnamsong on 2016. 12. 9..
 */

public class ImportancyArray {

    private static Context context;

    public ImportancyArray(Context context) {
        this.context = context;
    }

    public static Drawable importancyRegular = context.getResources().getDrawable(R.drawable.importancy);
    public static Drawable importancyHigh = context.getResources().getDrawable(R.drawable.importancy_high);
    public static Drawable importancyLow = context.getResources().getDrawable(R.drawable.importancy_low);
    public static Drawable importancyFinished = context.getResources().getDrawable(R.drawable.importancy_finished);
}
