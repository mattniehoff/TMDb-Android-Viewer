package com.mattniehoff.tmdbandroidviewer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattniehoff.tmdbandroidviewer.model.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Result> data;
    private int numberOfDataItems;
    private Context context;
    private ListItemClickListener clickListener;

    public interface ListItemClickListener {
        void onListItemClick(Result movieResult);
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public ImageView posterImageView;
        public ViewHolder (View v) {
            super (v);
            posterImageView = (ImageView) v.findViewById(R.id.movie_poster_iv);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Result clickedResult = data.get(clickedPosition);
            clickListener.onListItemClick(clickedResult);
        }
    }

    public MovieAdapter(Context context, List<Result> data, ListItemClickListener listener){
        // Context needed for Picasso
        this.context = context;
        this.data = data;
        this.clickListener = listener;

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
        Picasso.get().load(result.getMoviePosterUrl()).into(holder.posterImageView);
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
