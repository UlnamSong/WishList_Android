package com.ulnamsong.todolist.DataBase;

/**
 * Created by Ulnamsong on 2016. 12. 9..
 */

public class WishData {
    public int _id = 0;
    public String wishTitle = "";
    public int wishCategoryIndex = 0;
    public int currentValue = 0;
    public int maximumValue = 0;
    public int importancyValue = 0; // 0 : High, 1 : Regular, 2 : Low, 3 : Finished

    public WishData() {

    }

    public WishData(int _id, String wishTitle, int wishCategoryIndex, int currentValue, int maximumValue, int importancyValue) {
        this._id = _id;
        this.wishTitle = wishTitle;
        this.wishCategoryIndex = wishCategoryIndex;
        this.currentValue = currentValue;
        this.maximumValue = maximumValue;
        this.importancyValue = importancyValue;
    }
}
