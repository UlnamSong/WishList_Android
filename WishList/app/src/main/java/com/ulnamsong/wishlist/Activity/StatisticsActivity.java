package com.ulnamsong.wishlist.Activity;

import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ulnamsong.wishlist.DataBase.DBOpenHelper;
import com.ulnamsong.wishlist.Module.TypefaceUtil;
import com.ulnamsong.wishlist.R;

import at.grabner.circleprogress.CircleProgressView;

public class StatisticsActivity extends AppCompatActivity {

    private ActionBar actionBar = null;
    private String TAG = "StatisticsActivity";
    private DBOpenHelper mDBOpenHelper;

    private TextView mainTitle = null;
    private TextView tvGraphValue = null;

    private TextView tvCategory1Title = null;
    private TextView tvCategory1Content = null;

    private TextView tvCategory2Title = null;
    private TextView tvCategory2Content = null;

    private TextView tvCategory3Title = null;
    private TextView tvCategory3Content = null;

    private TextView tvCategory4Title = null;
    private TextView tvCategory4Content = null;

    private TextView tvCategory5Title = null;
    private TextView tvCategory5Content = null;

    private TextView tvCategory6Title = null;
    private TextView tvCategory6Content = null;

    private TextView tvCategory7Title = null;
    private TextView tvCategory7Content = null;

    private TextView tvCategory8Title = null;
    private TextView tvCategory8Content = null;

    private TextView tvCategoryTitle = null;

    private CircleProgressView circleProgressView;

    Cursor cursor;

    private int totalColumnCount = 0;
    private int[] importancy_counts = new int[4];
    private int[] category_counts = new int[8];

    private int[] category_curcounts = new int[8];
    private int[] category_maxcounts = new int[8];

    private int[] category_percent = new int[8];

    private int currentSum = 0;
    private int maxSum = 0;
    private int mainPercent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceUtil.loadTypeface(this);
        setContentView(R.layout.activity_statistics);

        actionBar = getSupportActionBar();

        TextView TextViewNewFont = new TextView(StatisticsActivity.this);
        FrameLayout.LayoutParams layoutparams = new FrameLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        TextViewNewFont.setLayoutParams(layoutparams);
        TextViewNewFont.setText("리스트 통계");

        // TextView Color
        TextViewNewFont.setTextColor(getResources().getColor(R.color.actionbar_text_color));
        TextViewNewFont.setTextSize(18);

        // Load Typeface font url String ExternalFontPath
        Typeface FontLoaderTypeface = Typeface.createFromAsset(getAssets(), "fonts/NanumSquareOTFBold.otf");
        TextViewNewFont.setTypeface(FontLoaderTypeface);

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(TextViewNewFont);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDBOpenHelper = new DBOpenHelper(this);
        mDBOpenHelper.open();

        setContent();
        loadFunction();
    }

    private void loadFunction() {
        getDataFromDB();

        circleProgressView.setMaxValue(100);
        circleProgressView.setValue(mainPercent);

        tvGraphValue.setText(mainPercent + "%");

        tvCategory1Content.setText(category_percent[0] + "%");
        tvCategory2Content.setText(category_percent[1] + "%");
        tvCategory3Content.setText(category_percent[2] + "%");
        tvCategory4Content.setText(category_percent[3] + "%");
        tvCategory5Content.setText(category_percent[4] + "%");
        tvCategory6Content.setText(category_percent[5] + "%");
        tvCategory7Content.setText(category_percent[6] + "%");
        tvCategory8Content.setText(category_percent[7] + "%");
    }

    private void setContent() {
        circleProgressView = (CircleProgressView) findViewById(R.id.main_graph);

        mainTitle = (TextView) findViewById(R.id.tvTitle);
        tvGraphValue = (TextView) findViewById(R.id.main_graph_val);

        mainTitle.setTypeface(TypefaceUtil.typeface_m);
        tvGraphValue.setTypeface(TypefaceUtil.typeface_m);

        tvCategoryTitle = (TextView) findViewById(R.id.tvCategoryTitle);
        tvCategoryTitle.setTypeface(TypefaceUtil.typeface_m);

        tvCategory1Title = (TextView) findViewById(R.id.tv_category1_title);
        tvCategory1Content = (TextView) findViewById(R.id.tv_category1_val);

        tvCategory2Title = (TextView) findViewById(R.id.tv_category2_title);
        tvCategory2Content = (TextView) findViewById(R.id.tv_category2_val);

        tvCategory3Title = (TextView) findViewById(R.id.tv_category3_title);
        tvCategory3Content = (TextView) findViewById(R.id.tv_category3_val);

        tvCategory4Title = (TextView) findViewById(R.id.tv_category4_title);
        tvCategory4Content = (TextView) findViewById(R.id.tv_category4_val);

        tvCategory5Title = (TextView) findViewById(R.id.tv_category5_title);
        tvCategory5Content = (TextView) findViewById(R.id.tv_category5_val);

        tvCategory6Title = (TextView) findViewById(R.id.tv_category6_title);
        tvCategory6Content = (TextView) findViewById(R.id.tv_category6_val);

        tvCategory7Title = (TextView) findViewById(R.id.tv_category7_title);
        tvCategory7Content = (TextView) findViewById(R.id.tv_category7_val);

        tvCategory8Title = (TextView) findViewById(R.id.tv_category8_title);
        tvCategory8Content = (TextView) findViewById(R.id.tv_category8_val);

        tvCategory1Title.setTypeface(TypefaceUtil.typeface);
        tvCategory2Title.setTypeface(TypefaceUtil.typeface);
        tvCategory3Title.setTypeface(TypefaceUtil.typeface);
        tvCategory4Title.setTypeface(TypefaceUtil.typeface);
        tvCategory5Title.setTypeface(TypefaceUtil.typeface);
        tvCategory6Title.setTypeface(TypefaceUtil.typeface);
        tvCategory7Title.setTypeface(TypefaceUtil.typeface);
        tvCategory8Title.setTypeface(TypefaceUtil.typeface);

        tvCategory1Content.setTypeface(TypefaceUtil.typeface_m);
        tvCategory2Content.setTypeface(TypefaceUtil.typeface_m);
        tvCategory3Content.setTypeface(TypefaceUtil.typeface_m);
        tvCategory4Content.setTypeface(TypefaceUtil.typeface_m);
        tvCategory5Content.setTypeface(TypefaceUtil.typeface_m);
        tvCategory6Content.setTypeface(TypefaceUtil.typeface_m);
        tvCategory7Content.setTypeface(TypefaceUtil.typeface_m);
        tvCategory8Content.setTypeface(TypefaceUtil.typeface_m);
    }

    private void getDataFromDB () {
        int temp_cur;
        int temp_max;

        cursor = null;

        for(int i = 0; i < 4; ++i) {
            importancy_counts[i] = 0;
        }

        for(int i = 0; i < 8; ++i) {
            category_counts[i] = 0;
        }

        cursor = mDBOpenHelper.getAllColumns();
        totalColumnCount = cursor.getCount();

        if(totalColumnCount > 0) {

            currentSum = 0;
            maxSum = 0;

            // Get Data from DB
            while (cursor.moveToNext()) {
                importancy_counts[cursor.getInt(cursor.getColumnIndex("importancy_value"))]++;
                category_counts[cursor.getInt(cursor.getColumnIndex("category_index"))]++;

                currentSum += cursor.getInt(cursor.getColumnIndex("cur_value"));

                temp_cur = cursor.getInt(cursor.getColumnIndex("cur_value"));
                temp_max = cursor.getInt(cursor.getColumnIndex("max_value"));

                category_curcounts[cursor.getInt(cursor.getColumnIndex("category_index"))] = temp_cur;
                category_maxcounts[cursor.getInt(cursor.getColumnIndex("category_index"))] = temp_max;

                maxSum += cursor.getInt(cursor.getColumnIndex("max_value"));
                category_percent[cursor.getInt(cursor.getColumnIndex("category_index"))] = temp_cur * 100 / temp_max;
            }

            // Main Graph Percent Value Calculation
            mainPercent = currentSum * 100 / maxSum;
        }
    }

    @Override
    public void onResume() {
        getDataFromDB();
        circleProgressView.setMaxValue(100);
        circleProgressView.setValue(mainPercent);
        tvGraphValue.setText(mainPercent + "%");
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                overridePendingTransition(R.anim.fade, R.anim.hold);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
