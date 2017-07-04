package com.ins.newproject.view;

import android.content.Context;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.ins.common.utils.L;

/**
 * Created by liaoinstan on 2017/7/3.
 * 这个SeekBar继承自系统SeekBar ，新增了临界值的功能：比如设置临界值：50，那么滑条滑动不会超过50
 * <p>
 * 注意：
 * 这个功能主要在AppCompatSeekBar.OnSeekBarChangeListener的监听中实现功能然后回调自己的同名监听接口LimitSeekBar.OnSeekBarChangeListener2
 * 注意要使用setOnSeekBarChangeListener(LimitSeekBar.OnSeekBarChangeListener2)代替setOnSeekBarChangeListener(AppCompatSeekBar.OnSeekBarChangeListener2)
 */

public class LimitSeekBar extends AppCompatSeekBar {

    private Context context;
    private int limitProgress = -1; //临界值，如果为-1则表示失效

    public LimitSeekBar(Context context) {
        this(context, null);
    }

    public LimitSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.seekBarStyle);
    }

    public LimitSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setOnSeekBarChangeListener(new AppCompatSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (onSeekBarChangeListener != null) {
                    if (fromUser && progress >= limitProgress && limitProgress != -1) {
                        seekBar.setProgress(limitProgress);
                        onSeekBarChangeListener.onProgressChanged((LimitSeekBar) seekBar, limitProgress, fromUser);
                    } else {
                        onSeekBarChangeListener.onProgressChanged((LimitSeekBar) seekBar, progress, fromUser);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (onSeekBarChangeListener != null)
                    onSeekBarChangeListener.onStartTrackingTouch((LimitSeekBar) seekBar);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (onSeekBarChangeListener != null)
                    onSeekBarChangeListener.onStopTrackingTouch((LimitSeekBar) seekBar);
            }
        });
    }

    public synchronized void setProgress(int progress, int saveLimitProgress) {
        setProgress(progress);
        this.limitProgress = saveLimitProgress;
    }

    private OnSeekBarChangeListener onSeekBarChangeListener;

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener onSeekBarChangeListener) {
        this.onSeekBarChangeListener = onSeekBarChangeListener;
    }

    public interface OnSeekBarChangeListener {
        void onProgressChanged(LimitSeekBar seekBar, int progress, boolean fromUser);

        void onStartTrackingTouch(LimitSeekBar seekBar);

        void onStopTrackingTouch(LimitSeekBar seekBar);
    }

    ////////////// get & set ////////////

    public int getLimitProgress() {
        return limitProgress;
    }

    public void setLimitProgress(int limitProgress) {
        this.limitProgress = limitProgress;
    }
}
