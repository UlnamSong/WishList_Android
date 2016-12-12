package com.ulnamsong.wishlist.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.ulnamsong.wishlist.DataBase.DBOpenHelper;
import com.ulnamsong.wishlist.Module.MySpinnerAdapter;
import com.ulnamsong.wishlist.Module.TypefaceUtil;
import com.ulnamsong.wishlist.R;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static android.content.ContentValues.TAG;

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

    Button modCategoryButton = null;
    Button modContentButton = null;
    Button modImportancyButton = null;
    Button modRepeatButton = null;
    TextView backButton = null;

    String title;
    String startDate;

    int id;
    int categoryIndex;
    int importancy;
    int curValue;
    int maxValue;
    int modType = 0; // 0 : Category, 1 : Importance

    int exitStatus = 0; // 0 : Nothing, 1 : Do Once, 2 : Delete

    private DBOpenHelper mDbOpenHelper;

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

        modContentButton = (Button) findViewById(R.id.mod_content_button);
        modCategoryButton = (Button) findViewById(R.id.mod_category_button);

        modContentButton.setTypeface(TypefaceUtil.typeface_m);
        modCategoryButton.setTypeface(TypefaceUtil.typeface_m);

        modImportancyButton = (Button) findViewById(R.id.mod_importancy_button);
        modImportancyButton.setTypeface(TypefaceUtil.typeface_m);

        modRepeatButton = (Button) findViewById(R.id.mod_repeat_button);
        modRepeatButton.setTypeface(TypefaceUtil.typeface_m);

        backButton = (TextView) findViewById(R.id.backButton);
        backButton.setTypeface(TypefaceUtil.typeface_m);

        doButton.setTypeface(TypefaceUtil.typeface_m);
        deleteButton.setTypeface(TypefaceUtil.typeface_m);

        mDbOpenHelper = new DBOpenHelper(getContext());
        mDbOpenHelper.open();

        tvTitle.setTypeface(TypefaceUtil.typeface_m);
        tvTitleContent.setTypeface(TypefaceUtil.typeface);

        tvCategory.setTypeface(TypefaceUtil.typeface_m);
        tvCategoryContent.setTypeface(TypefaceUtil.typeface);

        tvImportancy.setTypeface(TypefaceUtil.typeface_m);
        tvImportancyContent.setTypeface(TypefaceUtil.typeface);

        tvMax.setTypeface(TypefaceUtil.typeface_m);
        tvMaxContent.setTypeface(TypefaceUtil.typeface);

        // Automatically Open Keyboard.
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        modContentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditNameDialog dialog = new EditNameDialog(context);
                dialog.setOnShowListener(new OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialog.id = id;
                        dialog.title = title;
                        dialog.importancy = importancy;
                        dialog.categoryIndex = categoryIndex;
                        dialog.curValue = curValue;
                        dialog.maxValue = maxValue;

                        dialog.inputType = 1;
                        dialog.etDialogInput.setInputType(InputType.TYPE_CLASS_TEXT);
                        dialog.tvDialogTitle.setText(context.getString(R.string.edit_content_dialog_title));
                        dialog.tvDialogContent.setText(context.getString(R.string.edit_content_dialog_content));
                        dialog.etDialogInput.setHint(context.getString(R.string.edit_content_dialog_content));
                    }
                });
                dialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if(dialog.exitStatus == 0) {
                            Log.d(TAG, "onDismiss: changed title : " + dialog.title);
                            tvTitleContent.setText(dialog.title);
                            title = dialog.title;
                            exitStatus = 3;
                        }
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
            }
        });

        modCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modType = 0;
                final EditSpinnerDialog dialog = new EditSpinnerDialog(context);
                dialog.setOnShowListener(new OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialog.id = id;
                        dialog.title = title;
                        dialog.importancy = importancy;
                        dialog.categoryIndex = categoryIndex;
                        dialog.curValue = curValue;
                        dialog.maxValue = maxValue;

                        dialog.tvDialogTitle.setText(context.getString(R.string.edit_category_dialog_title));
                        dialog.tvDialogContent.setText(context.getString(R.string.edit_category_dialog_content));
                        dialog.contentAdapter = new MySpinnerAdapter(context,
                                R.layout.support_simple_spinner_dropdown_item,
                                Arrays.asList(context.getResources().getStringArray(R.array.category_list))
                        );
                        dialog.spinnerContent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                dialog.selectedIndex = i;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        dialog.spinnerContent.setAdapter(dialog.contentAdapter);
                    }
                });
                dialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        String[] strs_category = context.getResources().getStringArray(R.array.category_list);

                        Log.d(TAG, "onDismiss: dismissStart!!");
                        switch(dialog.exitStatus) {
                            case 0:
                                if(modType == 0) {
                                    Log.d(TAG, "onDismiss:" + "modType0");

                                    // Category
                                    categoryIndex = dialog.selectedIndex;

                                    tvCategoryContent.setText(strs_category[dialog.selectedIndex]);
                                    exitStatus = 3;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
            }
        });

        modImportancyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modType = 1;
                final EditSpinnerDialog dialog = new EditSpinnerDialog(context);
                dialog.setOnShowListener(new OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialog.id = id;
                        dialog.title = title;
                        dialog.importancy = importancy;
                        dialog.categoryIndex = categoryIndex;
                        dialog.curValue = curValue;
                        dialog.maxValue = maxValue;

                        dialog.tvDialogTitle.setText(context.getString(R.string.edit_category_dialog_title));
                        dialog.tvDialogContent.setText(context.getString(R.string.edit_category_dialog_content));
                        dialog.contentAdapter = new MySpinnerAdapter(context,
                                R.layout.support_simple_spinner_dropdown_item,
                                Arrays.asList(context.getResources().getStringArray(R.array.importancy_list))
                        );
                        dialog.spinnerContent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                dialog.selectedIndex = i;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) { }
                        });
                        dialog.spinnerContent.setAdapter(dialog.contentAdapter);
                    }
                });
                dialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        String[] strs_importancy = context.getResources().getStringArray(R.array.importancy_list);

                        switch(dialog.exitStatus) {
                            case 0:
                                if(modType == 1) {
                                    Log.d(TAG, "onDismiss:" + "modType1");
                                    // Importance
                                    importancy = dialog.selectedIndex;

                                    tvImportancyContent.setText(strs_importancy[dialog.selectedIndex]);
                                    exitStatus = 3;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
            }
        });

        modRepeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: repeat edit clicked");
                final EditNameDialog dialog = new EditNameDialog(context);
                dialog.setOnShowListener(new OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialog.id = id;
                        dialog.title = title;
                        dialog.importancy = importancy;
                        dialog.categoryIndex = categoryIndex;
                        dialog.curValue = curValue;
                        dialog.maxValue = maxValue;

                        dialog.inputType = 0;

                        dialog.etDialogInput.setInputType(InputType.TYPE_CLASS_NUMBER);

                        dialog.tvDialogTitle.setText(context.getString(R.string.edit_repeat_dialog_title));
                        dialog.tvDialogContent.setText(context.getString(R.string.edit_repeat_dialog_content));
                        dialog.etDialogInput.setHint(context.getString(R.string.edit_repeat_dialog_content));
                    }
                });
                dialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if(dialog.exitStatus == 0) {
                            Log.d(TAG, "onDismiss: changed title : " + dialog.title);
                            maxValue = dialog.newCount;
                            tvMaxContent.setText(curValue + " / " + dialog.newCount + context.getString(R.string.iteminfo_dialog_progress_tag));
                            exitStatus = 3;
                        }
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        exitStatus = 0;
        super.onBackPressed();
    }
}
