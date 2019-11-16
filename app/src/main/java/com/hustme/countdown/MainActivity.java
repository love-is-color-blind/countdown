package com.hustme.countdown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private String mDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendBroadcast();

        final ViewInfo info = ViewInfo.getInstance(this);
        DatePicker tvDate = findViewById(R.id.edit_date);

        try {
            mDate = info.getTargetDate();
            Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(mDate);
            Calendar instance = Calendar.getInstance();
            instance.setTime(parse);
            tvDate.init(instance.get(Calendar.YEAR), instance.get(Calendar.MONTH),instance.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(Calendar.YEAR, year);
                    selectedDate.set(Calendar.MONTH, monthOfYear);
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    mDate = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate.getTime());
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }


        TextView tvTitle = findViewById(R.id.edit_title);
        tvTitle.setText(info.getTargetTitle());
//        this.onBackPressed();
    }


    public void onSubmit(View view) {
        ViewInfo info = ViewInfo.getInstance(this);
        TextView tvTitle = findViewById(R.id.edit_title);
        if (mDate.length() != 10) {
            Toast.makeText(this, "日期输入错误" + mDate, Toast.LENGTH_SHORT).show();
            return;
        }
        info.save(tvTitle.getText().toString(), mDate);
        Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
        this.sendBroadcast();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },750);


    }


    private void sendBroadcast() {
        Intent intent = new Intent(AppWidget.ACTION_AUTO_UPDATE);
        intent.setClass(getApplicationContext(), AppWidget.class);
        this.sendBroadcast(intent);
    }
}
