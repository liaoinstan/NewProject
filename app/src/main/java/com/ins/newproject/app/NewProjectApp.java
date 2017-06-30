package com.ins.newproject.app;

import android.app.Application;

import com.ins.common.net.NetApi;
import com.ins.common.utils.App;
import com.ins.common.utils.L;
import com.ins.domain.launcher.DomainLauncher;
import com.ins.newproject.BuildConfig;

public class NewProjectApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initSetting();
        initLauncher();
    }

    private void initSetting() {
        App.saveApplication(this);
        L.setDEBUG(BuildConfig.DEBUG);
    }

    private void initLauncher() {
        DomainLauncher.getInstance().setSettingChangeCallback(new DomainLauncher.SettingChangeCallback() {
            @Override
            public void onDomainChange(String domain) {
                NetApi.setBaseUrl("http://" + domain + "/");
            }
        });
        DomainLauncher.getInstance()
                .addDomain("192.168.118.206:8080", "(Web开发服务器)")
                .addDomain("192.168.1.166:8080", "(开发服务器：谢启谋)")
                .addDomain("192.168.118.110:8080", "(测试服务器)")
                .addDomain("139.129.111.76:8102", "(远程测试服务器)")
                .addDomain("tiger.magic-beans.cn", "(远程测试服务器)");
    }
}
