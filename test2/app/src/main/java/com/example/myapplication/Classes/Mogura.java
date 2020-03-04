package com.example.myapplication.Classes;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class Mogura {

    private ImageView image;
    private TimeText time_text_ref;
    private Activity parent_activity;
    private long mogura_elapsed_time;
    private Point window_size;
    private Point position;
    private boolean is_active=false;
    private AnimationDrawable frameAnimation;

    private static final int MAXMOGURA_COUNT = 10;

    public int remain_count;


    public Mogura(Activity activity,TimeText in_time_text)
    {

        remain_count=MAXMOGURA_COUNT;
        parent_activity=activity;
        image=new ImageView(parent_activity);
        //image.setImageResource(R.drawable.heart);
        image.setBackgroundResource(R.drawable.mogura_animation);
        frameAnimation = (AnimationDrawable) image.getBackground();
        frameAnimation.start();
        time_text_ref=in_time_text;
        System.out.println("test");
        LinearLayout lay = (LinearLayout)activity.findViewById(R.id.linear);
        lay.addView(image);
        Math.random();
        WindowManager wm = activity.getWindowManager();
        Display display = wm.getDefaultDisplay();
        window_size = new Point();
        display.getSize(window_size);
        System.out.println(window_size);
        display.getSize(window_size);
        image.setX(window_size.x * 0.5f-100);
        image.setY(window_size.y * -0.4f+100);
        image.setAlpha(0.5f);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(285, 240);
        image.setLayoutParams(layoutParams);
        image.setOnClickListener(

                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        if(!is_active)return;
                        // イメージ画像がクリックされたときに実行される処理
                        time_text_ref.ShowText(image.getX(),image.getY());

                        ChangePoint();
                        time_text_ref.StartTime();
                    }
                }
        );
        //image.onTouchEvent( )
    }


    public void ChangePoint()
    {
        remain_count--;

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(285, 240);
        image.setLayoutParams(layoutParams);
        mogura_elapsed_time = System.currentTimeMillis();

        float pos_x= remain_count>0?(window_size.x * (float)Math.random()*0.8f):-9999;
        image.setX(pos_x);
        System.out.println(pos_x);
        image.setY(window_size.y * (float)Math.random()*0.8f);
    }


    public void GameStart()
    {
        image.setAlpha(1.0f);
        ChangePoint();
        is_active=true;
    }


}
