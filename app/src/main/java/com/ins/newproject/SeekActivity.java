package com.ins.newproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;

import com.ins.common.utils.L;
import com.ins.newproject.view.LimitSeekBar;

public class SeekActivity extends AppCompatActivity {

    private LimitSeekBar seek;
    private int end = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initBase();
        initView();
        initCtrl();
    }

    private void initBase() {
    }

    private void initView() {
        seek = (LimitSeekBar) findViewById(R.id.seek);
    }

    private void initCtrl() {
        //TODO：要做到的功能：
        /**
         * 1：开始结束监听 和
         * setProgress
         * setSecondaryProgress
         * setVisibility
         * setMax
         * setOnSeekBarChangeListener2
         * 继承重写，或者包装代理
         *
         * 大于进度值UI不拖动
         * 大于进度值只回调最大值，并且fromUser = true
         */

        seek.setOnSeekBarChangeListener(new LimitSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(LimitSeekBar seekBar, int progress, boolean fromUser) {
                L.d("Changed:" + progress + "fromUser:" + fromUser);
            }

            @Override
            public void onStartTrackingTouch(LimitSeekBar seekBar) {
                L.e("start:" + seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(LimitSeekBar seekBar) {
                L.e("end:" + seekBar.getProgress());
            }
        });
    }

    public void onClick(View v) {
        int pro = seek.getProgress();
        seek.setProgress(pro + 10, pro + 10);
    }
}
