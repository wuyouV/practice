package com.hwp.test.shudu.model;

import java.util.List;

/**
 * 数独的行
 *
 * @author weiping.he
 */
public class SudokuLine extends SudokuGroup {

    /**
     * 这是第几行
     */
    private int lineNum;

    public SudokuLine(int lineNum, List<SudokuCell> sudokuCells) {
        super(sudokuCells);
        this.lineNum = lineNum;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }
}
