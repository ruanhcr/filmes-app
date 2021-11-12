package com.ruru.movies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ruru.movies.R;
import com.ruru.movies.models.MovieModel;
import com.ruru.movies.utils.Credentials;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;

    private static final int DISPLAY_POP = 1;
    private static final int DISPLAY_SEARCH = 2;

    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        if (viewType == DISPLAY_SEARCH){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,
                    parent, false);
            return new MovieViewHolder(view, onMovieListener);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,
                    parent, false);
            return new PopularViewHolder(view, onMovieListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == DISPLAY_SEARCH){
            //convertendo votação pela metade pois são 5 estrelas no layout
            ((MovieViewHolder)holder).ratingBar.setRating(mMovies.get(position).getVote_average()/2);

            //upload da imagem usando a biblioteca Glide
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/" + mMovies.get(position).getPoster_path())
                    .into((((MovieViewHolder)holder).imageView));
        }else{
            //convertendo votação pela metade pois são 5 estrelas no layout
            ((PopularViewHolder)holder).ratingBarPop.setRating(mMovies.get(position).getVote_average()/2);

            //upload da imagem usando a biblioteca Glide
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/" + mMovies.get(position).getPoster_path())
                    .into((((PopularViewHolder)holder).imageViewPop));
        }
    }

    @Override
    public int getItemCount() {
        if(mMovies != null){
            return mMovies.size();
        }
        return 0;
    }



    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    //pegando o id do filme que foi clicado
    public MovieModel getSelectedMovie(int position){
        if(mMovies != null){
            if (mMovies.size() > 0){
                return mMovies.get(position);
            }
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if(Credentials.POPULAR){
            return DISPLAY_POP;
        }else{
            return DISPLAY_SEARCH;
        }
    }
}
