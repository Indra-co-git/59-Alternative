package com.indra.alter;

public class App {


    String appname,alternativeurl;

    public App(){

    }

    public App(String n,String a)
    {
        appname = n;
        alternativeurl = a;
    }


    public String getAppname(){
        return appname;
    }

    public String getAlternativeurl() {
        return alternativeurl;
    }
}
