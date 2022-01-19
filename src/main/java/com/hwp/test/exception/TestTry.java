package com.hwp.test.exception;

/**
 * @author weiping.he
 */
public class TestTry {
    public static void main(String[] args) {
//        int b = 1 / 0;
        try {
            int a = 1 / 0;
        } catch (Exception e) {
            throw e;
        } finally {
            System.out.println("finally");
            return;
        }
    }
}
