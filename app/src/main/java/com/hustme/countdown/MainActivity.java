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
        this.sendBroadcast(new Intent(AppWidget.ACTION_AUTO_UPDATE));

        TargetInfo info = TargetInfo.getInstance(this);
        TextView tvDate = findViewById(R.id.edit_date);
        TextView tvTitle = findViewById(R.id.edit_title);
        tvDate.setText(info.getTargetDate());
        tvTitle.setText(info.getTargetTitle());
//        this.onBackPressed();
    }

    public void onSubmit(View view) {
        TargetInfo info = TargetInfo.getInstance(this);
        TextView tvDate = findViewById(R.id.edit_date);
        TextView tvTitle = findViewById(R.id.edit_title);
        info.save(tvTitle.getText().toString(), tvDate.getText().toString());
        Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
    }
}
