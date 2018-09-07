package com.mattniehoff.tmdbandroidviewer.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TheMovieDatabaseVideosResponse {

	@SerializedName("id")
	private int id;

	@SerializedName("results")
	private List<TheMovieDatabaseVideosResult> results;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setResults(List<TheMovieDatabaseVideosResult> results){
		this.results = results;
	}

	public List<TheMovieDatabaseVideosResult> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"TheMovieDatabaseVideosResponse{" +
			"id = '" + id + '\'' + 
			",results = '" + results + '\'' + 
			"}";
		}
}