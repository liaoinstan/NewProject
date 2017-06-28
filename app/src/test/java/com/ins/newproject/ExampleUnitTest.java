package com.ins.newproject;


import com.ins.common.utils.UrlUtil;

import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String url = "http://www.baidu.com/api/map/loc?id=1&name=ins";
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("aa", "11");
        map.put("bb", "22");
        map.put("id", "33");
        String id = UrlUtil.addParams(url, map);
        System.out.println(id);
    }
}