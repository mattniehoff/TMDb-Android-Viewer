package com.mattniehoff.tmdbandroidviewer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mattniehoff.tmdbandroidviewer.R;
import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseMovieResult;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<TheMovieDatabaseMovieResult> data;
    private int numberOfDataItems;
    private Context context;
    private ListItemClickListener clickListener;

    public interface ListItemClickListener {
        void onListItemClick(TheMovieDatabaseMovieResult movieTheMovieDatabaseMovieResult);
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
            TheMovieDatabaseMovieResult clickedTheMovieDatabaseMovieResult = data.get(clickedPosition);
            clickListener.onListItemClick(clickedTheMovieDatabaseMovieResult);
        }
    }

    public MovieAdapter(Context context, List<TheMovieDatabaseMovieResult> data, ListItemClickListener listener){
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
        TheMovieDatabaseMovieResult theMovieDatabaseMovieResult = data.get(position);
        Picasso.get().load(theMovieDatabaseMovieResult.getMoviePosterUrl()).into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<TheMovieDatabaseMovieResult> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
