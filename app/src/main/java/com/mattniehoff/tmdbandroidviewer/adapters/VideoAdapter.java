package com.mattniehoff.tmdbandroidviewer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattniehoff.tmdbandroidviewer.R;
import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseVideosResult;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private Context context;
    private List<TheMovieDatabaseVideosResult> data;

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView videoThumbnailImageView;
        public TextView videoTitleTextView;

        public ViewHolder(View v) {
            super(v);
            videoThumbnailImageView = (ImageView) v.findViewById(R.id.video_thumbnail);
            videoTitleTextView = (TextView) v.findViewById(R.id.video_title);
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
        View view = layoutInflater.inflate(R.layout.item_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TheMovieDatabaseVideosResult result = data.get(position);
        if (result != null) {
            //holder.videoThumbnailImageView.setText(result.getName());
            Picasso.get().load(result.getVideoThumbnailUrl()).into(holder.videoThumbnailImageView);
            holder.videoTitleTextView.setText(result.getName());
        } else {
            holder.videoTitleTextView.setText(R.string.invalid_trailer_message);
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
