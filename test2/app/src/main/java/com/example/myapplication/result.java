package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class result extends AppCompatActivity {

    private long result_time=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //result_time
        Intent intent = this.getIntent();
        result_time = intent.getLongExtra("score",0);
        TextView textView = (TextView)this.findViewById(R.id.result_text);

        SimpleDateFormat sdf = new SimpleDateFormat("m:ss:SS");



        textView.setText(sdf.format(result_time));


        findViewById(R.id.title_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // クリック時の処理
                Intent intent = new Intent(getApplication(), TitleActivity.class);

                startActivity(intent);

            }
        });

        findViewById(R.id.retry_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // クリック時の処理
                Intent intent = new Intent(getApplication(), MainActivity.class);

                startActivity(intent);

            }
        });

    }
}
