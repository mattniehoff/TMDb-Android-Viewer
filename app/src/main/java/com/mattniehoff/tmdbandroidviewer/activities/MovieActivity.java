package com.mattniehoff.tmdbandroidviewer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mattniehoff.tmdbandroidviewer.R;
import com.mattniehoff.tmdbandroidviewer.adapters.ReviewAdapter;
import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseMovieResult;
import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseReviewsResponse;
import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseReviewsResult;
import com.mattniehoff.tmdbandroidviewer.network.MovieDatabaseNetworkUtils;
import com.mattniehoff.tmdbandroidviewer.network.MoviesDatabaseClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mattniehoff.tmdbandroidviewer.BuildConfig.TMDB_API_KEY;

public class MovieActivity extends AppCompatActivity {

    public static final String RESULT_EXTRA = "result_extra";

    private ImageView moviePosterImageView;
    private TextView titleTextView;
    private TextView releaseDateTextView;
    private TextView voteAverageTextView;
    private TextView plotTextView;

    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;
    private LinearLayoutManager reviewLayoutManager;

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
        TheMovieDatabaseMovieResult movieTheMovieDatabaseMovieResult = data.getParcelable(RESULT_EXTRA);

        Picasso.get().load(movieTheMovieDatabaseMovieResult.getMoviePosterUrl()).into(moviePosterImageView);
        titleTextView.setText(movieTheMovieDatabaseMovieResult.getTitle());
        releaseDateTextView.setText(movieTheMovieDatabaseMovieResult.getReleaseDate());
        voteAverageTextView.setText(Double.toString(movieTheMovieDatabaseMovieResult.getVoteAverage()));
        plotTextView.setText(movieTheMovieDatabaseMovieResult.getOverview());

        configureAndPopulateReviewsRecyclerView(movieTheMovieDatabaseMovieResult.getId());
    }

    private void configureAndPopulateReviewsRecyclerView(int movieId) {
        reviewRecyclerView = (RecyclerView) findViewById(R.id.reviews_recycler_view);
        reviewRecyclerView.setHasFixedSize(true);

        reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        reviewRecyclerView.setLayoutManager(reviewLayoutManager);

        reviewAdapter = new ReviewAdapter(getApplicationContext(), new ArrayList<TheMovieDatabaseReviewsResult>(0));
        reviewRecyclerView.setAdapter(reviewAdapter);

        // See https://stackoverflow.com/a/27037230/2107568 for divider decoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(reviewRecyclerView.getContext(),
                reviewLayoutManager.getOrientation());
        reviewRecyclerView.addItemDecoration(dividerItemDecoration);

        populateReviews(movieId);
    }

    private void populateReviews(int movieId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieDatabaseNetworkUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesDatabaseClient client = retrofit.create(MoviesDatabaseClient.class);
        Call<TheMovieDatabaseReviewsResponse> call;

        call = client.reviewsByMovieId(movieId, TMDB_API_KEY);

        call.enqueue(new Callback<TheMovieDatabaseReviewsResponse>() {
            @Override
            public void onResponse(Call<TheMovieDatabaseReviewsResponse> call, Response<TheMovieDatabaseReviewsResponse> response) {
                if (response.isSuccessful()) {
                    reviewAdapter.updateData(response.body().getResults());
                } else {
                    Toast.makeText(getApplicationContext(), "Fetch reviews response code:" + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TheMovieDatabaseReviewsResponse> call, Throwable t) {
                showFailureMessage();
            }
        });
    }

    private void showFailureMessage() {
        Toast.makeText(this, R.string.failure_message, Toast.LENGTH_LONG).show();
    }
}
