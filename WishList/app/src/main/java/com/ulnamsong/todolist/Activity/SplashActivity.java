package com.ulnamsong.todolist.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ulnamsong.todolist.DataBase.UserData;
import com.ulnamsong.todolist.R;

import org.jsoup.Jsoup;

public class SplashActivity extends AppCompatActivity {

    private ActionBar actionBar = null;
    private final Handler handler =  new Handler();
    private String sfName = "wishListFile";
    private String TAG = "SplashActivity : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        actionBar = getSupportActionBar();
        actionBar.hide();
        Log.d(TAG, "onDismiss: packagename : " + getPackageName());

        // After 2 Seconds, load new Activity
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sf = getSharedPreferences(sfName, 0);
                boolean isInitial = sf.getBoolean("isInitial", true);
                UserData.userName = sf.getString("userName", "NO_NAME");

                if(isInitial) {
                    // If Initial
                    Log.d(TAG, "run: Is Initial is True");
                    startActivity(new Intent(SplashActivity.this, InitialActivity.class));
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                    finish();
                } else {
                    // If Not Initial
                    Log.d(TAG, "run: Is Initial is False");
                    startActivity(new Intent(SplashActivity.this, MainWishActivity.class));
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                    finish();
                }
            }
        }, 2000);

    }

    private class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground");
            String newVersion = null;
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + SplashActivity.this.getPackageName() + "&hl=it")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();
                return newVersion;
            } catch (Exception e) {
                return newVersion;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            Log.d(TAG, "onPostExecute");
            String currentVersion = null;
            try {
                currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                if (onlineVersion != null && !onlineVersion.isEmpty()) {
                    if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {
                        Log.d(TAG, "onPostExecute: Need to be updated.");

                        final OneButtonDialog oneButtonDialog = new OneButtonDialog(SplashActivity.this);
                        oneButtonDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                oneButtonDialog.tvDialogTitle.setText("업데이트 필요");
                                oneButtonDialog.tvDialogContent.setText("최신 버전으로 업데이트 합니다.");
                                oneButtonDialog.positiveButton.setText("확인");
                            }
                        });
                        oneButtonDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                Log.d(TAG, "onDismiss: packagename : " + getPackageName());
                                intent.setData(Uri.parse("market://details?id=" + getPackageName()));
                                startActivity(intent);
                            }
                        });
                        oneButtonDialog.setCancelable(false);
                        oneButtonDialog.show();
                    } else {
                        Log.d(TAG, "onPostExecute: No need to be updated.");
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);
        }
    }
}
