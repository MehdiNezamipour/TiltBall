package com.nezamipour.mehdi.tiltball;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.FrameLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private BallView mBallView = null;
    private Handler mRedrawHandler = new Handler();
    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private int mScrWidth, mScrHeight;
    private PointF mBallPos, mBallSpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FrameLayout mainView = findViewById(R.id.main_view);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = getWindowManager().getCurrentWindowMetrics();
            mScrHeight = windowMetrics.getBounds().height();
            mScrWidth = windowMetrics.getBounds().width();

        } else {
            Display display = getWindowManager().getDefaultDisplay();
            mScrWidth = display.getWidth();
            mScrHeight = display.getHeight();
        }
        mBallPos = new PointF();
        mBallSpd = new PointF();

        mBallPos.x = mScrWidth / 2;
        mBallPos.y = mScrHeight / 2;
        mBallSpd.x = 0;
        mBallSpd.y = 0;

        mBallView = new BallView(this, mBallPos.x, mBallPos.y, 30);
        mainView.addView(mBallView);
        //call on draw of BallView
        mBallView.invalidate();

        


    }
}