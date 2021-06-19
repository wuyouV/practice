package com.hwp.test.genshin;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GenshinUpPool {

    private final static int MAX = 90;
    private final static int MAX_UP = 73;
    private final static BigDecimal BASE_PROB = new BigDecimal("0.006");// 1~73
    private final static BigDecimal ADD_PROB = new BigDecimal("0.06");// 74~90

    public static void main(String[] args) {
        for (int i = 1; i <= 90; i++) {
            String prob1 = cal(i).setScale(3, RoundingMode.CEILING).toPlainString();
            String prob2 = calculateProbability(i).setScale(10, RoundingMode.CEILING).toPlainString();
            System.out.println(String.format("第%s抽的概率是:%s, %s抽没五星概率:%s", i, prob1, i, prob2));
        }
    }

    public static BigDecimal cal(int drawNumber) {
        BigDecimal get = BASE_PROB;
        if (drawNumber > MAX_UP) {
            get = get.add(ADD_PROB.multiply(new BigDecimal(drawNumber - MAX_UP)));
        }
        return get;
    }

    /**
     * 计算不获得5*的概率
     *
     * @param drawNumber
     * @return
     */
    public static BigDecimal calculateProbability(int drawNumber) {
        if (drawNumber >= MAX) {
            return BigDecimal.ZERO;
        }
        BigDecimal prob = BigDecimal.ZERO;
        for (int i = 1; i <= drawNumber; i++) {
            BigDecimal get = cal(i);
            BigDecimal notGet = BigDecimal.ONE.subtract(get);
            if (prob.equals(BigDecimal.ZERO)) {
                prob = notGet;
            } else {
                prob = prob.multiply(notGet);
            }
        }
        return prob;
    }
}
