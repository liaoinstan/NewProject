package com.ins.newproject.contacts.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.ins.common.utils.FocusUtil;
import com.ins.newproject.R;
import com.ins.newproject.contacts.adapter.SortAdapter;
import com.ins.newproject.contacts.bean.SortBean;
import com.ins.common.common.ItemDecorationSortStickTop;
import com.ins.newproject.contacts.util.ColorUtil;
import com.ins.newproject.contacts.util.SortUtil;
import com.ins.common.view.IndexBar;
import com.ins.common.view.SideBar;

import java.util.ArrayList;
import java.util.List;

public class SortActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edit_query;
    private IndexBar index_bar;
    private RecyclerView recycler;
    private SortAdapter adapter;
    private ItemDecorationSortStickTop decoration;
    private List<SortBean> results = new ArrayList<>();
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        initView();
        initCtrl();
        initData();
    }

    public void initView() {
        edit_query = (EditText) findViewById(R.id.edit_query);
        index_bar = (IndexBar) findViewById(R.id.index_bar);
        recycler = (RecyclerView) findViewById(R.id.rl_recycle_view);
        findViewById(R.id.btn_right).setOnClickListener(this);
        FocusUtil.focusToTop(recycler);
    }

    public void initCtrl() {
        adapter = new SortAdapter(this);
        recycler.setLayoutManager(layoutManager = new LinearLayoutManager(this));
        recycler.addItemDecoration(decoration = new ItemDecorationSortStickTop(this, ColorUtil.colors));
        recycler.setAdapter(adapter);

        index_bar.setColors(ColorUtil.colors);
        index_bar.addOnIndexChangeListener(new SideBar.OnIndexChangeListener() {
            @Override
            public void onIndexChanged(float centerY, String tag, int position) {
                int pos = SortUtil.getPosByTag(results, tag);
                if (pos != -1) layoutManager.scrollToPositionWithOffset(pos, 0);
            }
        });

        edit_query.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String[] names = {"孙尚香", "安其拉", "白起", "不知火舞", "@小马快跑", "_德玛西亚之力_", "妲己", "狄仁杰", "典韦", "韩信",
                        "老夫子", "刘邦", "刘禅", "鲁班七号", "墨子", "孙膑", "孙尚香", "孙悟空", "项羽", "亚瑟",
                        "周瑜", "庄周", "蔡文姬", "甄姬", "廉颇", "程咬金", "后羿", "扁鹊", "钟无艳", "小乔", "王昭君", "虞姬",
                        "李元芳", "张飞", "刘备", "牛魔", "张良", "兰陵王", "露娜", "貂蝉", "达摩", "曹操", "芈月", "荆轲", "高渐离",
                        "钟馗", "花木兰", "关羽", "李白", "宫本武藏", "吕布", "嬴政", "娜可露露", "武则天", "赵云", "姜子牙",};
                for (String name : names) {
                    SortBean bean = new SortBean();
                    bean.setName(name);
                    results.add(bean);
                }
                freshData(results);
            }
        }, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                break;
        }
    }

    private void freshData(List<SortBean> results) {
        SortUtil.sortData(results);
        String tagsStr = SortUtil.getTags(results);
        List<String> tagsArr = SortUtil.getTagsArr(results);
        index_bar.setIndexStr(tagsStr);
        decoration.setTags(tagsArr);
        adapter.getResults().clear();
        adapter.getResults().addAll(results);
        adapter.notifyDataSetChanged();
    }

    public void search(String filterStr) {
        List<SortBean> resultsSort = new ArrayList<>();
        for (SortBean sortBean : results) {
            if (SortUtil.match(sortBean, filterStr)) {
                resultsSort.add(sortBean);
            }
        }
        freshData(resultsSort);
    }
}
