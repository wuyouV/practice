package com.hwp.test.shudu.model;

import com.hwp.test.shudu.exception.SudokuValueException;

import java.util.ArrayList;
import java.util.List;

/**
 * 数独的组, 统一行,列,宫的抽象类
 *
 * @author weiping.he
 */
public abstract class SudokuGroup {

    /**
     * 数独组的9个子单元
     */
    private List<SudokuCell> sudokuCells;

    /**
     * 该组中已经存在的基本数的集合
     */
    private List<Integer> certainNumList;

    public SudokuGroup(List<SudokuCell> sudokuCells) {
        this.sudokuCells = sudokuCells;
        this.certainNumList = new ArrayList<Integer>();
    }

    /**
     * 向该组中添加一个单元格, 如果该单元格是基本单元格, 则添加它的值到certainNumList中
     */
    public void putCell(SudokuCell cell) {
        sudokuCells.add(cell);
        if (cell.isCertain()) {
            certainNumList.add(cell.getValue());
        }
    }

    /**
     * 广播该单元格的新值
     *
     * @param sudokuCell
     */
    public void broadcast(SudokuCell sudokuCell) throws SudokuValueException {
        for (SudokuCell cell : sudokuCells) {
            cell.broadcast(sudokuCell);
        }
    }

    /**
     * 广播该单元格的撤回动作
     *
     * @param sudokuCell
     * @param tryNum
     */
    public void revocation(SudokuCell sudokuCell, Integer tryNum) {
        for (SudokuCell cell : sudokuCells) {
            cell.revocation(sudokuCell, tryNum);
        }
    }

    public List<SudokuCell> getSudokuCells() {
        return sudokuCells;
    }

    public void setSudokuCells(List<SudokuCell> sudokuCells) {
        this.sudokuCells = sudokuCells;
    }

    public List<Integer> getCertainNumList() {
        return certainNumList;
    }

    public void setCertainNumList(List<Integer> certainNumList) {
        this.certainNumList = certainNumList;
    }
}
