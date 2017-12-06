package com.example.csy.project_demo;

import android.content.ContentUris;

/**
 * Created by csy on 2017-11-30.
 */

public class CurrentInfo {
    final static int ID = 1;
    final static int TEMPER = 2;
    private static String userID;
    private static double temperature;

    public static void SET(int variable, String value){
        if(variable==CurrentInfo.TEMPER)
            CurrentInfo.temperature = Double.parseDouble(value);
        else if(variable==CurrentInfo.ID)
            CurrentInfo.userID = value;
    }

    public static String GET(int variable){
        if(variable == CurrentInfo.TEMPER)
            return Double.toString(CurrentInfo.temperature);
        else if(variable == CurrentInfo.ID)
            return CurrentInfo.userID;
        else
            return null;
    }
}
