package com.ulnamsong.wishlist.Activity;

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

import com.ulnamsong.wishlist.DataBase.UserData;
import com.ulnamsong.wishlist.Module.TypefaceUtil;
import com.ulnamsong.wishlist.R;

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
                    builder.setTitle("이름 입력");
                    builder.setMessage("당신의 소중한 이름을 입력해주세요.");
                    builder.setPositiveButton("확인", null);
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
                            oneButtonDialog.tvDialogTitle.setText("환영합니다.");
                            oneButtonDialog.tvDialogContent.setText("꿈을 이루시길 기원합니다!");
                            oneButtonDialog.positiveButton.setText("확인");
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
                twoButtonDialog.tvDialogTitle.setText("종료 확인");
                twoButtonDialog.tvDialogContent.setText("어플리케이션을 종료합니다.");
                twoButtonDialog.positiveButton.setText("확인");
                twoButtonDialog.negativeButton.setText("취소");
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
