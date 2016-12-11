package com.ulnamsong.wishlist.Module;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ulnamsong.wishlist.DataBase.WishData;
import com.ulnamsong.wishlist.R;

import java.util.ArrayList;

/**
 * Created by Ulnamsong on 2016. 12. 9..
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    Context context;
    ArrayList<WishData> wishList;

    public ListAdapter(Context context, ArrayList<WishData> wishList) {
        this.context = context;
        this.wishList = wishList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        WishData wishData = wishList.get(position);
        String[] categoryArray = context.getResources().getStringArray(R.array.category_list);

        int percent = (wishData.currentValue * 100) / wishData.maximumValue;

        holder.tv_title.setText(wishData.wishTitle); // Wish Title
        holder.tv_category.setText(categoryArray[wishData.wishCategoryIndex]); // Wish Category
        holder.tv_percent.setText(percent + "%");

        // 100% -> Finished
        if(percent == 100) {
            wishData.importancyValue = 3;
        }

        switch(wishData.importancyValue) {
            case 0:
                holder.importancyMark.setImageResource(R.drawable.importancy_high);
                holder.tv_percent.setTextColor(context.getResources().getColor(R.color.colorImportancyHigh));
                break;
            case 1:
                holder.importancyMark.setImageResource(R.drawable.importancy);
                holder.tv_percent.setTextColor(context.getResources().getColor(R.color.colorImportancyRegular));
                break;
            case 2:
                holder.importancyMark.setImageResource(R.drawable.importancy_low);
                holder.tv_percent.setTextColor(context.getResources().getColor(R.color.colorImportancyLow));
                break;
            case 3:
                holder.importancyMark.setImageResource(R.drawable.importancy_finished);
                holder.tv_percent.setTextColor(context.getResources().getColor(R.color.colorImportancyFinished));
                holder.tv_percent.setText("완료");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return this.wishList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_category;
        TextView tv_percent;
        ImageView importancyMark;
        CardView cv;

        public ViewHolder(View v) {
            super(v);
            TypefaceUtil.loadTypeface(context);
            tv_title = (TextView) v.findViewById(R.id.tv_title);
            tv_category = (TextView) v.findViewById(R.id.tv_category);
            tv_percent = (TextView) v.findViewById(R.id.tv_percent);

            tv_title.setTypeface(TypefaceUtil.typeface_m);
            tv_category.setTypeface(TypefaceUtil.typeface);
            tv_percent.setTypeface(TypefaceUtil.typeface);

            importancyMark = (ImageView) v.findViewById(R.id.importancyMark);
            cv = (CardView) v.findViewById(R.id.cv);
        }
    }
}
