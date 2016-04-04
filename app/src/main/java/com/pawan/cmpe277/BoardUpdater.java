package com.pawan.cmpe277;

import java.util.Random;


public class BoardUpdater {

    private int mTotalColumns = 12;
    private int mTotalRows = 8;
    private int mTotalMines = 30;
    public static final int MINE = -1;

    private int[][] mMineboard;

    public static int[][] mNeighbors = new int[][]{
            {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    public BoardUpdater() {
        initGameBoard();
    }

    public int getMineNumberAt(int row, int column) {
        return mMineboard[row][column];
    }

    public int getTotalRows() {
        return mTotalRows;
    }

    public int getTotalColumns() {
        return mTotalColumns;
    }

    private void initGameBoard() {
        mMineboard = new int[mTotalColumns][mTotalRows];
        mRandomMine();
    }

    private void mRandomMine() {
        Random random = new Random(System.currentTimeMillis());
        int count = 0;
        int row = 0;
        int column = 0;
        while (count < mTotalMines) {
            row = random.nextInt(mTotalColumns);
            column = random.nextInt(mTotalRows);
            if (mMineboard[row][column] != MINE) {
                mMineboard[row][column] = MINE;
                mUpdateMinePosition(row, column);
                count++;
            }
        }
    }

    private void mUpdateMinePosition(int row, int column) {
        for (int i = 0; i < 8; i++) {
            int r = row + mNeighbors[i][0];
            int c = column + mNeighbors[i][1];
            if (r >= 0 && r < mTotalColumns && c >= 0 && c < mTotalRows && mMineboard[r][c] != MINE) {
                mMineboard[r][c]++;
            }
        }
    }
}
