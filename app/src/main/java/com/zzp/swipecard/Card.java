package com.zzp.swipecard;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/17 0017.
 * 自定义卡片
 */
public class Card extends RelativeLayout
{
    private Context mContext = null;
    private float[] metrics = new float[2];
    private float fAngle = 0.0f;    //	图片旋转

    private int width = 0;
    private int height = 0;

    private float maxDegree = 40;
    private String origin = null;

    private TextView tv = null;

    public Card(Context context, float[] metrics)
    {
        super(context);
        this.metrics = metrics;
        init(context);
    }

    public Card(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public Card(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        this.mContext = context;
    }

    float startX = 0;
    float startY = 0;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
        if (width == 0) {
            width = this.getMeasuredWidth();
            height = this.getMeasuredHeight();
            tv = (TextView) getChildAt(0);
            origin = tv.getText().toString();
            tv.setTextSize(25);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float dix = 0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getX();
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:

                dix = (event.getX() - startX);

                fAngle = (float) (dix*0.1);

                //向右
                if (dix > 0) {
                    if (fAngle > 0) {
                        this.setPivotX(width - 100);
                        this.setPivotY(width);
                    }
                }
                //向左
                else if (dix < 0) {
                    if (fAngle < 0) {
                        this.setPivotX(100);
                        this.setPivotY(width);
                    }
                }

                //如果旋转的角度大于棍定的角度，就隐藏这个View
                if (fAngle == maxDegree || fAngle == -maxDegree) {
                    this.setVisibility(View.GONE);
                }

                if (fAngle >= (maxDegree - 20)) {
                    tv.setText("继续右滑删除");
                } else if (fAngle <= (-maxDegree + 20)) {
                    tv.setText("继续左滑删除");
                } else {
                    tv.setText(origin);
                }

                MyLog.e("fAnble=" + fAngle);

                if (Math.abs(dix) > 0) {
                    this.setRotation(fAngle);
                }

                //向右
                if (dix > 0 && fAngle < maxDegree) {
                    this.setAlpha(Math.abs(1 - fAngle / 100f));
                }
                //向左
                else if (dix < 0 && fAngle > -maxDegree) {
                    this.setAlpha(Math.abs(1 + fAngle / 100f));
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (fAngle < maxDegree || fAngle > -maxDegree) {
                    this.setRotation(0);
                    fAngle = 0;
                    setAlpha(1.0f);
                    tv.setText(origin);
                }
                return super.onTouchEvent(event);
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        invalidate();
    }
}
