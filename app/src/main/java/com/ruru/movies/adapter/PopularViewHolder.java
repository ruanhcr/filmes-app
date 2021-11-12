package com.ruru.movies.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ruru.movies.R;

public class PopularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    OnMovieListener onMovieListener;
    ImageView imageViewPop;
    RatingBar ratingBarPop;

    public PopularViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;
        imageViewPop = itemView.findViewById(R.id.movie_img_pop);
        ratingBarPop = itemView.findViewById(R.id.rating_bar_pop);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
