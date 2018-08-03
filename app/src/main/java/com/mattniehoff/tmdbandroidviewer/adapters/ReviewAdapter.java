package com.mattniehoff.tmdbandroidviewer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattniehoff.tmdbandroidviewer.R;
import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseReviewsResult;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private Context context;
    private List<TheMovieDatabaseReviewsResult> data;

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView reviewAuthorTextView;
        public TextView reviewContentTextView;

        public ViewHolder(View v) {
            super(v);
            reviewAuthorTextView = (TextView) v.findViewById(R.id.review_author);
            reviewContentTextView = (TextView) v.findViewById(R.id.review_content);
        }
    }

    public ReviewAdapter(Context context, List<TheMovieDatabaseReviewsResult> data) {
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
        TheMovieDatabaseReviewsResult result = data.get(position);
        if (result != null) {
            holder.reviewAuthorTextView.setText(result.getAuthor());
            holder.reviewContentTextView.setText(result.getContent());
        } else {
            holder.reviewAuthorTextView.setText(R.string.invalid_review_data_text);
            holder.reviewContentTextView.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<TheMovieDatabaseReviewsResult> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
