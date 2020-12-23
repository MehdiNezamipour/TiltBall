package com.nezamipour.mehdi.tiltball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class BallView extends View {

    float mX;
    float mY;
    private int mRadius;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public BallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BallView(Context context, float x, float y, int r) {
        super(context);
        mPaint.setColor(0xFF00FF00);
        this.mX = x;
        this.mY = y;
        this.mRadius = r;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mX, mY, mRadius, mPaint);
    }




}
