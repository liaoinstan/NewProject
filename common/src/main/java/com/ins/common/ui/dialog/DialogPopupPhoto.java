package com.ins.common.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ins.common.R;

/**
 * liaoinstan
 * 选择照片或拍照弹窗
 */
public class DialogPopupPhoto extends Dialog {
    private TextView text_cancel, text_photo, text_camera;
    private Context context;

    public DialogPopupPhoto(Context context) {
        super(context, R.style.PopupDialog);
        this.context = context;
        setMsgDialog();
    }

    private void setMsgDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_choose_photo, null);
        text_cancel = (TextView) mView.findViewById(R.id.text_pop_identy_cancel);
        text_photo = (TextView) mView.findViewById(R.id.text_pop_identy_photo);
        text_camera = (TextView) mView.findViewById(R.id.text_pop_identy_camera);
        text_cancel.setOnClickListener(listener);
        text_photo.setOnClickListener(listener);
        text_camera.setOnClickListener(listener);

        this.setCanceledOnTouchOutside(true);    //点击外部关闭

        Window win = this.getWindow();
        win.setGravity(Gravity.BOTTOM);    //从下方弹出
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);

        super.setContentView(mView);
    }

    @Override
    public void show() {
        super.show();
    }

    /**
     * 相册键监听器
     */
    public void setOnPhotoListener(View.OnClickListener listener) {
        text_photo.setOnClickListener(listener);
    }

    /**
     * 相机键监听器
     */
    public void setOnCameraListener(View.OnClickListener listener) {
        text_camera.setOnClickListener(listener);
    }

    /**
     * 取消监听，不光取消按钮，点击外部，返回键等一切导致dialog消失的操作都会回调这个监听
     */
    @Override
    public void setOnCancelListener(final OnCancelListener listener) {
        super.setOnCancelListener(listener);
        text_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel(DialogPopupPhoto.this);
                DialogPopupPhoto.this.dismiss();
            }
        });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogPopupPhoto.this.dismiss();
        }
    };
}
