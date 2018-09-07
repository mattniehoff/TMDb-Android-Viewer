package com.mattniehoff.tmdbandroidviewer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattniehoff.tmdbandroidviewer.R;
import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseVideosResult;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private Context context;
    private List<TheMovieDatabaseVideosResult> data;

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView reviewAuthorTextView;
        public TextView reviewContentTextView;

        public ViewHolder(View v) {
            super(v);
            reviewAuthorTextView = (TextView) v.findViewById(R.id.review_author);
            reviewContentTextView = (TextView) v.findViewById(R.id.review_content);
        }
    }

    public VideoAdapter(Context context, List<TheMovieDatabaseVideosResult> data) {
        this.context = context;
        this.data = data;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TheMovieDatabaseVideosResult result = data.get(position);
        if (result != null) {
            holder.reviewAuthorTextView.setText(result.getName());
            holder.reviewContentTextView.setText(result.getId());
        } else {
            holder.reviewAuthorTextView.setText(R.string.invalid_review_data_text);
            holder.reviewContentTextView.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<TheMovieDatabaseVideosResult> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
