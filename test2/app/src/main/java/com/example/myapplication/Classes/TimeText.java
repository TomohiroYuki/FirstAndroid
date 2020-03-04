package com.example.myapplication.Classes;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.text.format.Time;
import android.widget.TextView;

import androidx.core.math.MathUtils;

import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeText
{
    public TimeText(Activity activity)
    {

        text_view= activity.findViewById(R.id.time_text);

       // String diffTime = Format(start_time_ms,end_time_ms);
        text_view.setText("");

        result_time=0;
    }

    private float time_s;
    private float color_alpha=0;
    private TextView text_view;
    private long start_time_ms=0;

    public static long result_time=0;
    //private long end_time_ms=0;


    private static final int TIME_RESULT_POOR_MS = 700;
    private static final int TIME_RESULT_GREAT_MS =500;

    //https://qiita.com/deaf_tadashi/items/8b9e00bf5385c33eb8fd
    private static String Format(long startTime, long endTime)
    {

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        Calendar result = Calendar.getInstance();

        start.setTimeInMillis(startTime);
        end.setTimeInMillis(endTime);

        long time_length = end.getTimeInMillis() - start.getTimeInMillis() ;

        result_time+=time_length;
        System.out.println("test");

        result.setTimeInMillis( time_length);
        System.out.println(result.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("m:ss:SS");

        return sdf.format(result.getTime());
    }

    public void StartTime()
    {
        start_time_ms=System.currentTimeMillis();

    }

    public void ShowText(float x,float y)
    {
        long end_time_ms=System.currentTimeMillis();
        String diffTime = Format(start_time_ms,end_time_ms);
        time_s=0;
        text_view.setX(x);
        text_view.setY(y);

        float scale=GetScaleByTimeResult(end_time_ms-start_time_ms);
        text_view.setScaleX(scale);
        text_view.setScaleY(scale);
        text_view.setTextColor(GetColorByTimeResult(end_time_ms-start_time_ms));
        text_view.setText(diffTime);
    }

    public void Update(float dt)
    {
        time_s+=dt;
        color_alpha=Lerp(1,0,time_s*2-1.0f);
        text_view.setAlpha(color_alpha);

    }

    public float Lerp(float a,float b,float f)// 0 is a, 1 is b
    {
        float factor= MathUtils.clamp(f,0,1);
        return b*factor + (1-factor) * a;
    }

    public int GetColorByTimeResult(long diff_ms)
    {
        if(diff_ms<TIME_RESULT_GREAT_MS)
            return Color.YELLOW;
        else if(diff_ms<TIME_RESULT_POOR_MS)
            return Color.LTGRAY;
        else return  Color.BLACK;
    }

    public float GetScaleByTimeResult(long diff_ms)
    {
        if(diff_ms<TIME_RESULT_GREAT_MS)
            return 2;
        else if(diff_ms<TIME_RESULT_POOR_MS)
            return 1.5f;
        else return  1;
    }
}
