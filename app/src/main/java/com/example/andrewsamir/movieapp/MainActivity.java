package com.example.andrewsamir.movieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.andrewsamir.movieapp.Adapters.MovieAdapter;
import com.example.andrewsamir.movieapp.Data.API_KEYS;
import com.example.andrewsamir.movieapp.Data.MovieData;
import com.example.andrewsamir.movieapp.jsonData.MoviesApi;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GridView gridView= (GridView) findViewById(R.id.gridView);

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        API_KEYS key=new API_KEYS();

        String url = "http://api.themoviedb.org/3/movie/popular?api_key="+key.api_key;

        StringRequest str = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        Gson gson=new Gson();
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();

                        MoviesApi movieData=gson.fromJson(response,MoviesApi.class);

                        ArrayList<MovieData> arrayList_movieData =new ArrayList<>();
                        for(int i=0;i>movieData.getResults().size();i++){

                            arrayList_movieData.add(new MovieData(movieData.getResults().get(i).getTitle(),
                                    movieData.getResults().get(i).getReleaseDate(),
                                    movieData.getResults().get(i).getPosterPath(),
                                    movieData.getResults().get(i).getOverview(),
                                    movieData.getResults().get(i).getVoteAverage()));
                        }

                        MovieAdapter movieAdapter=new MovieAdapter(arrayList_movieData,MainActivity.this);
                        gridView.setAdapter(movieAdapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
        queue.add(str);
    }
}
