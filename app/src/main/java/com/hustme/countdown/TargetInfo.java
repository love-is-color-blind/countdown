package com.hustme.countdown;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TargetInfo {


    private static TargetInfo info;

    public static TargetInfo getInstance(Context context) {
        if (info == null) {
            info = new TargetInfo(context);

        }
        return info;
    }

    private TargetInfo(Context context) {
        this.context = context;

        SharedPreferences preferences = context.getSharedPreferences("TargetInfo", Context.MODE_PRIVATE);
        targetDate = preferences.getString("targetDate", null);
        targetTitle = preferences.getString("targetTitle", null);
        if (targetDate == null) {
            // 默认添加元旦
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int nexYear = year + 1;

            calendar.set(Calendar.YEAR, nexYear);
            calendar.set(Calendar.MONTH, 0);
            calendar.set(Calendar.DATE, 1);

            targetDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

            SharedPreferences.Editor edit = preferences.edit();
            edit.putString("targetDate", targetDate);
            targetTitle = "新年";
            edit.putString("targetTitle", targetTitle);

            edit.commit();
        }

    }

    private String targetTitle;
    private String targetDate;

    private Context context;


    public void save(String title, String date) {
        this.targetTitle = title;
        this.targetDate = date;
        SharedPreferences preferences = context.getSharedPreferences("TargetInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("targetDate", targetDate);
        edit.putString("targetTitle", targetTitle);
        edit.commit();

    }

    public String getTargetDate() {
        return targetDate;
    }

    public String getTargetTitle() {
        return targetTitle;
    }

    public int calcDays() {
        String targetDate = getTargetDate();
        try {
            Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(targetDate);

            long now = System.currentTimeMillis();
            long target = parse.getTime();

            return (int) ((target - now) / (1000 * 60 * 60 * 24)) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
