package com.ruru.movies.utils;

import com.ruru.movies.models.MovieModel;
import com.ruru.movies.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    //configurar a busca por filmes
    //https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher&language=pt-BR

    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page,
            @Query("language") String language
    );


    // configurando os filmes populares na main activity
    // https://api.themoviedb.org/3/movie/popular?api_key=33962a76d3a5747925f8463df0ef4282&page=1
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query("api_key") String key,
            @Query("page") int page,
            @Query("language") String language
    );


    //configurar a busca por ID
    //https://api.themoviedb.org/3/movie/550?api_key=33962a76d3a5747925f8463df0ef4282

    @GET("3/movie/{movie_id}?")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );
}
