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

public class TwoButtonDialog extends Dialog {
    private Context context;
    TextView tvDialogTitle = null;
    TextView tvDialogContent = null;

    Button positiveButton = null;
    Button negativeButton = null;

    int exitStatus = 0; // 0 : negative, 1 : positive

    public TwoButtonDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceUtil.loadTypeface(getContext());
        setContentView(R.layout.twobutton_dialog);

        tvDialogTitle = (TextView) findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setTypeface(TypefaceUtil.typeface_m);

        tvDialogContent = (TextView) findViewById(R.id.tvDialogContent);
        tvDialogContent.setTypeface(TypefaceUtil.typeface);

        positiveButton = (Button) findViewById(R.id.positive_button);
        positiveButton.setTypeface(TypefaceUtil.typeface_m);

        negativeButton = (Button) findViewById(R.id.negative_button);
        negativeButton.setTypeface(TypefaceUtil.typeface_m);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitStatus = 1;
                dismiss();
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitStatus = 0;
                dismiss();
            }
        });
    }
}
