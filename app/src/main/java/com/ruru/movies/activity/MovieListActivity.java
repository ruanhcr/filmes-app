package com.ruru.movies.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ruru.movies.R;
import com.ruru.movies.adapter.MovieRecyclerView;
import com.ruru.movies.adapter.OnMovieListener;
import com.ruru.movies.models.MovieModel;
import com.ruru.movies.viewmodels.MovieListViewModel;

import java.util.List;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerAdapter;

    // ViewModel
    private MovieListViewModel movieListViewModel;

    private List<MovieModel> mmMovies;

    boolean isPopular = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //SearchView
        setupSearchView();

        recyclerView = findViewById(R.id.recyclerView);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        //chamando o observer e o método
        observeAnyChange();
        observePopularMovies();

        configurerRecyclerView();

        //get dados dos filmes populares
        movieListViewModel.searchMoviePop(1);
    }

    private void observePopularMovies() {
        movieListViewModel.getPopular().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Observando mudança de dados
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        //get dados
                        Log.v("Tag", "onChaged: " + movieModel.getTitle());

                        movieRecyclerAdapter.setmMovies(movieModels);
                    }
                }
            }
        });
    }

    // Observando mudança de dados
    private void observeAnyChange(){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Observando mudança de dados
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        movieRecyclerAdapter.setmMovies(movieModels);
                    }
                }
            }
        });
    }

    // 5- inicializando recyclerview e adicionando os dados
    private void configurerRecyclerView(){
        movieRecyclerAdapter = new MovieRecyclerView( this);

        recyclerView.setAdapter(movieRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        //paginação do recyclerview - adicionando proximas paginas de resposta da api
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)){
                    //aqui que precisa mostrar os resultados da proxima pagina da api
                    //movieListViewModel.searchNextPage();
                }
            }
        });
    }

    @Override
    public void onMovieClick(int position) {
        //usando o id  para passar o filme pra outra activity
        Intent intent = new Intent(this, MovieDetalisActivity.class);
        intent.putExtra("movie", movieRecyclerAdapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }

    //pegando os dados do searchview e pedindo pra api mandar os resultados (filmes)
    private void setupSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieApi(query, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPopular = false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                isPopular = true;
                getPopularMovies();
                return false;
            }
        });
    }
    public void getPopularMovies(){
        movieListViewModel.searchMoviePop(1);
    }
}