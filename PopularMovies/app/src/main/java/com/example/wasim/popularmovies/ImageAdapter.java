package com.example.wasim.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {return posterIDs.size();}

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public ArrayList<String> posterIDs = new ArrayList<>();

    public ArrayList<JSONObject> movieInfo = new ArrayList<>();

    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        //REFERENCE: https://developer.android.com/guide/topics/ui/layout/gridview.htmlST
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        String url = "http://image.tmdb.org/t/p/w185/" + posterIDs.get(position);
        Picasso.with(imageView.getContext()).load(url).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Context context = v.getContext();
            Intent intent = new Intent(context, MovieDetail.class);
                //REF: http://stackoverflow.com/questions/5082122/passing-jsonobject-into-another-activity
                intent.putExtra("info",movieInfo.get(position).toString());
            context.startActivity(intent);
            }
        });
        return imageView;
    }

}