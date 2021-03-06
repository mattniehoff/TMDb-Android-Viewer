package com.mattniehoff.tmdbandroidviewer.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.mattniehoff.tmdbandroidviewer.BuildConfig;
import com.mattniehoff.tmdbandroidviewer.R;
import com.mattniehoff.tmdbandroidviewer.adapters.MovieAdapter;
import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseMovieResult;
import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseMovieResultUtils;
import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseResponse;
import com.mattniehoff.tmdbandroidviewer.network.MovieDatabaseNetworkUtils;
import com.mattniehoff.tmdbandroidviewer.network.MovieDatabaseQueryType;
import com.mattniehoff.tmdbandroidviewer.network.MoviesDatabaseClient;
import com.mattniehoff.tmdbandroidviewer.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.ListItemClickListener {

    public static final int MOVIE_ACTIVITY_REQUEST_CODE = 1;

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String[] testData;
   // private MovieDatabaseQueryType movieDatabaseQueryType;

    private MainViewModel mainViewModel;

    private static final String TMDB_API_KEY = BuildConfig.TMDB_API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // From https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#13
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getFavorites().observe(this, new Observer<List<TheMovieDatabaseMovieResult>>() {
            @Override
            public void onChanged(@Nullable List<TheMovieDatabaseMovieResult> theMovieDatabaseMovieResults) {
                if (mainViewModel.getMovieDatabaseQueryType() == MovieDatabaseQueryType.MOVIES_FAVORITE) {
                    refreshFavorites(theMovieDatabaseMovieResults);
                }
            }
        });

       // movieDatabaseQueryType = MovieDatabaseQueryType.MOVIES_BY_POPULARITY;

        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);

        recyclerView.setHasFixedSize(true);

        // Set layout manager
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MovieAdapter(getApplicationContext(), new ArrayList<TheMovieDatabaseMovieResult>(0), this);
        recyclerView.setAdapter(adapter);

        populateData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.change_sort_menu:
                changeSort();
                populateData();
                return true;
            case R.id.toggle_favorites:
                changeSortToFavorites();
                refreshFavorites(mainViewModel.getFavorites().getValue());
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeSort() {
        String toastMessage = getString(R.string.change_sort_prefix);
        if (mainViewModel.getMovieDatabaseQueryType() == MovieDatabaseQueryType.MOVIES_BY_POPULARITY) {
            mainViewModel.setMovieDatabaseQueryType(MovieDatabaseQueryType.MOVIES_BY_RATING);
            toastMessage += getString(R.string.rating);
        } else {
            mainViewModel.setMovieDatabaseQueryType(MovieDatabaseQueryType.MOVIES_BY_POPULARITY);
            toastMessage += getString(R.string.popularity);
        }

        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
    }

    private void changeSortToFavorites() {
        if (mainViewModel.getMovieDatabaseQueryType() != MovieDatabaseQueryType.MOVIES_FAVORITE) {
            mainViewModel.setMovieDatabaseQueryType(MovieDatabaseQueryType.MOVIES_FAVORITE);
        } else {
            changeSort();
            populateData();
        }
    }

    private void populateData() {
        if (mainViewModel.getMovieDatabaseQueryType() == MovieDatabaseQueryType.MOVIES_FAVORITE) {
            refreshFavorites(mainViewModel.getFavorites().getValue());
            return;
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieDatabaseNetworkUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesDatabaseClient client = retrofit.create(MoviesDatabaseClient.class);
        Call<TheMovieDatabaseResponse> call;

        switch (mainViewModel.getMovieDatabaseQueryType()) {

            case MOVIES_BY_POPULARITY:
                call = client.moviesByPopularity(TMDB_API_KEY);
                break;
            case MOVIES_BY_RATING:
                call = client.moviesByRating(TMDB_API_KEY);
                break;
            default:
                Toast.makeText(this, getString(R.string.error_invalid_movie_database_query_type) + mainViewModel.getMovieDatabaseQueryType(), Toast.LENGTH_LONG);
                return;
        }

        call.enqueue(new Callback<TheMovieDatabaseResponse>() {
            @Override
            public void onResponse(Call<TheMovieDatabaseResponse> call, Response<TheMovieDatabaseResponse> response) {
                if (response.isSuccessful()) {
                    adapter.updateData(response.body().getTheMovieDatabaseMovieResults());
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.movie_database_response_code_message_prefix) + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TheMovieDatabaseResponse> call, Throwable t) {
                showFailureMessage();
            }
        });
    }

    private void refreshFavorites(List<TheMovieDatabaseMovieResult> theMovieDatabaseMovieResults) {
        if (mainViewModel.getMovieDatabaseQueryType() == MovieDatabaseQueryType.MOVIES_FAVORITE) {
            adapter.updateData(theMovieDatabaseMovieResults);
        }
    }

    private void showFailureMessage() {
        Toast.makeText(this, R.string.failure_message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onListItemClick(TheMovieDatabaseMovieResult movieTheMovieDatabaseMovieResult) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(MovieActivity.MOVIE_EXTRA, movieTheMovieDatabaseMovieResult);
        intent.putExtra(MovieActivity.IS_FAVORITE_EXTRA, checkIfMovieFavorited(movieTheMovieDatabaseMovieResult.getId()));
        startActivityForResult(intent, MOVIE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MOVIE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            TheMovieDatabaseMovieResult movie = data.getParcelableExtra(MovieActivity.MOVIE_EXTRA);
            boolean isFavorite = data.getBooleanExtra(MovieActivity.IS_FAVORITE_EXTRA, false);
            if (isFavorite) {
                mainViewModel.insert(movie);
            } else {
                mainViewModel.delete(movie);
            }
        }
    }

    private boolean checkIfMovieFavorited(int movieId) {
        return TheMovieDatabaseMovieResultUtils
                .containsMovieId(mainViewModel.getFavorites().getValue(), movieId);
    }
}
