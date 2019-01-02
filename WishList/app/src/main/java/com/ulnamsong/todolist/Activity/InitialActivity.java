package com.ulnamsong.todolist.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ulnamsong.todolist.DataBase.UserData;
import com.ulnamsong.todolist.Module.TypefaceUtil;
import com.ulnamsong.todolist.R;

public class InitialActivity extends AppCompatActivity {

    private ActionBar actionBar = null;
    private TextView  titleTop = null;
    private TextView  titleBottom = null;
    private TextView  nameInputText = null;
    private EditText  nameInput = null;
    private Button    okButton = null;

    private String sfName = "wishListFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceUtil.loadTypeface(this);
        setContentView(R.layout.activity_initial);

        actionBar = getSupportActionBar();
        actionBar.hide();

        setContent();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameInput.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(InitialActivity.this);
                    builder.setTitle(getString(R.string.name_input));
                    builder.setMessage(getString(R.string.initial_input_name));
                    builder.setPositiveButton(getString(R.string.one_dialog_ok), null);
                    builder.show();
                } else {
                    // 사용자 이름 설정
                    UserData.userName = nameInput.getText().toString();

                    SharedPreferences sf = getSharedPreferences(sfName, 0);
                    SharedPreferences.Editor editor = sf.edit();
                    editor.putBoolean("isInitial", false);
                    editor.putString("userName", nameInput.getText().toString());
                    editor.commit();


                    final OneButtonDialog oneButtonDialog = new OneButtonDialog(InitialActivity.this);
                    oneButtonDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            oneButtonDialog.tvDialogTitle.setText(getString(R.string.initial_welcome_title));
                            oneButtonDialog.tvDialogContent.setText(getString(R.string.initial_welcome_content));
                            oneButtonDialog.positiveButton.setText(getString(R.string.one_dialog_ok));
                        }
                    });

                    oneButtonDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if(oneButtonDialog.exitStatus == 1) {
                                startActivity(new Intent(InitialActivity.this, MainWishActivity.class));
                                overridePendingTransition(R.anim.fade, R.anim.hold);
                                finish();
                            }
                        }
                    });
                    oneButtonDialog.setCancelable(false);
                    oneButtonDialog.show();
                }
            }
        });
    }

    private void setContent() {
        titleTop = (TextView) findViewById(R.id.titleTop);
        titleBottom = (TextView) findViewById(R.id.titleBottom);
        nameInputText = (TextView) findViewById(R.id.nameInputText);
        nameInput = (EditText) findViewById(R.id.nameInput);
        okButton = (Button) findViewById(R.id.okButton);

        titleTop.setTypeface(TypefaceUtil.typeface);
        titleBottom.setTypeface(TypefaceUtil.typeface_b);

        nameInput.setTypeface(TypefaceUtil.typeface);
        nameInputText.setTypeface(TypefaceUtil.typeface);
        okButton.setTypeface(TypefaceUtil.typeface);
    }

    @Override
    public void onBackPressed() {
        final TwoButtonDialog twoButtonDialog = new TwoButtonDialog(InitialActivity.this);
        twoButtonDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                twoButtonDialog.tvDialogTitle.setText(getString(R.string.exit_dialog_title));
                twoButtonDialog.tvDialogContent.setText(getString(R.string.exit_dialog_content));
                twoButtonDialog.positiveButton.setText(getString(R.string.two_dialog_ok));
                twoButtonDialog.negativeButton.setText(getString(R.string.two_dialog_cancel));
            }
        });
        twoButtonDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                switch(twoButtonDialog.exitStatus) {
                    case 1:
                        finish();
                        break;
                    case 0:
                    default:
                        break;
                }
            }
        });
        twoButtonDialog.setCancelable(false);
        twoButtonDialog.show();
    }
}
