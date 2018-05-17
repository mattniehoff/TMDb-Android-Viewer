package com.mattniehoff.tmdbandroidviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mattniehoff.tmdbandroidviewer.model.Result;
import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseResponse;
import com.mattniehoff.tmdbandroidviewer.network.MovieDatabaseNetworkUtils;
import com.mattniehoff.tmdbandroidviewer.network.MoviesDatabaseClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
    implements MovieAdapter.ListItemClickListener {

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

        adapter = new MovieAdapter(getApplicationContext(), new ArrayList<Result>(0), this);
        recyclerView.setAdapter(adapter);

        populateData();
    }

    private void populateData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieDatabaseNetworkUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesDatabaseClient client = retrofit.create(MoviesDatabaseClient.class);
        Call<TheMovieDatabaseResponse> call = client.moviesByPopularity(ApiKeys.TmdbApiKey);

       call.enqueue(new Callback<TheMovieDatabaseResponse>() {
           @Override
           public void onResponse(Call<TheMovieDatabaseResponse> call, Response<TheMovieDatabaseResponse> response) {
               if (response.isSuccessful()){
                   adapter.updateData(response.body().getResults());
               } else{
                   Toast.makeText(getApplicationContext(), "Response code: " + response.code(), Toast.LENGTH_LONG).show();
               }
           }

           @Override
           public void onFailure(Call<TheMovieDatabaseResponse> call, Throwable t) {
                showFailureMessage();
           }
       });
    }

    private void showFailureMessage(){
        Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onListItemClick(Result movieResult) {
        String toastMessage = "Movie title: " + movieResult.getTitle() + " clicked.";
        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(MovieActivity.RESULT_EXTRA, movieResult);
        startActivity(intent);
    }
}
