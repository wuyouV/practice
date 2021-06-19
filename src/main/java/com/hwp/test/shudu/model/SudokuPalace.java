package com.hwp.test.shudu.model;

import java.util.List;

/**
 * 数独的宫
 *
 * @author weiping.he
 */
public class SudokuPalace extends SudokuGroup{

    /**
     * 这是第几宫
     */
    private int palaceNum;

    public SudokuPalace(int palaceNum, List<SudokuCell> sudokuCells) {
        super(sudokuCells);
        this.palaceNum = palaceNum;
    }

    public int getPalaceNum() {
        return palaceNum;
    }

    public void setPalaceNum(int palaceNum) {
        this.palaceNum = palaceNum;
    }
}
