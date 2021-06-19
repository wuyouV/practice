package com.hwp.test.shudu.model;

import com.hwp.test.shudu.exception.SudokuValueException;

import java.util.List;
import java.util.Map;

/**
 * 数独格子的类
 *
 * @author weiping.he
 */
public class SudokuCell {

    /**
     * 第几行
     */
    private int lineNum;

    /**
     * 第几列
     */
    private int columnNum;

    /**
     * 第几宫(根据行数和列数算出来的)
     */
    private int palaceNum;

    /**
     * 是否是决定值(题目中给定的)
     */
    private boolean certain;

    /**
     * 数值, 0代表无数值
     */
    private int value;

    /**
     * 可尝试数组, 初始化后不可变更
     */
    private List<Integer> certainTryNumber;

    /**
     * 当前数组因为前面格子的尝试而被扣掉的可能数字, 以及是哪个格子把这个格子的数字扣掉了(因为遍历顺序的问题, 所以这样这样做)
     */
    private Map<Integer, SudokuCell> deductMap;

    public SudokuCell(int lineNum, int columnNum, int value) {
        this.lineNum = lineNum;
        this.columnNum = columnNum;
        this.palaceNum = queryPalace(lineNum, columnNum);
        this.certain = value > 0;
        this.value = value;
    }

    /**
     * 根据行和列, 计算出宫数
     *
     * @param lineNum
     * @param columnNum
     * @return
     */
    public static int queryPalace(int lineNum, int columnNum) {
        //  传入行号和列号, 1-9, 返回是第几个宫
        return (columnNum % 3 == 0 ? columnNum / 3 : columnNum / 3 + 1) + ((lineNum % 3 == 0 ? lineNum / 3 - 1 :
                lineNum / 3)) * 3;
    }

    /**
     * 广播该单元格的新值
     *
     * @param sudokuCell
     */
    public void broadcast(SudokuCell sudokuCell) throws SudokuValueException {
        // 如果是基本单元格, 直接结束, 基本单元格不接受任何广播
        if (certain) {
            return;
        }
        if (sudokuCell == this) {
            // 检查发起广播的单元格是否是自己, 是的话, 扣掉可能数字就返回
            deductMap.put(sudokuCell.getValue(), sudokuCell);
        } else {
            // 检查可填入数字中有个数字, 并且检查deductMap是否有这个数字, 没有就加一个
            if (certainTryNumber.contains(sudokuCell.getValue()) && !deductMap.containsKey(sudokuCell.getValue())) {
                // 注意!!!!!!!如果this单元格还没有数值, 并且可尝试数组长度 == 扣除map的长度, 爆出错误!!!!!!
                deductMap.put(sudokuCell.getValue(), sudokuCell);
                if (this.value == 0 && this.certainTryNumber.size() == this.deductMap.size()) {
                    throw new SudokuValueException();
                }
            }
        }
    }

    /**
     * 撤回数值的广播
     *
     * @param sudokuCell 引发撤回的单元格
     * @param tryNum     撤回数值
     */
    public void revocation(SudokuCell sudokuCell, Integer tryNum) {
        // 如果是基本单元格, 直接结束, 基本单元格不接受任何广播
        if (certain) {
            return;
        }
        // 判断deductMap内是否有这个数字
        if (deductMap.containsKey(tryNum)) {
            // 有, 就判断是否是因为这个单元格添加进来的, 如果是, 那就去掉
            if (deductMap.get(tryNum) == sudokuCell) {
                deductMap.remove(tryNum);
            }
        }
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

    public Map<Integer, SudokuCell> getDeductMap() {
        return deductMap;
    }

    public void setDeductMap(Map<Integer, SudokuCell> deductMap) {
        this.deductMap = deductMap;
    }

    public int getPalaceNum() {
        return palaceNum;
    }

    public void setPalaceNum(int palaceNum) {
        this.palaceNum = palaceNum;
    }

    public boolean isCertain() {
        return certain;
    }

    public void setCertain(boolean certain) {
        this.certain = certain;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Integer> getCertainTryNumber() {
        return certainTryNumber;
    }

    public void setCertainTryNumber(List<Integer> certainTryNumber) {
        this.certainTryNumber = certainTryNumber;
    }

}
