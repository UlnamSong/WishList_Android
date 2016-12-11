package com.ulnamsong.wishlist.Activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ulnamsong.wishlist.DataBase.DBOpenHelper;
import com.ulnamsong.wishlist.Module.MySpinnerAdapter;
import com.ulnamsong.wishlist.Module.TypefaceUtil;
import com.ulnamsong.wishlist.R;

import java.util.Arrays;

/**
 * Created by Ulnamsong on 2016. 12. 9..
 */

public class AddWishDialog extends Dialog {

    TextView tvDialogTitle = null;
    TextView tvTitle = null;
    TextView tvCategory = null;
    TextView tvImportancy = null;
    TextView tvMax = null;
    EditText etTitle = null;
    EditText etMax = null;

    Spinner spinnerCategory = null;
    Spinner spinnerImportancy = null;

    Button addButton = null;
    Button backButton = null;

    boolean isAddProcess = false;

    int importancy = 0;
    int categoryIndex = 0;

    private Context context;
    private DBOpenHelper mDbOpenHelper;

    private String TAG = "AddWishDialog";

    public AddWishDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceUtil.loadTypeface(getContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addwish_dialog);

        tvDialogTitle = (TextView) findViewById(R.id.tvDialogTitle);
        tvTitle = (TextView) findViewById(R.id.tvDialogContent);
        tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvImportancy = (TextView) findViewById(R.id.tvImportancy);
        etTitle = (EditText) findViewById(R.id.etTitle);

        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        spinnerImportancy = (Spinner) findViewById(R.id.spinnerImportancy);

        addButton = (Button) findViewById(R.id.deleteButton);
        backButton = (Button) findViewById(R.id.backButton);

        tvMax = (TextView) findViewById(R.id.tvMax);
        etMax = (EditText) findViewById(R.id.etMax);

        tvDialogTitle.setTypeface(TypefaceUtil.typeface_m);
        tvTitle.setTypeface(TypefaceUtil.typeface_m);
        tvCategory.setTypeface(TypefaceUtil.typeface_m);
        tvImportancy.setTypeface(TypefaceUtil.typeface_m);
        etTitle.setTypeface(TypefaceUtil.typeface);

        addButton.setTypeface(TypefaceUtil.typeface_m);
        backButton.setTypeface(TypefaceUtil.typeface_m);

        tvMax.setTypeface(TypefaceUtil.typeface_m);
        etMax.setTypeface(TypefaceUtil.typeface);

        mDbOpenHelper = new DBOpenHelper(context);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryIndex = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerImportancy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                importancy = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        MySpinnerAdapter adapterCategory = new MySpinnerAdapter(
                context,
                R.layout.support_simple_spinner_dropdown_item,
                Arrays.asList(context.getResources().getStringArray(R.array.category_list))
        );

        MySpinnerAdapter adapterImportancy = new MySpinnerAdapter(
                context,
                R.layout.support_simple_spinner_dropdown_item,
                Arrays.asList(context.getResources().getStringArray(R.array.importancy_list))
        );

        spinnerCategory.setAdapter(adapterCategory);
        spinnerImportancy.setAdapter(adapterImportancy);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAddProcess = true;
                Log.d(TAG, "onClick: Data Added.");
                dismiss();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAddProcess = false;
                Log.d(TAG, "onClick: Dialog Dismiss.");
                dismiss();
            }
        });
    }
}
