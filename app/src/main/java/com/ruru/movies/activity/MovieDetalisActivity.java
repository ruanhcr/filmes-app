package com.ruru.movies.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruru.movies.R;
import com.ruru.movies.models.MovieModel;

public class MovieDetalisActivity extends AppCompatActivity {

    //widgets
    private ImageView imageViewDetails;
    private TextView titleDetails, descDetails;
    private RatingBar ratingBarDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detalis);

        imageViewDetails = findViewById(R.id.imageViewDetalis);
        titleDetails = findViewById(R.id.textViewTitleDetalis);
        descDetails = findViewById(R.id.textViewDescDetails);
        ratingBarDetails = findViewById(R.id.ratingBarDetails);

        GetDataFromIntent();
    }

    private void GetDataFromIntent() {
        if (getIntent().hasExtra("movie")){
            MovieModel movieModel = getIntent().getParcelableExtra("movie");

            titleDetails.setText(movieModel.getTitle());
            descDetails.setText(movieModel.getMovie_overview());
            ratingBarDetails.setRating((movieModel.getVote_average())/2);

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/" + movieModel.getPoster_path())
                    .into(imageViewDetails);
        }
    }
}