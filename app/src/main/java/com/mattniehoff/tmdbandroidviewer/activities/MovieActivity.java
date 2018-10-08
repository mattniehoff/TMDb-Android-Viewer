package com.mattniehoff.tmdbandroidviewer.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mattniehoff.tmdbandroidviewer.R;
import com.mattniehoff.tmdbandroidviewer.adapters.ReviewAdapter;
import com.mattniehoff.tmdbandroidviewer.adapters.VideoAdapter;
import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseMovieResult;
import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseReviewsResponse;
import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseReviewsResult;
import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseVideosResponse;
import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseVideosResult;
import com.mattniehoff.tmdbandroidviewer.model.database.FavoriteRepository;
import com.mattniehoff.tmdbandroidviewer.network.MovieDatabaseNetworkUtils;
import com.mattniehoff.tmdbandroidviewer.network.MovieDatabaseQueryType;
import com.mattniehoff.tmdbandroidviewer.network.MoviesDatabaseClient;
import com.mattniehoff.tmdbandroidviewer.viewmodel.MainViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mattniehoff.tmdbandroidviewer.BuildConfig.TMDB_API_KEY;

public class MovieActivity extends AppCompatActivity
        implements VideoAdapter.ListItemClickListener {

    public static final String RESULT_EXTRA = "result_extra";
    public static final String IS_FAVORITE_EXTRA = "is_favorite_extra";

    private ImageView moviePosterImageView;
    private TextView titleTextView;
    private TextView releaseDateTextView;
    private TextView voteAverageTextView;
    private TextView plotTextView;
    private CheckBox favoritesCheckBox;

    private RecyclerView reviewRecyclerView;
    private RecyclerView videoRecyclerView;
    private ReviewAdapter reviewAdapter;
    private VideoAdapter videoAdapter;
    private LinearLayoutManager reviewLayoutManager;
    private LinearLayoutManager videoLayoutManager;

    private FavoriteRepository repository;
    private boolean isFavorite;

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
        final TheMovieDatabaseMovieResult movieTheMovieDatabaseMovieResult = data.getParcelable(RESULT_EXTRA);
        isFavorite = data.getBoolean(IS_FAVORITE_EXTRA);

        Picasso.get().load(movieTheMovieDatabaseMovieResult.getMoviePosterUrl()).into(moviePosterImageView);
        titleTextView.setText(movieTheMovieDatabaseMovieResult.getTitle());
        releaseDateTextView.setText(movieTheMovieDatabaseMovieResult.getReleaseDate());
        voteAverageTextView.setText(Double.toString(movieTheMovieDatabaseMovieResult.getVoteAverage()));
        plotTextView.setText(movieTheMovieDatabaseMovieResult.getOverview());

        configureAndPopulateReviewsAndVideosRecyclerView(movieTheMovieDatabaseMovieResult.getId());

        favoritesCheckBox = findViewById(R.id.favorite_check_box);
        favoritesCheckBox.setChecked(isFavorite);

//
//        favoritesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked){
//                    mainViewModel.insert(movieTheMovieDatabaseMovieResult);
//                } else {
//                    mainViewModel.delete(movieTheMovieDatabaseMovieResult);
//                }
//            }
//        });
    }

    private void configureAndPopulateReviewsAndVideosRecyclerView(int movieId) {
        // Review RecyclerView
        reviewRecyclerView = (RecyclerView) findViewById(R.id.reviews_recycler_view);
        reviewRecyclerView.setHasFixedSize(true);

        reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        reviewRecyclerView.setLayoutManager(reviewLayoutManager);

        reviewAdapter = new ReviewAdapter(getApplicationContext(), new ArrayList<TheMovieDatabaseReviewsResult>(0));
        reviewRecyclerView.setAdapter(reviewAdapter);

        // Videos RecyclerView
        videoRecyclerView = (RecyclerView) findViewById(R.id.videos_recycler_view);
        videoRecyclerView.setHasFixedSize(true);

        videoLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        videoRecyclerView.setLayoutManager(videoLayoutManager);

        videoAdapter = new VideoAdapter(getApplicationContext(), new ArrayList<TheMovieDatabaseVideosResult>(), this);
        videoRecyclerView.setAdapter(videoAdapter);

        // See https://stackoverflow.com/a/27037230/2107568 for divider decoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(reviewRecyclerView.getContext(),
                reviewLayoutManager.getOrientation());
        reviewRecyclerView.addItemDecoration(dividerItemDecoration);
        videoRecyclerView.addItemDecoration(dividerItemDecoration);

        populateReviews(movieId);
        populateVideos(movieId);
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

    private void populateVideos(int movieId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieDatabaseNetworkUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesDatabaseClient client = retrofit.create(MoviesDatabaseClient.class);
        Call<TheMovieDatabaseVideosResponse> call;

        call = client.videosByMovieId(movieId, TMDB_API_KEY);

        call.enqueue(new Callback<TheMovieDatabaseVideosResponse>() {
            @Override
            public void onResponse(Call<TheMovieDatabaseVideosResponse> call, Response<TheMovieDatabaseVideosResponse> response) {
                if (response.isSuccessful()) {
                    videoAdapter.updateData(response.body().getResults());
                } else {
                    Toast.makeText(getApplicationContext(), "Fetch reviews response code:" + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TheMovieDatabaseVideosResponse> call, Throwable t) {
                showFailureMessage();
            }
        });
    }

    private void showFailureMessage() {
        Toast.makeText(this, R.string.failure_message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onListItemClick(TheMovieDatabaseVideosResult movieDatabaseVideosResult) {
        Uri url = Uri.parse(movieDatabaseVideosResult.getYoutubeUri());
        Intent intent = new Intent(Intent.ACTION_VIEW, url);
        startActivity(intent);
    }
}
