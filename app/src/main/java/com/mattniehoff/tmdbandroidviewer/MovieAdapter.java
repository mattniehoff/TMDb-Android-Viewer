package com.mattniehoff.tmdbandroidviewer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattniehoff.tmdbandroidviewer.model.Result;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Result> data;
    private int numberOfDataItems;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView placeholderTextView;
        public ViewHolder (View v) {
            super (v);
            placeholderTextView = (TextView) v.findViewById(R.id.placeholder_text_view);
        }
    }

    public MovieAdapter(List<Result> data){
        this.data = data;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_block, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result result = data.get(position);
        holder.placeholderTextView.setText(result.getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<Result> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
