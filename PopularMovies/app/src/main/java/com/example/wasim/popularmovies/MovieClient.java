package com.example.wasim.popularmovies;

import android.os.AsyncTask;
import android.util.Log;

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


//REFERENCE: https://gist.github.com/anonymous/6b306e1f6a21b3718fa4
class MovieClient extends AsyncTask<URL, Integer, Long> {

    ImageAdapter imageAdapter;
    private ArrayList<JSONObject> movieArray = new ArrayList<>();

    URL moviesURL () {
        String baseURL = "http://api.themoviedb.org/3/movie/";
        String pop = "popular";
        String apiKey = "";
        String popMovies = baseURL + pop + apiKey;

        URL url = null;
        try {
            url = new URL(popMovies);
            return url;
        }catch(MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    protected Long doInBackground(URL...url) {

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            urlConnection = (HttpURLConnection) url[0].openConnection();
        } catch(IOException e) {
            e.printStackTrace();
        }try {
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String moviesJSON;
            StringBuffer buffer = new StringBuffer();
            String rLine;
            while ((rLine = reader.readLine()) != null) {
                buffer.append(rLine + "\n");
            }

            moviesJSON = buffer.toString();
            JSONArray myJson;
            try {
                myJson = new JSONObject(moviesJSON).getJSONArray("results");
                for (int i = 0; i < myJson.length(); i++) {
                    movieArray.add(myJson.getJSONObject(i));
                }
                onPostExecute(movieArray);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    private void onPostExecute(ArrayList<JSONObject> result) {

        if (imageAdapter.posterIDs != null) {
            imageAdapter.posterIDs.clear();
        }
        //GET POSTER PATH
        for (int i = 0; i < result.size(); i++) {
            String pPath = result.get(i).toString();
            try {
                JSONObject n = new JSONObject(pPath);
                pPath = n.getString("poster_path");
                imageAdapter.posterIDs.add(pPath);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        imageAdapter.movieInfo = result;
    }

    public void sortMovies (final String sortBy) {
        //REFERENCE SORTING : http://stackoverflow.com/questions/21423526/how-to-sort-json-array-from-json-objects-in-android

        Collections.sort(movieArray, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                try {
                    return o2.getString(sortBy).compareTo(o1.getString(sortBy));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        onPostExecute(movieArray);
    }
}
