package com.mattniehoff.tmdbandroidviewer.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseMovieResult;
import com.mattniehoff.tmdbandroidviewer.model.database.FavoriteRepository;

import java.util.List;

// ViewModel adapted from https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#8
public class MainViewModel extends AndroidViewModel {
    private FavoriteRepository repository;

    private LiveData<List<TheMovieDatabaseMovieResult>> favorites;

    public MainViewModel(Application application) {
        super(application);
        repository = new FavoriteRepository(application);
        favorites = repository.getAllFavorites();
    }

    public LiveData<List<TheMovieDatabaseMovieResult>> getFavorites() {
        return favorites;
    }

    public void delete(TheMovieDatabaseMovieResult favorite) {
        repository.delete(favorite);
    }

    public void insert(TheMovieDatabaseMovieResult favorite) {
        repository.insert(favorite);
    }

    public boolean favoriteSaved(TheMovieDatabaseMovieResult favorite) {
        return repository.favoriteExists(favorite.getId());
    }

    public void toggleFavorite(TheMovieDatabaseMovieResult favorite) {
        if (favoriteSaved(favorite)) {
            delete(favorite);
        } else {
            insert(favorite);
        }
    }
}

