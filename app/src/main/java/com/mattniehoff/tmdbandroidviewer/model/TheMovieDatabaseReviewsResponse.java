package com.mattniehoff.tmdbandroidviewer.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// Generated with http://www.jsonschema2pojo.org/
public class TheMovieDatabaseReviewsResponse implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("results")
    @Expose
    private List<TheMovieDatabaseReviewsResult> results = null;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    public final static Parcelable.Creator<TheMovieDatabaseReviewsResponse> CREATOR = new Creator<TheMovieDatabaseReviewsResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TheMovieDatabaseReviewsResponse createFromParcel(Parcel in) {
            return new TheMovieDatabaseReviewsResponse(in);
        }

        public TheMovieDatabaseReviewsResponse[] newArray(int size) {
            return (new TheMovieDatabaseReviewsResponse[size]);
        }

    };

    protected TheMovieDatabaseReviewsResponse(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.page = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.results, (com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseReviewsResult.class.getClassLoader()));
        this.totalPages = ((int) in.readValue((int.class.getClassLoader())));
        this.totalResults = ((int) in.readValue((int.class.getClassLoader())));
    }

    public TheMovieDatabaseReviewsResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<TheMovieDatabaseReviewsResult> getResults() {
        return results;
    }

    public void setResults(List<TheMovieDatabaseReviewsResult> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(page);
        dest.writeList(results);
        dest.writeValue(totalPages);
        dest.writeValue(totalResults);
    }

    public int describeContents() {
        return 0;
    }

}
