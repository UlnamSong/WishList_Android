package com.ulnamsong.wishlist.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.ulnamsong.wishlist.Module.MySpinnerAdapter;
import com.ulnamsong.wishlist.Module.TypefaceUtil;
import com.ulnamsong.wishlist.R;

/**
 * Created by Ulnamsong on 2016. 12. 13..
 */

public class EditSpinnerDialog extends Dialog {

    private Context context;
    TextView tvDialogTitle = null;
    TextView tvDialogContent = null;
    Spinner spinnerContent = null;

    Button positiveButton = null;
    Button negativeButton = null;

    // Initial Data
    int id = 0;
    String title = "";
    int categoryIndex = 0;
    int importancy = 0;
    int maxValue = 0;
    int curValue = 0;
    String startDate = "";

    MySpinnerAdapter contentAdapter = null;

    int selectedIndex = 0;
    int exitStatus = 0; // 0 : No Error, 1 : Error

    public EditSpinnerDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TypefaceUtil.loadTypeface(context);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_spinner_dialog);

        tvDialogTitle = (TextView) findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setTypeface(TypefaceUtil.typeface_m);

        tvDialogContent = (TextView) findViewById(R.id.tvDialogContent);
        tvDialogContent.setTypeface(TypefaceUtil.typeface);

        spinnerContent = (Spinner) findViewById(R.id.spinner_content);

        positiveButton = (Button) findViewById(R.id.positive_button);
        positiveButton.setTypeface(TypefaceUtil.typeface_m);

        negativeButton = (Button) findViewById(R.id.negative_button);
        negativeButton.setTypeface(TypefaceUtil.typeface_m);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitStatus = 0;

                final OneButtonDialog oneButtonDialog = new OneButtonDialog(context);
                oneButtonDialog.setOnShowListener(new OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        oneButtonDialog.tvDialogTitle.setText(context.getString(R.string.edit_complete_title));
                        oneButtonDialog.tvDialogContent.setText(context.getString(R.string.edit_complete_content));
                        oneButtonDialog.positiveButton.setText(context.getString(R.string.one_dialog_ok));
                    }
                });
                oneButtonDialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        exitStatus = 0;
                        dismiss();
                    }
                });
                oneButtonDialog.setCancelable(false);
                oneButtonDialog.show();
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitStatus = 1;
                dismiss();
            }
        });
    }
}
