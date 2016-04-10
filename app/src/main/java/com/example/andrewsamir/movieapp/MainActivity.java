package com.example.andrewsamir.movieapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.andrewsamir.movieapp.Adapters.DBhelper;
import com.example.andrewsamir.movieapp.Adapters.MovieAdapter;
import com.example.andrewsamir.movieapp.Data.KEYS;
import com.example.andrewsamir.movieapp.Data.MovieData;
import com.example.andrewsamir.movieapp.jsonData.MoviesApi;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<MovieData> arrayList_movieData;
    DBhelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);
        arrayList_movieData = new ArrayList<>();


        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        KEYS key = new KEYS();

        String url = "http://api.themoviedb.org/3/movie/popular?api_key=" + key.api_key;

        StringRequest str = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Gson gson = new Gson();

                        MoviesApi movieData = gson.fromJson(response, MoviesApi.class);

                        for (int i = 0; i < movieData.getResults().size(); i++) {

                            arrayList_movieData.add(new MovieData(movieData.getResults().get(i).getId(),
                                    movieData.getResults().get(i).getTitle(),
                                    movieData.getResults().get(i).getReleaseDate(),
                                    movieData.getResults().get(i).getPosterPath(),
                                    movieData.getResults().get(i).getOverview(),
                                    movieData.getResults().get(i).getVoteAverage()));
                        }

                        final MovieAdapter movieAdapter = new MovieAdapter(arrayList_movieData, MainActivity.this);
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

        create();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.favourite:

                myDB = new DBhelper(MainActivity.this);

                Cursor cursor = myDB.getMoviess();
                if (cursor.moveToFirst()) {
                    arrayList_movieData.clear();
                    do {

                        arrayList_movieData.add(new MovieData(cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                Double.parseDouble(cursor.getString(5))));

                    } while (cursor.moveToNext());


                    MovieAdapter movieAdapter = new MovieAdapter(arrayList_movieData, MainActivity.this);
                    gridView.setAdapter(movieAdapter);

                }
                create();
                return true;


            case R.id.popular:
                arrayList_movieData.clear();


                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                KEYS key = new KEYS();

                String url = "http://api.themoviedb.org/3/movie/popular?api_key=" + key.api_key;

                StringRequest str = new StringRequest(url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                Gson gson = new Gson();

                                MoviesApi movieData = gson.fromJson(response, MoviesApi.class);

                                for (int i = 0; i < movieData.getResults().size(); i++) {

                                    arrayList_movieData.add(new MovieData(movieData.getResults().get(i).getId(),
                                            movieData.getResults().get(i).getTitle(),
                                            movieData.getResults().get(i).getReleaseDate(),
                                            movieData.getResults().get(i).getPosterPath(),
                                            movieData.getResults().get(i).getOverview(),
                                            movieData.getResults().get(i).getVoteAverage()));
                                }

                                MovieAdapter movieAdapter = new MovieAdapter(arrayList_movieData, MainActivity.this);
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
                create();
                return true;


            case R.id.topRated:
                arrayList_movieData.clear();


                RequestQueue queue2 = Volley.newRequestQueue(MainActivity.this);

                KEYS key2 = new KEYS();

                String url2 = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + key2.api_key;

                StringRequest str2 = new StringRequest(url2,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                Gson gson = new Gson();

                                MoviesApi movieData = gson.fromJson(response, MoviesApi.class);

                                for (int i = 0; i < movieData.getResults().size(); i++) {

                                    arrayList_movieData.add(new MovieData(movieData.getResults().get(i).getId(),
                                            movieData.getResults().get(i).getTitle(),
                                            movieData.getResults().get(i).getReleaseDate(),
                                            movieData.getResults().get(i).getPosterPath(),
                                            movieData.getResults().get(i).getOverview(),
                                            movieData.getResults().get(i).getVoteAverage()));
                                }

                                MovieAdapter movieAdapter = new MovieAdapter(arrayList_movieData, MainActivity.this);
                                gridView.setAdapter(movieAdapter);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                queue2.add(str2);
                create();
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    public void create() {


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(MainActivity.this, Show_Movie.class);
                intent.putExtra("id", arrayList_movieData.get(position).getId());
                intent.putExtra("title", arrayList_movieData.get(position).getName());
                intent.putExtra("relasedate", arrayList_movieData.get(position).getRelase_date());
                intent.putExtra("overview", arrayList_movieData.get(position).getOverview());
                intent.putExtra("rate", arrayList_movieData.get(position).getAvg_vote());
                intent.putExtra("image", arrayList_movieData.get(position).getImage_path());
                startActivity(intent);
            }
        });
    }
}
