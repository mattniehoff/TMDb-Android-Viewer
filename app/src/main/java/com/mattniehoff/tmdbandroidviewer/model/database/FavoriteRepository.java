package com.mattniehoff.tmdbandroidviewer.model.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseMovieResult;

import java.util.List;

// Repository class adapted from https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#7
public class FavoriteRepository {
    private FavoriteDao favoriteDao;
    private LiveData<List<TheMovieDatabaseMovieResult>> allFavorites;

    public FavoriteRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        favoriteDao = db.favoriteDao();
        allFavorites = favoriteDao.getAllFavorites();
    }

    public LiveData<List<TheMovieDatabaseMovieResult>> getAllFavorites() {
        return allFavorites;
    }


    public void insert (TheMovieDatabaseMovieResult favorite) {
        new insertAsyncTask(favoriteDao).execute(favorite);
    }

    private static class insertAsyncTask extends AsyncTask<TheMovieDatabaseMovieResult, Void, Void> {

        private FavoriteDao favoriteDao;

        insertAsyncTask(FavoriteDao dao) {
            favoriteDao = dao;
        }

        @Override
        protected Void doInBackground(final TheMovieDatabaseMovieResult... params) {
            favoriteDao.insertFavorite(params[0]);
            return null;
        }
    }
}
