package com.mattniehoff.tmdbandroidviewer.model.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseMovieResult;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM favorite_movie")
    LiveData<List<TheMovieDatabaseMovieResult>> getAllFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(TheMovieDatabaseMovieResult movieResult);

    @Delete
    void deleteFavorite(TheMovieDatabaseMovieResult movieResult);
}
