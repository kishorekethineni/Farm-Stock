package com.example.farmstock;
//Developed By Pranshu Ranjan
import java.io.Serializable;

public class FoodItems implements Serializable {

    private  String mName;
    private int mAmount;
    private int mQuantity;

    public void setmQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public FoodItems(){}

    public FoodItems(String mName, int mAmount) {
        this.mName = mName;
        this.mAmount = mAmount;
        this.mQuantity = 1;
    }

    public String getmName() {
        return mName;
    }

    public int getmAmount() {
        return mAmount;
    }

    public int getmQuantity(){
        return mQuantity;
    }

    public void addToQuantity(){
        this.mQuantity += 1;
    }

    public void removeFromQuantity(){
        if(this.mQuantity > 1){
            this.mQuantity -= 1;
        }
    }

}