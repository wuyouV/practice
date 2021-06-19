package com.hwp.test.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestListClone {

    public static void main(String[] args) {
        List<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(4);
        a.add(5);
        List<Integer> b = a.stream().collect(Collectors.toList());
        a.add(6);
        System.out.println(a);
        System.out.println(b);
    }
}
