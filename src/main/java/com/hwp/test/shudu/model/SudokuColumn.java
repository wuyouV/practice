package com.hwp.test.shudu.model;

import java.util.List;

/**
 * 数独的列
 *
 * @author weiping.he
 */
public class SudokuColumn extends SudokuGroup {

    /**
     * 这是第几列
     */
    private int columnNum;

    public SudokuColumn(int columnNum, List<SudokuCell> sudokuCells) {
        super(sudokuCells);
        this.columnNum = columnNum;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }
}
