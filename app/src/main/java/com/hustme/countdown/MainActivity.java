package com.hustme.countdown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendBroadcast();

        ViewInfo info = ViewInfo.getInstance(this);
        TextView tvDate = findViewById(R.id.edit_date);
        TextView tvTitle = findViewById(R.id.edit_title);
        tvDate.setText(info.getTargetDate());
        tvTitle.setText(info.getTargetTitle());
//        this.onBackPressed();
    }



    public void onSubmit(View view) {
        ViewInfo info = ViewInfo.getInstance(this);
        TextView tvDate = findViewById(R.id.edit_date);
        TextView tvTitle = findViewById(R.id.edit_title);
        String date = tvDate.getText().toString();
        if(date.length() != 10) {
            Toast.makeText(this, "日期输入错误", Toast.LENGTH_SHORT).show();
            return;
        }
        info.save(tvTitle.getText().toString(), date);
        Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
        this.sendBroadcast();
    }

    public void onClose(View view) {
        this.finish();
    }
    private void sendBroadcast() {
        Intent intent = new Intent(AppWidget.ACTION_AUTO_UPDATE);
        intent.setClass(getApplicationContext(), AppWidget.class);
        this.sendBroadcast(intent);
    }
}
