package com.example.farmstock.ui.tools;
//Developed By Pranshu Ranjan
public class ExampleItem {
    private String mText1,mText2;

    public void changeText2(String text){
        mText2=text;

    }
    public ExampleItem(String mText1, String mText2) {
        this.mText1 = mText1;
        this.mText2 = mText2;
    }

    public String getmText1() {
        return mText1;
    }

    public String getmText2() {
        return mText2;
    }
}
