package com.indra.alter;


import android.graphics.drawable.Drawable;

public class Item {


    String appname,appdetails;

    Drawable appicon;

    public  Item(){

    }
    public Item(String n,String d)
    {
        appname=n;
        appdetails=d;
    }

    public Item(String n,String d,Drawable r)
    {
        appname=n;
        appdetails=d;
        appicon=r;
    }

    public void setAppdetails(String s)
    {
        appdetails = s;
    }

    public String getAppdetails() {
        return appdetails;
    }

    public String getAppname() {
        return appname;
    }

    public Drawable getAppicon(){
        return appicon;
    }

}
