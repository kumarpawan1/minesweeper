package com.pawan.cmpe277;

import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.HashSet;
import java.util.Set;


public class MineSweeperFragment extends Fragment implements View.OnClickListener {
    private Button mNewButton;
    private LinearLayout mMineFieldLayout;
    private BoardUpdater game;
    private MineBoard[][] mineBoards;


    private Set<Point> reveled;
    private CellClickListener cellClickListener;

    public MineSweeperFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState == null) {
                    //Do Nothing
        } else {
            setRetainInstance(true);
        }
        return inflater.inflate(R.layout.game_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initGame(new BoardUpdater());
    }

    private void initView(View view) {
        mMineFieldLayout = (LinearLayout) view.findViewById(R.id.ll_mine_field);
        mNewButton = (Button) view.findViewById(R.id.btNew);
        mNewButton.setOnClickListener(this);
    }

    private void initGame(BoardUpdater game) {
        this.game = game;
        mineBoards = new MineBoard[game.getTotalColumns()][game.getTotalRows()];
        cellClickListener = new CellClickListener();
        reveled = new HashSet<>();
        renderMineField();
    }

    private void initGame(BoardUpdater game, boolean[][] state, boolean enable) {
        this.game = game;
        this.mineBoards = new MineBoard[game.getTotalColumns()][game.getTotalRows()];
        cellClickListener = new CellClickListener();
        reveled = new HashSet<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btNew:
                initGame(new BoardUpdater());
                break;
        }
    }


    private void renderMineField() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);

        int cellSize = (screenSize.x - 2 * getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin))
                / (game.getTotalRows());

        mMineFieldLayout.removeAllViews();

        for (int i = 0; i < game.getTotalColumns(); i++) {
            LinearLayout row = new LinearLayout(getActivity());
            row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            row.setGravity(Gravity.CENTER);
            row.setHorizontalGravity(LinearLayout.HORIZONTAL);
            for (int j = 0; j < game.getTotalRows(); j++) {
                mineBoards[i][j] = new MineBoard(getActivity());
                mineBoards[i][j].setPositionAndValue(i, j, game.getMineNumberAt(i, j));
                mineBoards[i][j].setLayoutParams(new RelativeLayout.LayoutParams(cellSize, cellSize));
                mineBoards[i][j].setOnClickListener(cellClickListener);
                row.addView(mineBoards[i][j]);
            }
            mMineFieldLayout.addView(row);
        }
    }

    private void renderMineField(boolean[][] state) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);
        int cellSize = (screenSize.x - 2 * getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin))
                / (game.getTotalRows());

        mMineFieldLayout.removeAllViews();

        for (int i = 0; i < game.getTotalColumns(); i++) {
            LinearLayout row = new LinearLayout(getActivity());
            row.setHorizontalGravity(LinearLayout.HORIZONTAL);
            for (int j = 0; j < game.getTotalRows(); j++) {
                mineBoards[i][j] = new MineBoard(getActivity());
                mineBoards[i][j].setPositionAndValue(i, j, game.getMineNumberAt(i, j));
                mineBoards[i][j].setLayoutParams(new RelativeLayout.LayoutParams(cellSize, cellSize));
                mineBoards[i][j].setOnClickListener(cellClickListener);
                if (state[i][j]) {
                    mineBoards[i][j].setReveled(true);
                }
                row.addView(mineBoards[i][j]);
            }
            mMineFieldLayout.addView(row);
        }
    }

    class CellClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            MineBoard cell = (MineBoard) v;
            if (cell.isReveled()) {
                return;
            }

            cell.setReveled(true);
            int row = cell.getRow();
            int column = cell.getColumn();
            if (game.getMineNumberAt(row, column) == 0) {
                reveled.add(new Point(row, column));
                findNeighbour(row, column);
            } else if (game.getMineNumberAt(row, column) == BoardUpdater.MINE) {
                showGameOverDialog();
            }
        }
    }

    private void showGameOverDialog() {
        ConfirmDialog.show(getActivity(), R.string.game_over_title, R.string.game_over_message,
                R.string.game_over_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mShowResults();
                    }
                });
    }



    private void mShowResults() {
        for (int i = 0; i < game.getTotalColumns(); i++) {
            for (int j = 0; j < game.getTotalRows(); j++) {
                mineBoards[i][j].setReveled(true);
            }
        }

        ConfirmDialog.show(getActivity(), R.string.game_over_title, R.string.new_game_message,
                R.string.new_game, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        initGame(new BoardUpdater());
                    }
                });
    }


    private void findNeighbour(int row, int column) {
        for (int i = 0; i < BoardUpdater.mNeighbors.length; i++) {
            int r = row + BoardUpdater.mNeighbors[i][0];
            int c = column + BoardUpdater.mNeighbors[i][1];
            if (r >= 0 && r < game.getTotalColumns() && c >= 0 && c < game.getTotalRows()) {

                mineBoards[r][c].setReveled(true);
                if (game.getMineNumberAt(r, c) == 0 && reveled.add(new Point(r, c))) {
                    findNeighbour(r, c);
                }
            }
        }
    }



}

