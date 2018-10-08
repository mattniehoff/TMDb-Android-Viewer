package com.mattniehoff.tmdbandroidviewer.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

// Class generated with help from http://www.jsonschema2pojo.org/
public class TheMovieDatabaseResponse {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<TheMovieDatabaseMovieResult> theMovieDatabaseMovieResults = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<TheMovieDatabaseMovieResult> getTheMovieDatabaseMovieResults() {
        return theMovieDatabaseMovieResults;
    }

    public void setTheMovieDatabaseMovieResults(List<TheMovieDatabaseMovieResult> theMovieDatabaseMovieResults) {
        this.theMovieDatabaseMovieResults = theMovieDatabaseMovieResults;
    }

}
