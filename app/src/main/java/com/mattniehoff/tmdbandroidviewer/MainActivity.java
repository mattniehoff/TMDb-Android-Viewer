package com.mattniehoff.tmdbandroidviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView helloWorldTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helloWorldTextView = (TextView) findViewById(R.id.helloworld_tv);
        String apiKey = ApiKeys.TmdbApiKey;
        if (apiKey != null && !apiKey.isEmpty()) {
            helloWorldTextView.setText(apiKey);
        } else {
            helloWorldTextView.setText("Failed to load API Key.");
        }
    }
}
