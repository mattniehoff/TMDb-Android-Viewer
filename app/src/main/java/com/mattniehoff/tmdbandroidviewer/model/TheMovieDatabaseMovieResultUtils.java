package com.mattniehoff.tmdbandroidviewer.model;

import java.util.List;

public class TheMovieDatabaseMovieResultUtils {
    public static boolean containsMovieId(List<TheMovieDatabaseMovieResult> movies, int id) {
        for (TheMovieDatabaseMovieResult movie : movies) {
            if (movie.getId() == id) {
                return true;
            }
        }

        return false;
    }
}
