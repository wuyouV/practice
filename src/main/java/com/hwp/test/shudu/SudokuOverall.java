package com.hwp.test.shudu;

import com.hwp.test.shudu.exception.SudokuValueException;
import com.hwp.test.shudu.model.SudokuCell;
import com.hwp.test.shudu.model.SudokuColumn;
import com.hwp.test.shudu.model.SudokuLine;
import com.hwp.test.shudu.model.SudokuPalace;

import java.util.List;

/**
 * 包含数独对象的全局信息, 包含部分的计算逻辑
 *
 * @author weiping.he
 */
public class SudokuOverall {

    /**
     * 所有行的集合
     */
    private List<SudokuLine> sudokuLineList;

    /**
     * 所有列的集合
     */
    private List<SudokuColumn> sudokuColumnList;

    /**
     * 所有宫的集合
     */
    private List<SudokuPalace> sudokuPalaceList;

    /**
     * 所有可填入单元格的集合
     */
    private List<SudokuCell> tryCellList;

    /**
     * 广播该单元格的新值
     *
     * @param sudokuCell
     */
    public void broadcast(SudokuCell sudokuCell) throws SudokuValueException {
        sudokuLineList.get(sudokuCell.getLineNum() - 1).broadcast(sudokuCell);
        sudokuColumnList.get(sudokuCell.getColumnNum() - 1).broadcast(sudokuCell);
        sudokuPalaceList.get(sudokuCell.getPalaceNum() - 1).broadcast(sudokuCell);
    }

    public void revocation(SudokuCell sudokuCell, Integer tryNum) {
        sudokuLineList.get(sudokuCell.getLineNum() - 1).revocation(sudokuCell, tryNum);
        sudokuColumnList.get(sudokuCell.getColumnNum() - 1).revocation(sudokuCell, tryNum);
        sudokuPalaceList.get(sudokuCell.getPalaceNum() - 1).revocation(sudokuCell, tryNum);
    }

    public List<SudokuLine> getSudokuLineList() {
        return sudokuLineList;
    }

    public void setSudokuLineList(List<SudokuLine> sudokuLineList) {
        this.sudokuLineList = sudokuLineList;
    }

    public List<SudokuColumn> getSudokuColumnList() {
        return sudokuColumnList;
    }

    public void setSudokuColumnList(List<SudokuColumn> sudokuColumnList) {
        this.sudokuColumnList = sudokuColumnList;
    }

    public List<SudokuPalace> getSudokuPalaceList() {
        return sudokuPalaceList;
    }

    public void setSudokuPalaceList(List<SudokuPalace> sudokuPalaceList) {
        this.sudokuPalaceList = sudokuPalaceList;
    }

    public List<SudokuCell> getTryCellList() {
        return tryCellList;
    }

    public void setTryCellList(List<SudokuCell> tryCellList) {
        this.tryCellList = tryCellList;
    }

}
