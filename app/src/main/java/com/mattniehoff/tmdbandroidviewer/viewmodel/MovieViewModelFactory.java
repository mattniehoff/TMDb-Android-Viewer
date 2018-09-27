package com.mattniehoff.tmdbandroidviewer.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.mattniehoff.tmdbandroidviewer.model.database.AppDatabase;

// Factory class adapted from Udacity Arch Components tutorial
public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase database;
    private final int taskId;

    public MovieViewModelFactory(AppDatabase database, int taskId){
        this.database = database;
        this.taskId = taskId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MovieViewModel(database, taskId);
    }
}
