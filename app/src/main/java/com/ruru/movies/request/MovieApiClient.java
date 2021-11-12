package com.ruru.movies.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ruru.movies.executors.AppExecutors;
import com.ruru.movies.models.MovieModel;
import com.ruru.movies.response.MovieSearchResponse;
import com.ruru.movies.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    //LiveData para pesquisa
    private MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient instance;

    //Global Runnable Request
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    //LiveData para os filmes populares
    private MutableLiveData<List<MovieModel>> mMoviesPop;

    //Filmes Populares Runnable Request
    private RetrieveMoviesRunnablePop retrieveMoviesRunnablePop;

    public static MovieApiClient getInstance(){
        if(instance == null){
            instance = new MovieApiClient();
        }
        return instance;
    }
    private MovieApiClient(){
        mMovies = new MutableLiveData<>();
        mMoviesPop = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }

    public LiveData<List<MovieModel>> getMoviesPop(){
        return mMoviesPop;
    }

    //1- método que será chamado entre as classes
    public void searchMoviesApi(String query, int pageNumber){

        if (retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);

    }


    //pesquisa para filmes populares
    public void searchMoviesPop(int pageNumber){

        if (retrieveMoviesRunnablePop != null){
            retrieveMoviesRunnablePop = null;
        }

        retrieveMoviesRunnablePop = new RetrieveMoviesRunnablePop(pageNumber);

        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnablePop);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler2.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);

    }

    //Retreiving data from RestAPI by runnable class
    //2 tipos de Query: ID Query e search Query
    private class RetrieveMoviesRunnable implements Runnable{

        private String query, language;
        private int pageNumber;
        boolean cancelRequest;


        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try{
                Response response = getMovies(query, pageNumber, language).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(pageNumber == 1){
                        //sending data to live data
                        //postvalue: usado para thread background
                        //setvalue: nao é para a thread background
                        mMovies.postValue(list);
                    }else{
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                }else{
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error " + error);
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }
        }

        //search method/query
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber, String language){
                return Servicey.getMovieApi().searchMovie(
                        Credentials.API_KEY,
                        query,
                        pageNumber,
                        Credentials.LANGUAGE);
        }

        private void cancelRequest(){
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }

    }

    private class RetrieveMoviesRunnablePop implements Runnable{

        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnablePop(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try{
                Response response2 = getPopularMovies(pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if(response2.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response2.body()).getMovies());
                    if(pageNumber == 1){
                        //sending data to live data
                        //postvalue: usado para thread background
                        //setvalue: nao é para a thread background
                        mMoviesPop.postValue(list);
                    }else{
                        List<MovieModel> currentMovies = mMoviesPop.getValue();
                        currentMovies.addAll(list);
                        mMoviesPop.postValue(currentMovies);
                    }
                }else{
                    String error = response2.errorBody().string();
                    Log.v("Tag", "Error " + error);
                    mMoviesPop.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMoviesPop.postValue(null);
            }
        }

        //search method/query
        private Call<MovieSearchResponse> getPopularMovies(int pageNumber){
            return Servicey.getMovieApi().getPopular(
                    Credentials.API_KEY,
                    pageNumber,
                    Credentials.LANGUAGE);
        }

        private void cancelRequest(){
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }

    }

}
