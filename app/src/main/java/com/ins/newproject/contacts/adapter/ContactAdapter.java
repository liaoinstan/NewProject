package com.ins.newproject.contacts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.ins.newproject.R;
import com.ins.newproject.contacts.ContactBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MQ on 2017/5/8.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyRecycleHolder> {

    private List<ContactBean> results;
    private Context context;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder = TextDrawable.builder().round();

    public List<ContactBean> getResults() {
        return results;
    }

    public ContactAdapter(Context context) {
        this.context = context;
        results = new ArrayList<>();
    }

    public void add(ContactBean bean, int position) {
        results.add(position, bean);
        notifyItemInserted(position);
    }

    @Override
    public MyRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_layout, parent, false);
        return new MyRecycleHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecycleHolder holder, int position) {
        if (results == null || results.size() == 0 || results.size() <= position)
            return;
        ContactBean bean = results.get(position);
        if (bean != null) {
            holder.tv_name.setText(bean.getName());
            TextDrawable drawable = mDrawableBuilder.build(String.valueOf(bean.getName().charAt(0)), mColorGenerator.getColor(bean.getName()));
            holder.iv_img.setImageDrawable(drawable);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class MyRecycleHolder extends RecyclerView.ViewHolder {
        public final TextView tv_name;
        public final ImageView iv_img;

        public MyRecycleHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}
