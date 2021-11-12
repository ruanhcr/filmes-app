package com.ruru.movies.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ruru.movies.models.MovieModel;

//Requisição de apenas um filme

public class MovieResponse {

    //encontrando o objeto do filme
    @SerializedName("results")
    @Expose
    private MovieModel movie;

    public MovieModel getMovie(){
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }
}
