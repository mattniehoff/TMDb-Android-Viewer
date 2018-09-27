package com.mattniehoff.tmdbandroidviewer.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseMovieResult;
import com.mattniehoff.tmdbandroidviewer.model.database.AppDatabase;

// Adapted from Udacity Arch Components tutorial
public class MovieViewModel extends ViewModel{

    private static final String TAG = MovieViewModel.class.getSimpleName();

    private LiveData<TheMovieDatabaseMovieResult> movie;

    public MovieViewModel(AppDatabase database, int movieId) {
        movie = database.favoriteDao().loadFavoriteById(movieId);
    }

    public LiveData<TheMovieDatabaseMovieResult> getMovie() {
        return movie;
    }
}
