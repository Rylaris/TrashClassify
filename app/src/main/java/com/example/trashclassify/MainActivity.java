package com.example.trashclassify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.trashclassify.util.Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Util.readCSV(getAssets());
    }
}
