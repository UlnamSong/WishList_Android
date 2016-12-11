package com.ulnamsong.wishlist.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ulnamsong.wishlist.DataBase.DBOpenHelper;
import com.ulnamsong.wishlist.DataBase.WishData;
import com.ulnamsong.wishlist.Module.ListAdapter;
import com.ulnamsong.wishlist.Module.RecyclerItemClickListener;
import com.ulnamsong.wishlist.Module.TypefaceUtil;
import com.ulnamsong.wishlist.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class MainWishActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WishData tempData;
    private TextView tvbottom1;
    private TextView tvbottom2;
    private TextView tvbottom3;
    private TextView tvbottom4;
    private LinearLayoutManager mLinearLayoutManager;

    private DBOpenHelper mDbOpenHelper;
    private Cursor mCursor;
    private ActionBar actionBar;
    private int currentCount = 0;
    private String TAG = "MainWishActivity";
    ListAdapter adapter;
    Cursor cursor;

    private ArrayList<WishData> wishList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceUtil.loadTypeface(this);
        setContentView(R.layout.activity_main_wish);

        // Start of ActionBar Setting

        actionBar = getSupportActionBar();

        TextView TextViewNewFont = new TextView(MainWishActivity.this);
        FrameLayout.LayoutParams layoutparams = new FrameLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        TextViewNewFont.setLayoutParams(layoutparams);
        TextViewNewFont.setText("위시 리스트");

        // TextView Color
        TextViewNewFont.setTextColor(getResources().getColor(R.color.actionbar_text_color));

        // TextView Gravity : 일단 비활성화 (Center Alignment가 안됨)
        //TextViewNewFont.setGravity(Gravity.CENTER_HORIZONTAL);
        TextViewNewFont.setTextSize(18);

        // Load Typeface font url String ExternalFontPath
        Typeface FontLoaderTypeface = Typeface.createFromAsset(getAssets(), "fonts/NanumSquareOTFBold.otf");
        TextViewNewFont.setTypeface(FontLoaderTypeface);

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(TextViewNewFont);

        // End of ActionBar Setting

        // Floating Action Bar Setting
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainWishActivity.this, "FAB Clicked.", Toast.LENGTH_SHORT).show();

                final AddWishDialog dialog = new AddWishDialog(MainWishActivity.this);

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dia) {

                    }
                });

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dia) {
                        if(dialog.isAddProcess) {
                            // Add into DB
                            Log.d(TAG, "onDismiss: Add Process finished successfully.");
                            mDbOpenHelper.insertColumn(dialog.etTitle.getText().toString(), dialog.categoryIndex, 0,
                                    Integer.parseInt(dialog.etMax.getText().toString()), dialog.importancy, getTodayDate(), getTodayDate());

                            updateRecyclerView();
                        } else {
                            // Do Nothing
                        }
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
            }
        });

        mDbOpenHelper = new DBOpenHelper(this);
        mDbOpenHelper.open();

        // End Initialize
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        tvbottom1 = (TextView) findViewById(R.id.tv_bot1);
        tvbottom2 = (TextView) findViewById(R.id.tv_bot2);
        tvbottom3 = (TextView) findViewById(R.id.tv_bot3);
        tvbottom4 = (TextView) findViewById(R.id.tv_bot4);

        tvbottom1.setTypeface(TypefaceUtil.typeface);
        tvbottom2.setTypeface(TypefaceUtil.typeface);
        tvbottom3.setTypeface(TypefaceUtil.typeface);
        tvbottom4.setTypeface(TypefaceUtil.typeface);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        adapter = new ListAdapter(this, wishList);
        Log.d("TAG", Thread.currentThread().getStackTrace()[2].getMethodName() + wishList.size());

        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Log.d(TAG, "onItemClick: clicked : " + position);
                        //Toast.makeText(MainWishActivity.this, wishList.get(position).wishTitle + ", " + position, Toast.LENGTH_SHORT).show();

                        //cursor = mDbOpenHelper.getMatchTitle(wishList.get(position).wishTitle);
                        Log.d(TAG, "onItemClick: wishlist_id : " + wishList.get(position)._id);
                        cursor = mDbOpenHelper.getColumn(wishList.get(position)._id);

                        final WishInfoDialog dialog = new WishInfoDialog(MainWishActivity.this);

                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dia) {
                                String[] strs_category = getResources().getStringArray(R.array.category_list);
                                String[] strs_importancy = getResources().getStringArray(R.array.importancy_list);

                                Log.e(TAG, "COUNT = " + cursor.getCount());

                                //while (cursor.moveToNext()) {
                                Log.d(TAG, "onShow: Cursor : " + cursor.getInt(cursor.getColumnIndex("_id")) + "\n"
                                        + cursor.getString(cursor.getColumnIndex("title")) + "\n"
                                        + cursor.getInt(cursor.getColumnIndex("category_index")) + "\n"
                                        + cursor.getInt(cursor.getColumnIndex("cur_value")) + "\n"
                                        + cursor.getInt(cursor.getColumnIndex("max_value")) + "\n"
                                        + cursor.getInt(cursor.getColumnIndex("importancy_value")) + "\n"
                                        + cursor.getString(cursor.getColumnIndex("start_date")) + "\n"
                                        + cursor.getString(cursor.getColumnIndex("recent_date")) + "\n");

                                dialog.id = cursor.getInt(cursor.getColumnIndex("_id"));

                                dialog.title = cursor.getString(cursor.getColumnIndex("title"));
                                dialog.tvTitleContent.setText(cursor.getString(cursor.getColumnIndex("title")));

                                dialog.categoryIndex = cursor.getInt(cursor.getColumnIndex("category_index"));
                                dialog.tvCategoryContent.setText(strs_category[cursor.getInt(cursor.getColumnIndex("category_index"))]);

                                dialog.importancy = cursor.getInt(cursor.getColumnIndex("importancy_value"));
                                dialog.startDate = cursor.getString(cursor.getColumnIndex("start_date"));

                                ArrayList<String> s_dateData = parseDateData(cursor.getString(cursor.getColumnIndex("start_date")));
                                ArrayList<String> e_dateData = parseDateData(cursor.getString(cursor.getColumnIndex("recent_date")));

                                if(dialog.importancy == 3) {
                                    dialog.doButton.setEnabled(false);
                                    dialog.doButton.setVisibility(View.INVISIBLE);

                                    dialog.tvImportancy.setText("생성 날짜");
                                    //dialog.tvImportancyContent.setText(cursor.getString(cursor.getColumnIndex("start_date")));
                                    dialog.tvImportancyContent.setText(s_dateData.get(0) + "년 " + s_dateData.get(1) + "월 " + s_dateData.get(2) + "일");

                                    dialog.tvMax.setText("완료 날짜");
                                    dialog.tvMaxContent.setText(e_dateData.get(0) + "년 " + e_dateData.get(1) + "월 " + e_dateData.get(2) + "일");
                                } else {
                                    dialog.doButton.setEnabled(true);
                                    dialog.doButton.setVisibility(View.VISIBLE);
                                    dialog.tvImportancyContent.setText(strs_importancy[cursor.getInt(cursor.getColumnIndex("importancy_value"))]);
                                    dialog.curValue = cursor.getInt(cursor.getColumnIndex("cur_value"));
                                    dialog.maxValue = cursor.getInt(cursor.getColumnIndex("max_value"));

                                    dialog.tvMaxContent.setText(cursor.getString(cursor.getColumnIndex("cur_value"))
                                            + " / "
                                            + cursor.getString(cursor.getColumnIndex("max_value")) + "회");
                                }

                                cursor.close();
                            }
                        });

                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dia) {
                                Log.d(TAG, "onDismiss: exitStatus : " + dialog.exitStatus);

                                switch(dialog.exitStatus) {
                                    default:
                                    case 0:
                                        // Do Nothing
                                        break;
                                    case 1:
                                        // Do Once
                                        dialog.curValue++;

                                        Log.d(TAG, "onDismiss: id : " + dialog.id);
                                        Log.d(TAG, "onDismiss: curValue : " + dialog.curValue);
                                        boolean result = mDbOpenHelper.updateColumn(dialog.id, dialog.title,
                                                dialog.categoryIndex, dialog.curValue, dialog.maxValue,
                                                dialog.importancy, dialog.startDate, getTodayDate());

                                        // 완료할 경우 값 3으로 변경
                                        if(dialog.curValue == dialog.maxValue) {
                                            result = mDbOpenHelper.updateColumn(dialog.id, dialog.title,
                                                    dialog.categoryIndex, dialog.curValue, dialog.maxValue,
                                                    3, dialog.startDate, getTodayDate());
                                        }

                                        if(result) {
                                            //Toast.makeText(MainWishActivity.this, "수행하였습니다.", Toast.LENGTH_SHORT).show();
                                            updateRecyclerView();
                                        } else {
                                            Log.d(TAG, "onDismiss: ERROR");
                                        }
                                        break;
                                    case 2:
                                        // Delete

                                        final TwoButtonDialog t_dialog = new TwoButtonDialog(MainWishActivity.this);
                                        t_dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                            @Override
                                            public void onShow(DialogInterface dialogInterface) {
                                                t_dialog.tvDialogTitle.setText("항목 삭제");
                                                t_dialog.tvDialogContent.setText("정말 삭제하시겠습니까?");
                                                t_dialog.positiveButton.setText("예");
                                                t_dialog.negativeButton.setText("아니오");
                                            }
                                        });
                                        t_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                            @Override
                                            public void onDismiss(DialogInterface dialogInterface) {
                                                switch(t_dialog.exitStatus) {
                                                    case 1:
                                                        mDbOpenHelper.deleteColumn(dialog.id);
                                                        updateRecyclerView();

                                                        final OneButtonDialog o_dialog = new OneButtonDialog(MainWishActivity.this);
                                                        o_dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                                            @Override
                                                            public void onShow(DialogInterface dialogInterface) {
                                                                o_dialog.tvDialogTitle.setText("삭제 완료");
                                                                o_dialog.tvDialogContent.setText("삭제되었습니다.");
                                                                o_dialog.positiveButton.setText("확인");
                                                            }
                                                        });
                                                        o_dialog.setOnDismissListener(null);
                                                        o_dialog.setCancelable(false);
                                                        o_dialog.show();

                                                        break;
                                                    case 0:
                                                    default:
                                                        // Do Nothing
                                                        break;
                                                }
                                            }
                                        });
                                        t_dialog.setCancelable(false);
                                        t_dialog.show();
//
//
//
//
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainWishActivity.this);
//                                        builder.setTitle("항목 삭제");
//                                        builder.setMessage("정말 삭제하시겠습니까?");
//                                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                mDbOpenHelper.deleteColumn(dialog.id);
//                                                updateRecyclerView();
//
//                                                AlertDialog.Builder builder = new AlertDialog.Builder(MainWishActivity.this);
//                                                builder.setTitle("삭제 완료");
//                                                builder.setMessage("삭제되었습니다.");
//                                                builder.setPositiveButton("확인", null);
//                                                builder.setCancelable(false);
//                                                builder.show();
//                                            }
//                                        });
//                                        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                // Do Nothing
//                                            }
//                                        });
//                                        builder.setCancelable(false);
//                                        builder.show();
                                        break;
                                }
                            }
                        });
                        dialog.setCancelable(false);
                        dialog.show();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        adapter.notifyDataSetChanged();
    }

    private ArrayList<String> parseDateData(String date) {
        StringTokenizer st = new StringTokenizer(date, "/");
        ArrayList<String> result_list = new ArrayList<>();

        while(st.hasMoreTokens()) {
            String token = st.nextToken();
            Log.d(TAG, "parseDateData: " + token);
            result_list.add(token);
        }

        return result_list;
    }

    private String getTodayDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        //getTime() returns the current date in default time zone
        Date date = calendar.getTime();
        int day = calendar.get(Calendar.DATE);
        //Note: +1 the month for current month
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

        return year + "/" + month + "/" + day;
    }

    private void doWhileCursorToArray(){

        mCursor = null;
        mCursor = mDbOpenHelper.getAllColumns();

        Log.e(TAG, "COUNT = " + mCursor.getCount());

        wishList.clear();

        while (mCursor.moveToNext()) {

            tempData = new WishData (
                    mCursor.getInt(mCursor.getColumnIndex("_id")),
                    mCursor.getString(mCursor.getColumnIndex("title")),
                    mCursor.getInt(mCursor.getColumnIndex("category_index")),
                    mCursor.getInt(mCursor.getColumnIndex("cur_value")),
                    mCursor.getInt(mCursor.getColumnIndex("max_value")),
                    mCursor.getInt(mCursor.getColumnIndex("importancy_value"))
            );

            Log.d(TAG, "doWhileCursorToArray: tempData id : " + tempData._id);
            wishList.add(tempData);
        }

        mCursor.close();
    }

    private void updateRecyclerView() {
        wishList.clear();

        //generateDummyData();
        doWhileCursorToArray();

        adapter = new ListAdapter(this, wishList);
        currentCount = wishList.size();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        // Delete All Columns in DB
        //mDbOpenHelper.deleteAllColumn();

        updateRecyclerView();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        final TwoButtonDialog twoButtonDialog = new TwoButtonDialog(MainWishActivity.this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_wish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.action_deleteall:
                final TwoButtonDialog t_dialog = new TwoButtonDialog(MainWishActivity.this);
                t_dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        t_dialog.tvDialogTitle.setText("모든 항목 삭제");
                        t_dialog.tvDialogContent.setText("정말 모든 항목을 삭제하시겠습니까?");
                        t_dialog.positiveButton.setText("예");
                        t_dialog.negativeButton.setText("아니오");
                    }
                });
                t_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        switch(t_dialog.exitStatus) {
                            case 1:
                                if(currentCount < 1) {
                                    final OneButtonDialog o_dialog = new OneButtonDialog(MainWishActivity.this);
                                    o_dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                        @Override
                                        public void onShow(DialogInterface dialogInterface) {
                                            o_dialog.tvDialogTitle.setText("항목 없음");
                                            o_dialog.tvDialogContent.setText("삭제할 항목이 없습니다.");
                                            o_dialog.positiveButton.setText("확인");
                                        }
                                    });
                                    o_dialog.setOnDismissListener(null);
                                    o_dialog.setCancelable(false);
                                    o_dialog.show();
                                } else {
                                    mDbOpenHelper.deleteAllColumn();
                                    updateRecyclerView();

                                    final OneButtonDialog o_dialog = new OneButtonDialog(MainWishActivity.this);
                                    o_dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                        @Override
                                        public void onShow(DialogInterface dialogInterface) {
                                            o_dialog.tvDialogTitle.setText("삭제 완료");
                                            o_dialog.tvDialogContent.setText("모든 항목이 삭제되었습니다.");
                                            o_dialog.positiveButton.setText("확인");
                                        }
                                    });
                                    o_dialog.setOnDismissListener(null);
                                    o_dialog.setCancelable(false);
                                    o_dialog.show();
                                }
                                break;
                            case 0:
                                // Do Nothing
                                break;
                        }
                    }
                });
                t_dialog.setCancelable(false);
                t_dialog.show();
                return true;

            case R.id.action_statistics:
                startActivity(new Intent(MainWishActivity.this, StatisticsActivity.class));
                overridePendingTransition(R.anim.fade, R.anim.hold);
                return true;

            case R.id.action_credit:
                final CreditDialog credit_dialog = new CreditDialog(MainWishActivity.this);

                credit_dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dia) {

                    }
                });

                credit_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dia) {

                    }
                });
                credit_dialog.setCancelable(false);
                credit_dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
