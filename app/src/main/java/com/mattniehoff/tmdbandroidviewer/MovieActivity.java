package com.mattniehoff.tmdbandroidviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattniehoff.tmdbandroidviewer.model.Result;
import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {

    public static final String RESULT_EXTRA = "result_extra";

    private ImageView moviePosterImageView;
    private TextView titleTextView;
    private TextView releaseDateTextView;
    private TextView voteAverageTextView;
    private TextView plotTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        moviePosterImageView = findViewById(R.id.movie_poster_iv);
        titleTextView = findViewById(R.id.title_tv);
        releaseDateTextView = findViewById(R.id.release_date_tv);
        voteAverageTextView = findViewById(R.id.rating_tv);
        plotTextView = findViewById(R.id.synopsis_tv);

        Bundle data = getIntent().getExtras();
        Result movieResult = data.getParcelable(RESULT_EXTRA);

        Picasso.get().load(movieResult.getMoviePosterUrl()).into(moviePosterImageView);
        titleTextView.setText(movieResult.getTitle());
        releaseDateTextView.setText(movieResult.getReleaseDate());
        voteAverageTextView.setText(Double.toString(movieResult.getVoteAverage()));
        plotTextView.setText(movieResult.getOverview());
    }
}
