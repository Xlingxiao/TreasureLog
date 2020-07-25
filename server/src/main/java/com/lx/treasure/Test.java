package com.lx.treasure;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Lx
 * @Date: 2020/7/18 23:22
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("A", "qweqwe");
        System.out.println(map);
        changeMap(map);
        System.out.println(map);
    }

    static void changeMap(Map<String, String> map) {
        Map<String, String> hashMap = new HashMap<>();
        map = hashMap;
        System.out.println(map);
    }
}
