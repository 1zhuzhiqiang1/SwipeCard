package com.zzp.swipecard;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    private RelativeLayout rl_container = null;
    private Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        rl_container = (RelativeLayout) findViewById(R.id.rl_container);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int statusBarHeight = DensityUtil.dip2px(mContext,25);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels - statusBarHeight;

        float[] metric = new float[2];
        metric[0] = screenWidth/2-200;
        metric[1] = screenHeight/2-200;

        //添加10个Card
        for (int i=0;i<10;i++){
            Card card = new Card(mContext,metric);
            card.setBackgroundColor(Color.parseColor("#cccc00"));
            LayoutParams layoutParams = new LayoutParams(600,700);
            card.setX(50);
            card.setY(100);
            card.setLayoutParams(layoutParams);
            card.setGravity(Gravity.CENTER);
            TextView tv = new TextView(mContext);
            tv.setGravity(Gravity.CENTER);
            tv.setText("item"+i);
            tv.setBackgroundColor(Color.parseColor("#cccccc"));
            card.addView(tv);
            rl_container.addView(card);
        }
    }
}
