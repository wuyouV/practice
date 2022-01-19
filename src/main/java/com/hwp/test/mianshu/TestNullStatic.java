package com.hwp.test.mianshu;

public class TestNullStatic {

    public static void main(String[] args) {
        Integer i = null;
        // 是否能打印出值? 还是会报错终止?
        System.out.println(i.toBinaryString(42));
    }

}
