package com.hwp.test.shudu;

import com.hwp.test.shudu.exception.SudokuValueException;
import com.hwp.test.shudu.model.SudokuCell;
import com.hwp.test.shudu.model.SudokuColumn;
import com.hwp.test.shudu.model.SudokuLine;
import com.hwp.test.shudu.model.SudokuPalace;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 写这个数独计算,目的不是为了计算数独答案, 而是想写一套优雅的代码
 * 1. 代码量尽可能少
 * 2. 考虑性能,计算速度尽可能快
 * 3. 有学习价值,值得回味
 * TODO 优化路线
 * 1. 如果初始化的时候发现有什么格子是只能填一个数的, 那就把这个数字改为基本数, 然后被这个数字影响的其他格子或许又会有新的基本数出现,
 *    循环刷, 直到没有新基本数出现为止才算是初始化完成
 * 2.
 *
 * @author weiping.he
 */
public class Sudoku {

    public static int[][] sudokuBefore = new int[9][9];
    public static int[][] sudokuAfter = new int[9][9];
    public static long lowTime;
    public static int editNum = 0;
    public static int recallNum = 0;

    public static void main(String[] args) throws Exception {
        // 从文件读取已知的数独信息
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("D:\\workspace" +
                "\\untitled\\sudoku_before.txt"))))) {
            for (int i = 0; i < 9; i++) {
                String oneLine = br.readLine();
                String[] split = oneLine.split("_", 10);
                for (int n = 0; n < split.length; n++) {
                    int num = Integer.parseInt(split[n]);
                    sudokuBefore[i][n] = num;
                }
            }
        }
        System.out.println("---------开始计算");
        lowTime = System.currentTimeMillis();
        sudokuSolve();
        System.out.println("---------计算完成, 累计耗时(ms):" + (System.currentTimeMillis() - lowTime));
        System.out.println(String.format("---------共计尝试填入:%s次. 回退:%s次", editNum, recallNum));
        // 输出过程
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("D:\\workspace" +
                "\\untitled\\sudoku_after" + System.currentTimeMillis() + ".txt"))));) {
            for (int[] intArr : sudokuAfter) {
                String str = "";
                for (int i : intArr) {
                    str += i + "_";
                }
                bw.write(str);
                bw.newLine();
            }
        }
    }

    /**
     * 求解数独的主体方法
     *
     * @throws Exception 无解时抛出错误
     */
    public static void sudokuSolve() throws Exception {
        // 构建一个模型, 存储每一个格子的信息, 以及每一行,每一列,每一宫
        SudokuOverall overall = build();
        System.out.println("-------构建对象完成, 累计耗时(ms):" + (System.currentTimeMillis() - lowTime));
        // 计算每个格子的可能出现数, 即初始化
        init(overall);
        System.out.println("-------初始化完成, 累计耗时(ms):" + (System.currentTimeMillis() - lowTime));
        // 求解
        int result = getResult(0, overall);
        if (result == 1) {
            // 整理返回结果并返回
            List<SudokuLine> sudokuLineList = overall.getSudokuLineList();
            for (int i = 0; i < sudokuLineList.size(); i++) {
                List<SudokuCell> sudokuCells = sudokuLineList.get(i).getSudokuCells();
                for (int j = 0; j < sudokuCells.size(); j++) {
                    sudokuAfter[i][j] = sudokuCells.get(j).getValue();
                }
            }
        } else {
            // 无解
            throw new Exception("此题无解!!!");
        }
    }

    /**
     * 构建计算数独需要对象结构
     *
     * @return 返回的是数独全局对象
     */
    public static SudokuOverall build() {
        SudokuOverall overall = new SudokuOverall();
        // 生成所有行对象, 列对象, 宫对象
        List<SudokuLine> sudokuLineList = new ArrayList<>(9);
        List<SudokuColumn> sudokuColumnList = new ArrayList<>(9);
        List<SudokuPalace> sudokuPalaceList = new ArrayList<>(9);
        overall.setSudokuLineList(sudokuLineList);
        overall.setSudokuColumnList(sudokuColumnList);
        overall.setSudokuPalaceList(sudokuPalaceList);
        overall.setTryCellList(new ArrayList<SudokuCell>(81));
        for (int i = 0; i < 9; i++) {
            sudokuLineList.add(new SudokuLine(i + 1, new ArrayList<SudokuCell>(9)));
            sudokuColumnList.add(new SudokuColumn(i + 1, new ArrayList<SudokuCell>(9)));
            sudokuPalaceList.add(new SudokuPalace(i + 1, new ArrayList<SudokuCell>(9)));
        }
        // 一个个生成单元格对象, 然后放到对应的位置上
        for (int i = 0; i < 9; i++) {
            for (int n = 0; n < 9; n++) {
                SudokuCell cell = new SudokuCell(i + 1, n + 1, sudokuBefore[i][n]);
                // 放到对应的行内
                sudokuLineList.get(i).putCell(cell);
                // 放到对应的列内
                sudokuColumnList.get(n).putCell(cell);
                // 放到对应的宫内
                sudokuPalaceList.get(SudokuCell.queryPalace(i + 1, n + 1) - 1).putCell(cell);
                if (sudokuBefore[i][n] == 0) {
                    // 放到可填入的对象内
                    overall.getTryCellList().add(cell);
                }
            }
        }
        return overall;
    }

    /**
     * 生成一个1-9的List
     *
     * @return
     */
    public static List<Integer> getAllNumList() {
        List<Integer> list = new ArrayList<>(9);
        for (int i = 1; i <= 9; i++) {
            list.add(i);
        }
        return list;
    }

    /**
     * 初始化数独全局对象, 计算每个格子的可填入数
     *
     * @param overall
     */
    public static void init(SudokuOverall overall) {
        // 从第一个可填入格子开始, 计算可能出现数
        for (int i = 0; i < overall.getTryCellList().size(); i++) {
            SudokuCell sudokuCell = overall.getTryCellList().get(i);
            List<Integer> certainNumListLine =
                    overall.getSudokuLineList().get(sudokuCell.getLineNum() - 1).getCertainNumList();
            List<Integer> certainNumListColumn =
                    overall.getSudokuColumnList().get(sudokuCell.getColumnNum() - 1).getCertainNumList();
            List<Integer> certainNumListPalace =
                    overall.getSudokuPalaceList().get(sudokuCell.getPalaceNum() - 1).getCertainNumList();
            // 获得一个1-9的集合, 并且判断不存在于任何一个group中的数字
            List<Integer> certainTryNumber = getAllNumList().stream()
                    .filter(num -> !certainNumListLine.contains(num) && !certainNumListColumn.contains(num) && !certainNumListPalace.contains(num))
                    .collect(Collectors.toList());
            if (certainTryNumber.size() == 1) {
                System.out.println("可填入数字只有一个的单元格是" + sudokuCell.getLineNum() + sudokuCell.getColumnNum());
            }
            // 把这个list放到该单元格中的可尝试list
            sudokuCell.setCertainTryNumber(certainTryNumber);
            sudokuCell.setDeductMap(new HashMap<Integer, SudokuCell>(9));
        }
    }

    /**
     * 计算数独的递归方法
     *
     * @param cellIndex 即将填入第几个单元格(可填入单元格集合)
     * @param overall   全局对象
     * @return 0:失败,1:成功
     */
    public static int getResult(int cellIndex, SudokuOverall overall) {
        // 拿到这个单元格
        SudokuCell sudokuCell = overall.getTryCellList().get(cellIndex);
        List<Integer> certainTryNumber = sudokuCell.getCertainTryNumber();
        Map<Integer, SudokuCell> deductMap = sudokuCell.getDeductMap();
        // 如果当前单元格的怀疑可尝试数组为空,则直接返回给调用者失败:0
        if (certainTryNumber.size() == deductMap.size()) {
            return 0;
        }
        // 遍历这个单元格的可尝试数组
        for (int i = 0; i < certainTryNumber.size(); i++) {
            Integer tryNum = certainTryNumber.get(i);
            // 如果在扣除map中含有该数字就结束本次循环
            if (deductMap.containsKey(tryNum)) {
                continue;
            }
            // 尝试填入某一个数字
            sudokuCell.setValue(tryNum);
            System.out.println(String.format("-------尝试填入第%s个格子, 数值:%s", cellIndex + 1, tryNum));
            editNum++;
            // 如果是最后一个单元格, 就在这里返回成功, 这句话是这个递归方法的边界
            if (cellIndex + 1 == overall.getTryCellList().size()) {
                return 1;
            }

            try {
                // 广播该事件, 调用全局方法内的广播方法
                overall.broadcast(sudokuCell);
            } catch (SudokuValueException e) {
                // 广播过程中如果从其他单元格报错过来, 说明填入这个数字会导致后面的格子出现问题, 这个数字不能填, 执行撤回操作
                sudokuCell.setValue(0);
                System.out.println(String.format("------------------尝试填入第%s个格子失败!!执行回退", cellIndex + 1));
                recallNum++;
                overall.revocation(sudokuCell, tryNum);
                continue;
            }

            // 填入后递归调用下一个
            if (getResult(cellIndex + 1, overall) == 0) {
                // 如果调用后返回错误, 执行撤回操作, 把上面尝试的数字撤回, 然后再尝试下一个
                sudokuCell.setValue(0);
                System.out.println(String.format("------------------尝试填入第%s个格子失败!!执行回退", cellIndex + 1));
                recallNum++;
                overall.revocation(sudokuCell, tryNum);
            } else {
                // 返回成功的时候, 表示所有下面的格子都尝试完了, 没问题, 这个时候就会返回给调用者成功:1
                return 1;
            }
        }
        // 如果可以尝试的数字都尝试完了, 还没能得到成功结果, 就返回失败:0
        return 0;
    }

}
