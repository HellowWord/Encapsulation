package com.hellow.xuefeng.hwlibrary.components.timebutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;

import com.hellow.xuefeng.hwlibrary.R;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Phoenix on 2015/11/25.
 */
public class TimeButton extends AppCompatButton {

    private static String mtimeText;
    private TimerHandler mTimerHandler;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private int mTime;
    private CharSequence beforeText, afterText;
    private static long flagTime;

    public TimeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TimeButton, defStyle, 0);
        afterText = a.getString(R.styleable.TimeButton_end_text);
        beforeText = getText();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mTimerHandler = new TimerHandler(this);
        int time = (int) ((flagTime - System.currentTimeMillis()) / 1000);
        if (time > 1) {
            startTimer(time);
        }
        flagTime = 0;
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mTime != 0) {
            flagTime = System.currentTimeMillis() + mTime * 1000;
        }
        clean(null);
        super.onDetachedFromWindow();
    }


    private static class TimerHandler extends Handler {
        private WeakReference<TimeButton> mTimerButton;

        public TimerHandler(TimeButton button) {
            mTimerButton = new WeakReference<TimeButton>(button);
        }

        @Override
        public void handleMessage(Message msg) {
            TimeButton timeButton = mTimerButton.get();
            if (timeButton == null || msg == null || msg.what != 0x1) {
                return;
            }
            timeButton.changeTimer();
        }
    }

    private static class ButtonTimer extends TimerTask {
        private WeakReference<TimeButton> mTimerButton;

        public ButtonTimer(TimeButton button) {
            mTimerButton = new WeakReference<TimeButton>(button);
        }

        @Override
        public void run() {
            TimeButton timeButton = mTimerButton.get();
            if (timeButton == null) {
                return;
            }
            timeButton.mTimerHandler.sendEmptyMessage(0x1);
        }
    }

    private void changeTimer() {
        mtimeText = getResources().getString(R.string.time_second);
        setText(mTime + mtimeText);
        mTime -= 1;
        if (mTime < 0) {
            onEnd();
        }
    }

    public void startTimer(int time) {
        clean(null);
        mTime = time;
        TimeButton.this.setEnabled(false);
        mTimer = new Timer();
        mTimerTask = new ButtonTimer(this);
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    public void stopTimer() {
        clean(beforeText);
    }

    private void clean(CharSequence text) {
        mTime = 0;
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        TimeButton.this.setEnabled(true);
        if (text != null) {
            TimeButton.this.setText(text);
        }
    }

    private void onEnd() {
        clean(afterText);
    }
}
