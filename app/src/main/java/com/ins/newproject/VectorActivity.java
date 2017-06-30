package com.ins.newproject;

import android.Manifest;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class VectorActivity extends AppCompatActivity {

    private ImageView img_svg;
    private TextView text_svg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initBase();
        initView();
        initCtrl();
    }

    private void initBase() {
    }

    private void initView() {
        img_svg = (ImageView) findViewById(R.id.img_svg);
        text_svg = (TextView) findViewById(R.id.text_svg);
    }

    private void initCtrl() {
//        img_svg.setImageResource(R.drawable.ic_11);
        text_svg.setBackgroundResource(R.drawable.ic_11);

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_11);
        AnimatedVectorDrawableCompat animatedVectorDrawableCompat = AnimatedVectorDrawableCompat.create(this, R.drawable.ic_11);
//        img_svg.setImageDrawable(animatedVectorDrawableCompat);
    }
}
