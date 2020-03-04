package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.math.MathUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.myapplication.Classes.Mogura;
import com.example.myapplication.Classes.TimeText;

public class MainActivity extends AppCompatActivity implements Runnable
{

    private Mogura mogura;
    private Handler handler;
    public TimeText time_text;
    public TextView game_flow_text;
    public TextView remain_count_text;
    private long pre_time=0;
    private long current_time;

    private float game_timer=0;

    public enum GameScene
    {
        GAME_START,
        GAME_LOOP,
        GAME_END,
        STOP
    }
    private GameScene game_scene;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("mmm");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //gesture_detector = new GestureDetector(this, mOnGestureListener);
        System.out.println("mmm");
        time_text=new TimeText(this);
        game_flow_text= this.findViewById(R.id.game_flow);
        remain_count_text=this.findViewById(R.id.remain_count);
        System.out.println(game_flow_text);
        time_text.StartTime();
        mogura=new Mogura(this,time_text);
        handler= new Handler();

        game_scene=GameScene.GAME_START;
        game_timer=4.5f;
        current_time=System.nanoTime();
        pre_time=current_time;
        handler.postDelayed(this,(long)1.0/(long)60.0);

        remain_count_text.setText("残り："+mogura.remain_count+"匹");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //sphere.setX(sphere.getX()+12);
        //event.getAction()==MotionEvent.ACTION_MOVE

        return super.onTouchEvent(event);


    }

    @Override
    public void run()
    {
        current_time=System.nanoTime();
        float dt= (float)(current_time-pre_time)/1000000000.0f;
        //System.out.println(game_scene);
        switch (game_scene)
        {
            case GAME_START:
                    if(GameStartUpdate(dt))
                    {
                        game_scene=GameScene.GAME_LOOP;
                        time_text.StartTime();
                    }
                break;
            case GAME_LOOP:
                remain_count_text.setText("残り："+mogura.remain_count+"匹");
                if(mogura.remain_count==0)
                {
                    game_scene=GameScene.GAME_END;
                    game_timer=0;
                }
                break;
            case GAME_END:
                if(GameFinishUpdate(dt))
                {
                    game_scene=GameScene.STOP;
                    //リザルトシーンに遷移
                    Intent intent = new Intent(MainActivity.this, result.class);
                    intent.putExtra("score",time_text.result_time);
                    startActivity(intent);

                    System.out.println("end");
                }

                break;
            case STOP:

                break;
        }

        time_text.Update(dt);



        handler.postDelayed(this,(long)(1.0f/60.0f*1000f));//
        pre_time=current_time;
    }

    public boolean GameStartUpdate(float dt)
    {
        game_timer-=dt;

        if(game_timer<4&&game_timer>=1)
        {
            int display_time=(int)game_timer;
            game_flow_text.setTextSize(80);
            game_flow_text.setText(String.valueOf(display_time));
            game_flow_text.setAlpha(CalcTextAlpha(game_timer));

            //
            System.out.println(game_timer);
        }
        else if( game_timer<1&&game_timer>=0)
        {
            game_flow_text.setText("Start!");
            game_flow_text.setAlpha(CalcTextAlpha(game_timer));
        }
        else if(game_timer<0)
        {
            game_flow_text.setAlpha(0);
            mogura.GameStart();
            mogura.remain_count=10;
            return true;
        }


        return false;
    }

    public boolean GameFinishUpdate(float dt)
    {
        game_timer+=dt*0.3f;
        game_flow_text.setText("Finish!");
        game_flow_text.setAlpha(CalcTextAlpha(game_timer));

        return game_timer>1.0f;



    }

    public float CalcTextAlpha(float time)
    {
        float surplus=time%1.0f;
        return Lerp(0.0f,1.0f,surplus*2);
    }

    public float Lerp(float a,float b,float f)// 0 is a, 1 is b
    {
        float factor= MathUtils.clamp(f,0,1);
        return b*factor + (1-factor) * a;
    }

    }
