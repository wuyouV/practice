package com.hwp.test.genshin;

public class GenshinGold {
    public static boolean goldFlag = false;
    public static int changzhu = 0;
    public static int up = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 1000000; i++) {
            if (goldFlag) {
                goldFlag = false;
                up++;
            } else {
                double random = Math.random();
                if (random > 0.5D) {
                    up++;
                } else {
                    changzhu++;
                    goldFlag = true;
                }
            }
        }
        System.out.println("常驻->" + changzhu);
        System.out.println("UP->" + up);
    }
}
