package com.pawan.cmpe277;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MineSweeperActivity extends AppCompatActivity {
    private MineSweeperFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        fragment = (MineSweeperFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    }
}