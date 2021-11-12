package com.ruru.movies.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ruru.movies.models.MovieModel;
import com.ruru.movies.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    private MovieRepository movieRepository;

    //Construtor
    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }

    //método para os filmes populares
    public LiveData<List<MovieModel>> getPopular(){
        return movieRepository.getPopularMovies();
    }

    //3- chamando o método no view model para pesquisa
    public void searchMovieApi(String query, int pageNumber){
        movieRepository.searchMovieApi(query, pageNumber);
    }

    //chamando o método de filmes populares
    public void searchMoviePop(int pageNumber){
        movieRepository.searchMoviePop(pageNumber);
    }

    public void searchNextPage(){
        movieRepository.searchNextPage();
    }
}
