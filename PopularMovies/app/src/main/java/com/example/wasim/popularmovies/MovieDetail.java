package com.example.wasim.popularmovies;

import android.icu.text.SimpleDateFormat;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //REFERENCE : http://stackoverflow.com/questions/15686555/display-back-button-on-action-bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView titleView = (TextView) findViewById(R.id.titleView);
        TextView synopsisView = (TextView) findViewById(R.id.synopsis);
        TextView releaseDView = (TextView) findViewById(R.id.releaseD);
        TextView ratingView = (TextView) findViewById(R.id.rating);
        ImageView backDrop = (ImageView) findViewById(R.id.imageViewBack);
        ImageView posterView = (ImageView) findViewById(R.id.imageView);

        try {
            JSONObject info = new JSONObject(getIntent().getStringExtra("info"));
            String title = info.getString("title");
            String releaseDate = info.getString("release_date");
            String rating = info.get("vote_average").toString();
            String posterPath = info.getString("poster_path");
            String backdropPath = info.getString("backdrop_path");
            String synopsis = info.getString("overview");
            titleView.setText(title);
            releaseDView.setText(releaseDate);
            ratingView.setText(rating + "/10");
            synopsisView.setText(synopsis);
            String url = "http://image.tmdb.org/t/p/w185/" + posterPath;
            String url2 = "http://image.tmdb.org/t/p/w780/" + backdropPath;
            Picasso.with(posterView.getContext()).load(url).into(posterView);
            Picasso.with(backDrop.getContext()).load(url2).into(backDrop);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
