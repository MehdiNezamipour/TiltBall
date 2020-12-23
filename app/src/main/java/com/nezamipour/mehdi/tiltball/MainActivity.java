package com.nezamipour.mehdi.tiltball;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.FrameLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TiltBall";
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

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                mBallSpd.x = -event.values[0];
                mBallSpd.y = event.values[1];
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, ((SensorManager) getSystemService(Context.SENSOR_SERVICE))
                .getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_NORMAL);

        mainView.setOnTouchListener((v, event) -> {
            mBallPos.x = event.getX();
            mBallPos.y = event.getY();
            return true;
        });

    }

    @Override
    public void onPause() {
        mTimer.cancel();
        mTimer = null;
        mTimerTask = null;
        super.onPause();
    }

    @Override
    protected void onResume() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                android.util.Log.d(TAG, "Timer Hit - " + mBallPos.x + ":" + mBallPos.y);

                mBallPos.x += mBallSpd.x;
                mBallPos.y += mBallSpd.y;

                if (mBallPos.x > mScrWidth) mBallPos.x = 0;
                if (mBallPos.y > mScrHeight) mBallPos.y = 0;
                if (mBallPos.x < 0) mBallPos.x = mScrWidth;
                if (mBallPos.y < 0) mBallPos.y = mScrHeight;

                mBallView.mX = mBallPos.x;
                mBallView.mY = mBallPos.y;
                mRedrawHandler.post(() -> mBallView.invalidate());

            }
        };

        mTimer.schedule(mTimerTask, 10, 10);
        super.onResume();

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}