package com.ins.newproject.contacts;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.ins.common.utils.FocusUtil;
import com.ins.newproject.R;
import com.ins.newproject.contacts.adapter.ContactAdapter;
import com.ins.newproject.contacts.common.CustomItemDecoration;
import com.ins.newproject.contacts.util.CommonUtil;
import com.ins.newproject.contacts.view.IndexBar;
import com.ins.newproject.contacts.view.SideBar;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recycler;
    private ContactAdapter adapter;
    private CustomItemDecoration decoration;
    private IndexBar index_bar;
    private List<ContactBean> results = new ArrayList<>();
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
        index_bar = (IndexBar) findViewById(R.id.index_bar);
        recycler = (RecyclerView) findViewById(R.id.rl_recycle_view);
        findViewById(R.id.btn_right).setOnClickListener(this);
        FocusUtil.focusToTop(recycler);
    }

    public void initCtrl() {
        adapter = new ContactAdapter(this);
        recycler.setLayoutManager(layoutManager = new LinearLayoutManager(this));
        recycler.addItemDecoration(decoration = new CustomItemDecoration(this));
        recycler.setAdapter(adapter);

        index_bar.addOnIndexChangeListener(new SideBar.OnIndexChangeListener() {
            @Override
            public void onIndexChanged(float centerY, String tag, int position) {
                if (TextUtils.isEmpty(tag) || results.size() <= 0) return;
                for (int i = 0; i < results.size(); i++) {
                    if (tag.equals(results.get(i).getIndexTag())) {
                        layoutManager.scrollToPositionWithOffset(i, 0);
//                        layoutManager.scrollToPosition(i);
                        return;
                    }
                }
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
                    ContactBean bean = new ContactBean();
                    bean.setName(name);
                    results.add(bean);
                }
                //对数据源进行排序
                CommonUtil.sortData(results);
                //返回一个包含所有Tag字母在内的字符串并赋值给tagsStr
                String tagsStr = CommonUtil.getTags(results);
                index_bar.setIndexStr(tagsStr);
                decoration.setDatas(results, tagsStr);
                adapter.getResults().clear();
                adapter.getResults().addAll(results);
                adapter.notifyDataSetChanged();
            }
        }, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                add();
                break;
        }
    }

    public void add() {
        ContactBean bean = new ContactBean();
        bean.setName("安其拉666");
        results.add(bean);
        //对数据源进行排序
        CommonUtil.sortData(results);
        //返回一个包含所有Tag字母在内的字符串并赋值给tagsStr
        String tagsStr = CommonUtil.getTags(results);
        index_bar.setIndexStr(tagsStr);
        decoration.setDatas(results, tagsStr);
        //这里写死位置1 只是为了看动画效果
        adapter.add(bean, 1);
    }
}
