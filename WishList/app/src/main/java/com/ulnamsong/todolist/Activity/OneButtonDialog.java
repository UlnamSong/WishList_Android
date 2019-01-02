package com.ulnamsong.todolist.Activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ulnamsong.todolist.Module.TypefaceUtil;
import com.ulnamsong.todolist.R;

/**
 * Created by Ulnamsong on 2016. 12. 10..
 */

public class OneButtonDialog extends Dialog {

    private Context context;
    TextView tvDialogTitle = null;
    TextView tvDialogContent = null;

    Button positiveButton = null;

    int exitStatus = 1;

    public OneButtonDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceUtil.loadTypeface(getContext());
        setContentView(R.layout.onebutton_dialog);

        tvDialogTitle = (TextView) findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setTypeface(TypefaceUtil.typeface_m);

        tvDialogContent = (TextView) findViewById(R.id.tvDialogContent);
        tvDialogContent.setTypeface(TypefaceUtil.typeface);

        positiveButton = (Button) findViewById(R.id.positive_button);
        positiveButton.setTypeface(TypefaceUtil.typeface_m);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitStatus = 1;
                dismiss();
            }
        });
    }

}
