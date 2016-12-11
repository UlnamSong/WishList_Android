package com.ulnamsong.wishlist.Activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ulnamsong.wishlist.Module.TypefaceUtil;
import com.ulnamsong.wishlist.R;

/**
 * Created by Ulnamsong on 2016. 12. 9..
 */

public class WishInfoDialog extends Dialog {
    private Context context;
    TextView tvDialogTitle = null;
    TextView tvTitle = null;
    TextView tvTitleContent = null;

    TextView tvCategory = null;
    TextView tvCategoryContent = null;

    TextView tvImportancy = null;
    TextView tvImportancyContent = null;

    TextView tvMax = null;
    TextView tvMaxContent = null;

    Button doButton = null;
    Button deleteButton = null;
    TextView backButton = null;

    String title;
    String startDate;

    int id;
    int categoryIndex;
    int importancy;
    int curValue;
    int maxValue;

    int exitStatus = 0; // 0 : Nothing, 1 : Do Once, 2 : Delete

    public WishInfoDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceUtil.loadTypeface(getContext());
        setContentView(R.layout.wishinfo_dialog);

        TextView tvDialogTitle = (TextView) findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setTypeface(TypefaceUtil.typeface_m);

        tvTitle = (TextView) findViewById(R.id.tvDialogContent);
        tvTitleContent = (TextView) findViewById(R.id.tvTitleContent);

        tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvCategoryContent = (TextView) findViewById(R.id.tvCategoryContent);

        tvImportancy = (TextView) findViewById(R.id.tvImportancy);
        tvImportancyContent = (TextView) findViewById(R.id.tvImportancyContent);

        tvMax = (TextView) findViewById(R.id.tvMax);
        tvMaxContent = (TextView) findViewById(R.id.tvMaxContent);

        doButton = (Button) findViewById(R.id.doButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        backButton = (TextView) findViewById(R.id.backButton);
        backButton.setTypeface(TypefaceUtil.typeface_m);

        doButton.setTypeface(TypefaceUtil.typeface_m);
        deleteButton.setTypeface(TypefaceUtil.typeface_m);

        tvTitle.setTypeface(TypefaceUtil.typeface_m);
        tvTitleContent.setTypeface(TypefaceUtil.typeface);

        tvCategory.setTypeface(TypefaceUtil.typeface_m);
        tvCategoryContent.setTypeface(TypefaceUtil.typeface);

        tvImportancy.setTypeface(TypefaceUtil.typeface_m);
        tvImportancyContent.setTypeface(TypefaceUtil.typeface);

        tvMax.setTypeface(TypefaceUtil.typeface_m);
        tvMaxContent.setTypeface(TypefaceUtil.typeface);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitStatus = 0;
                dismiss();
            }
        });

        doButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitStatus = 1;
                dismiss();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitStatus = 2;
                dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        exitStatus = 0;
        super.onBackPressed();
    }
}
