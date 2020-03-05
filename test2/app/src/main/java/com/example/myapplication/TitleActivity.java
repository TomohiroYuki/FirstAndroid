package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;

public class TitleActivity extends AppCompatActivity {

    private ImageView mogura_image;
    private AnimationDrawable frameAnimation;
    private TextView tips_text;
    private boolean can_game_start=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        mogura_image=findViewById(R.id.mogura);
        mogura_image.setBackgroundResource(R.drawable.mogura_animation);
        mogura_image.setOnClickListener(iconButtonOnClickListener);

        frameAnimation = (AnimationDrawable) mogura_image.getBackground();
        frameAnimation.start();

        SharedPreferences dataStore = getSharedPreferences("DataStore", MODE_PRIVATE);
        long data_long = dataStore.getLong("saved_score", -1);
        if(data_long==-1)
        {
            can_game_start=false;
        }
        tips_text=findViewById(R.id.tips);



        AlphaAnimation alphaFadeout = new AlphaAnimation(1.0f, 0.0f);
        alphaFadeout.setRepeatCount(Animation.INFINITE);
        alphaFadeout.setDuration(3000);
        // animationが終わったそのまま表示にする
        alphaFadeout.setFillAfter(true);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(1000);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(1000);



        tips_text.startAnimation(alphaFadeout);
    }

    private Button.OnClickListener iconButtonOnClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            clickHandler.sendEmptyMessage(0);
        }
    };
    private Handler clickHandler = new GameStartButtonHandler(this) ;
    private void iconButton_Click() {
        // ボタンを押したときの処理を記述
        if(!can_game_start)return;
        Intent intent = new Intent(getApplication(), MainActivity.class);
        startActivity(intent);


    }

    static class GameStartButtonHandler extends Handler
    {
        private final WeakReference<TitleActivity> activity_ref;

        GameStartButtonHandler(TitleActivity activity)
        {
            activity_ref= new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            activity_ref.get().iconButton_Click();
        }
    }



    private void TryToConnectWebAPI()
    {
//        try
//        {
//            URL url= new URL()
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//

    }

}
