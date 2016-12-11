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
 * Created by Ulnamsong on 2016. 12. 10..
 */

public class CreditDialog extends Dialog {
    private Context context;
    TextView tvDialogTitle = null;
    TextView tvTitle = null;
    TextView tvTitleContent = null;

    TextView tvCategory = null;
    TextView tvCategoryContent = null;

    TextView tvImportancy = null;
    TextView tvImportancyContent = null;

    Button closeButton = null;

    public CreditDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceUtil.loadTypeface(getContext());
        setContentView(R.layout.credit_dialog);

        TextView tvDialogTitle = (TextView) findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setTypeface(TypefaceUtil.typeface_m);

        tvTitle = (TextView) findViewById(R.id.tvDialogContent);
        tvTitleContent = (TextView) findViewById(R.id.tvTitleContent);

        tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvCategoryContent = (TextView) findViewById(R.id.tvCategoryContent);

        tvImportancy = (TextView) findViewById(R.id.tvImportancy);
        tvImportancyContent = (TextView) findViewById(R.id.tvImportancyContent);

        tvTitle.setTypeface(TypefaceUtil.typeface_m);
        tvTitleContent.setTypeface(TypefaceUtil.typeface);

        tvCategory.setTypeface(TypefaceUtil.typeface_m);
        tvCategoryContent.setTypeface(TypefaceUtil.typeface);

        tvImportancy.setTypeface(TypefaceUtil.typeface_m);
        tvImportancyContent.setTypeface(TypefaceUtil.typeface);

        closeButton = (Button) findViewById(R.id.closeButton);
        closeButton.setTypeface(TypefaceUtil.typeface_m);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
