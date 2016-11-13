package com.example.wasim.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    GridView gridview;
    ImageAdapter imageAdapter;
    MovieClient movieClient = new MovieClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridview = (GridView) findViewById(R.id.gridview);
        imageAdapter = new ImageAdapter(this);
        movieClient.imageAdapter = imageAdapter;
        movieClient.execute(movieClient.moviesURL());
        gridview.setAdapter(imageAdapter);

        while (gridview.getAdapter().getCount() == 0) {
            gridview.invalidateViews();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.most_popular:
                movieClient.sortMovies("popularity");
                gridview.invalidateViews();
                return true;
            case R.id.top_rated:
                movieClient.sortMovies("vote_average");
                gridview.invalidateViews();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}