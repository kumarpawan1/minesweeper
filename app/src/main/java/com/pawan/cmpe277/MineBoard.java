package com.pawan.cmpe277;

import android.content.Context;
import android.widget.Button;

public class MineBoard extends Button {
    private int row;
    private int column;
    private int mineNumber;
    private boolean reveled;
    private String mineSymbol = "@";

    public MineBoard(Context context) {
        super(context);
    }

    public void setPositionAndValue(int row, int column, int mineNumber) {
        this.row = row;
        this.column = column;
        this.mineNumber = mineNumber;
    }

    public void setReveled(boolean reveled) {
        this.reveled = reveled;
        if (reveled) {
            if (mineNumber == BoardUpdater.MINE) {
                setText(mineSymbol);
                setBackgroundResource(R.drawable.hit);
            } else {
                setText(mineNumber == 0 ? "" : String.valueOf(mineNumber));
                setBackgroundResource(R.drawable.reveal);
            }
        } else {
            setBackgroundResource(R.drawable.hidden);
        }
    }


    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isReveled() {
        return reveled;
    }
}
