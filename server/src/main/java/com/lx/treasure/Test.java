package com.lx.treasure;

import lombok.Data;

import java.util.*;

/**
 * @Auther: Lx
 * @Date: 2020/7/18 23:22
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        A[] strings = new A[10];
        System.out.println(strings);
        List<A> stringList = Arrays.asList(strings);
        System.out.println(stringList);
        stringList.set(1, new A("AAA", 23423));
        System.out.println(stringList);

    }

    @Data
    static
    class A {
        String s;
        int i;

        public A(String s, int i) {
            this.s = s;
            this.i = i;
        }
    }
    static void changeMap(Map<String, String> map) {
        Map<String, String> hashMap = new HashMap<>();
        map = hashMap;
        System.out.println(map);
    }
}
