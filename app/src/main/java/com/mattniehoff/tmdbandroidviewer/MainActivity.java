package com.mattniehoff.tmdbandroidviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String[] testData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);

        recyclerView.setHasFixedSize(true);

        // Set layout manager
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        populateMovieGrid();
    }

    private void populateMovieGrid() {
        testData = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        adapter = new MovieAdapter(testData);
        recyclerView.setAdapter(adapter);
    }
}
