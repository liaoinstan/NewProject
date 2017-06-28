package com.ins.newproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.ins.common.helper.CropHelper;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StrUtil;

public class PhotoActivity extends AppCompatActivity implements CropHelper.CropInterface, CompoundButton.OnCheckedChangeListener {

    private CropHelper cropHelper;
    private ImageView img_photo;
    private CheckBox check_needcrop;
    private CheckBox check_needpress;
    private CheckBox check_needasync;
    private CheckBox check_needforcelv;
    private EditText edit_x;
    private EditText edit_y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initBase();
        initView();
        initCtrl();
    }

    private void initBase() {
        cropHelper = new CropHelper(this, this);
    }

    private void initView() {
        img_photo = (ImageView) findViewById(R.id.img_photo);
        check_needcrop = (CheckBox) findViewById(R.id.check_needcrop);
        check_needpress = (CheckBox) findViewById(R.id.check_needpress);
        check_needasync = (CheckBox) findViewById(R.id.check_needasync);
        check_needforcelv = (CheckBox) findViewById(R.id.check_needforcelv);
        edit_x = (EditText) findViewById(R.id.edit_x);
        edit_y = (EditText) findViewById(R.id.edit_y);
        check_needcrop.setOnCheckedChangeListener(this);
        check_needpress.setOnCheckedChangeListener(this);
        check_needasync.setOnCheckedChangeListener(this);
        check_needforcelv.setOnCheckedChangeListener(this);
        check_needcrop.setChecked(cropHelper.isNeedCrop());
        check_needpress.setChecked(cropHelper.isNeedPress());
        check_needasync.setChecked(cropHelper.isASync());
        check_needforcelv.setChecked(cropHelper.isNeedForceLv());
        edit_x.setText(cropHelper.getAspectX() + "");
        edit_y.setText(cropHelper.getAspectY() + "");
    }

    private void initCtrl() {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.check_needcrop:
                cropHelper.setNeedCrop(isChecked);
                break;
            case R.id.check_needpress:
                cropHelper.setNeedPress(isChecked);
                break;
            case R.id.check_needasync:
                cropHelper.setASync(isChecked);
                break;
            case R.id.check_needforcelv:
                cropHelper.setNeedForceLv(isChecked);
                edit_x.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                edit_y.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                break;
        }
    }

    public void onClick(View v) {
        cropHelper.setAspectX(StrUtil.str2int(edit_x.getText().toString(), 1));
        cropHelper.setAspectY(StrUtil.str2int(edit_y.getText().toString(), 1));
        switch (v.getId()) {
            case R.id.btn_gocamera:
                cropHelper.startCamera();
                break;
            case R.id.btn_gophoto:
                cropHelper.startPhoto();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        cropHelper.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void cropResult(String path) {
        Log.e("path", path);
        GlideUtil.loadImg(this, img_photo, R.drawable.default_bk_img, path);
    }

    @Override
    public void cancel() {
    }
}
