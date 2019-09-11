package com.example.csy.project_demo;

import android.content.ContentUris;

/**
 * Created by csy on 2017-11-30.
 */

public class CurrentInfo {
    final static int ID = 1;
    final static int TEMPER = 2;
    final static int WEATHER=3;
    final static int URL=4;
    private static String userID;
    private static double temperature;
    private static String weather;
    private static String url;

    public static void SET(int variable, String value){
        if(variable==CurrentInfo.TEMPER)
            CurrentInfo.temperature = Double.parseDouble(value);
        else if(variable==CurrentInfo.ID)
            CurrentInfo.userID = value;
        else if(variable==CurrentInfo.WEATHER)
            CurrentInfo.weather=value;
        else if(variable==CurrentInfo.URL)
            CurrentInfo.url=value;

    }

    public static String GET(int variable){
        if(variable == CurrentInfo.TEMPER)
            return Double.toString(CurrentInfo.temperature);
        else if(variable == CurrentInfo.ID)
            return CurrentInfo.userID;
        else if(variable==CurrentInfo.WEATHER)
            return CurrentInfo.weather;
        else if(variable==CurrentInfo.URL)
            return CurrentInfo.url;
        else
            return null;
    }
}
