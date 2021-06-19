package com.hwp.test.effectivejava.tag14;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * 小节练习, 在这个武器类内实现Comparable
 *
 * @author weiping.he
 */
class Weapon implements Comparable<Weapon> {

    /**
     * 等级越高, 越厉害
     */
    private final int level;

    /**
     * 等级一样时, 制造年份越新越厉害
     */
    private final int makeYear;

    /**
     * 写一个稍微复杂的逻辑, 练习基于Comparable的排序
     * 这个方法原本是用减法计算出一个整数的,但这是非常危险的做法,很容易造成整数溢出,正确的做法是使用Java包装类提供的静态对比方法
     *
     * @param weapon 被比较的实例
     * @return 返回比较结果
     */
    @Override
    public int compareTo(Weapon weapon) {
        int compare = Integer.compare(level, weapon.getLevel());
        if (compare == 0) {
            compare = Integer.compare(makeYear, weapon.getMakeYear());
        }
        return compare;
    }

    public static void main(String[] args) {
        Weapon[] list = new Weapon[4];
        list[0] = new Weapon(2, 2012);
        list[1] = new Weapon(1, 2010);
        list[2] = new Weapon(1, 2015);
        list[3] = new Weapon(2, 2021);
        for (Weapon weapon : list) {
            System.out.println(weapon);
        }
        Arrays.sort(list);
        System.out.println("进行了排序");
        for (Weapon weapon : list) {
            System.out.println(weapon);
        }

        Set<Weapon> weaponSet = new TreeSet<>();
        weaponSet.add(list[3]);
        weaponSet.add(list[1]);
        weaponSet.add(list[0]);
        weaponSet.add(list[2]);
        System.out.println(weaponSet);
    }

    /**
     * 为这个类添加equals实现是因为, 对于实现了Comparable的类, 排序相等的对象, equals建议返回true
     * 该建议来自Java之父James Gosling的书[Effective Java]
     * @param o 被比较的实例
     * @return 返回是否相同
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Weapon weapon = (Weapon) o;
        return level == weapon.level && makeYear == weapon.makeYear;
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, makeYear);
    }

    public Weapon(int level, int makeYear) {
        this.level = level;
        this.makeYear = makeYear;
    }

    public int getLevel() {
        return level;
    }

    public int getMakeYear() {
        return makeYear;
    }

    @Override
    public String toString() {
        return "Weapon{" +
                "level=" + level +
                ", makeYear=" + makeYear +
                '}';
    }
}
