package com.gibbon.marqueerecyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.gibbon.marqueerecyclerview.LooperLayoutManager;
import com.gibbon.marqueerecyclerview.MarqueeRecyclerView;

public class MainActivity extends AppCompatActivity {

    private MarqueeRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAutoRun(true);
        recyclerView.setAdapter(new SimpleAdapter());
        LooperLayoutManager layoutManager = new LooperLayoutManager();
        layoutManager.setLooperEnable(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recyclerView.stop();
    }
}
