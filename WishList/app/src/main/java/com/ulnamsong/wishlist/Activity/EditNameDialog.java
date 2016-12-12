package com.ulnamsong.wishlist.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ulnamsong.wishlist.Module.TypefaceUtil;
import com.ulnamsong.wishlist.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Ulnamsong on 2016. 12. 13..
 */

public class EditNameDialog extends Dialog {

    private Context context;
    TextView tvDialogTitle = null;
    TextView tvDialogContent = null;
    EditText etDialogInput = null;

    Button positiveButton = null;
    Button negativeButton = null;

    int inputType = 0; // 0 : Integer, 1 : String
    int exitStatus = 0; // 0 : Negative, 1 : Positive
    String newText = "";
    int newCount = 0;


    int curCount = 0;

    // Initial Data
    int id = 0;
    String title = "";
    int categoryIndex = 0;
    int importancy = 0;
    int maxValue = 0;
    int curValue = 0;
    String startDate = "";

    public EditNameDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TypefaceUtil.loadTypeface(context);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_name_dialog);

        tvDialogTitle = (TextView) findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setTypeface(TypefaceUtil.typeface_m);

        tvDialogContent = (TextView) findViewById(R.id.tvDialogContent);
        tvDialogContent.setTypeface(TypefaceUtil.typeface);

        etDialogInput = (EditText) findViewById(R.id.etContent);
        etDialogInput.setTypeface(TypefaceUtil.typeface);

        positiveButton = (Button) findViewById(R.id.positive_button);
        positiveButton.setTypeface(TypefaceUtil.typeface_m);

        negativeButton = (Button) findViewById(R.id.negative_button);
        negativeButton.setTypeface(TypefaceUtil.typeface_m);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitStatus = 1;
                boolean isValid = false;

                if(etDialogInput.getText().toString().equals("")) {
                    final OneButtonDialog dialog = new OneButtonDialog(context);
                    dialog.setOnShowListener(new OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            dialog.tvDialogTitle.setText(context.getString(R.string.additem_inputerror));
                            dialog.tvDialogContent.setText(context.getString(R.string.additem_inputerror_content));
                        }
                    });
                    dialog.setOnDismissListener(null);
                    dialog.setCancelable(false);
                    dialog.show();
                } else {
                    if (inputType > 0) {
                        // Content Title
                        newText = etDialogInput.getText().toString();
                        title = newText;

                        isValid = true;
                    } else {
                        // Repeat Count
                        newCount = Integer.parseInt(etDialogInput.getText().toString());
                        Log.d(TAG, "onClick: curValue : " + curValue + ", newCount : " + newCount);
                        if(curValue > newCount || newCount < 1) {
                            final OneButtonDialog dialog = new OneButtonDialog(context);
                            dialog.setOnShowListener(new OnShowListener() {
                                @Override
                                public void onShow(DialogInterface dialogInterface) {
                                    dialog.tvDialogTitle.setText(context.getString(R.string.additem_inputerror));
                                    dialog.tvDialogContent.setText(context.getString(R.string.edit_repeat_error_content));
                                }
                            });
                            dialog.setOnDismissListener(null);
                            dialog.setCancelable(false);
                            dialog.show();
                        } else {
                            maxValue = newCount;

                            isValid = true;
                        }
                    }
                }

                if(isValid) {
                    Log.d(TAG, "onClick: isValue : true");
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
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitStatus = 0;
                newText = title;
                newCount = maxValue;
                dismiss();
            }
        });
    }

}
