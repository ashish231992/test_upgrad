package com.huntongo.testupgradandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.huntongo.testupgradandroid.adapter.MovieAdapter;
import com.huntongo.testupgradandroid.core.MovieList;
import com.huntongo.testupgradandroid.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private RequestQueue mRequestQueue;
    private JSONArray results;
    private ArrayList<MovieList> movieLists;
    private ArrayList<MovieList> movieListsBuffer;
    private MovieList movieList;
    private MovieAdapter movieAdapter;
    private int i = 0;
    private int init = 1;
    private boolean is_loading = true;
    private ImageView sortList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initDataRequest();
    }

    public void initViews() {
        movieLists = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        movieAdapter = new MovieAdapter(this, movieLists);
        sortList = (ImageView)findViewById(R.id.sort);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int count = gridLayoutManager.getChildCount();
                int count1 = gridLayoutManager.getItemCount();
                int count3 = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                if (count + count3 > count1 + 5) {
                    init += 1;
                    if (init < i) {
                        initDataRequestAgain(init);
                    }
                }
            }
        });
        sortList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(movieLists);
                movieAdapter.notifyDataSetChanged();
            }
        });

    }

    public void initRecyclerView() {

        recyclerView.setAdapter(movieAdapter);
    }

    public void initDataRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        String Url = Constants.TMDB_URL + Constants.API_KEY + "1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    results = response.getJSONArray("results");
                    i = response.getInt("total_pages");
                    JSONObject mJsonObject;
                    for (int i = 0; i < results.length(); i++) {
                        movieList = new MovieList();
                        mJsonObject = results.getJSONObject(i);
                        movieList.setPosterPath(mJsonObject.getString("poster_path"));
                        movieList.setAdult(mJsonObject.getBoolean("adult"));
                        movieList.setOverview(mJsonObject.getString("overview"));
                        movieList.setReleaseDate(mJsonObject.getString("release_date"));
                        movieList.setId(mJsonObject.getInt("id"));
                        movieList.setOriginalTitle(mJsonObject.getString("original_title"));
                        movieList.setOriginalLanguage(mJsonObject.getString("original_language"));
                        movieList.setTitle(mJsonObject.getString("title"));
                        movieList.setBackdropPath(mJsonObject.getString("backdrop_path"));
                        movieList.setPopularity(mJsonObject.getDouble("popularity"));
                        movieList.setVoteCount(mJsonObject.getInt("vote_count"));
                        movieList.setVideo(mJsonObject.getBoolean("video"));
                        movieList.setVoteAverage(mJsonObject.getDouble("vote_average"));
                        movieLists.add(movieList);

                    }

                    initRecyclerView();

                } catch (Exception e) {
                    Log.d("Excception", e.toString());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(jsonObjectRequest);
    }

    public void initDataRequestAgain(int page) {
        is_loading = true;
        movieListsBuffer = new ArrayList<>();

        String Url = Constants.TMDB_URL + Constants.API_KEY + page;
        Log.d("StringUrl",Url);
        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    results = response.getJSONArray("results");
                    JSONObject mJsonObject;
                    for (int i = 0; i < results.length(); i++) {
                        movieList = new MovieList();
                        mJsonObject = results.getJSONObject(i);
                        movieList.setPosterPath(mJsonObject.getString("poster_path"));
                        movieList.setAdult(mJsonObject.getBoolean("adult"));
                        movieList.setOverview(mJsonObject.getString("overview"));
                        movieList.setReleaseDate(mJsonObject.getString("release_date"));
                        movieList.setId(mJsonObject.getInt("id"));
                        movieList.setOriginalTitle(mJsonObject.getString("original_title"));
                        movieList.setOriginalLanguage(mJsonObject.getString("original_language"));
                        movieList.setTitle(mJsonObject.getString("title"));
                        movieList.setBackdropPath(mJsonObject.getString("backdrop_path"));
                        movieList.setPopularity(mJsonObject.getDouble("popularity"));
                        movieList.setVoteCount(mJsonObject.getInt("vote_count"));
                        movieList.setVideo(mJsonObject.getBoolean("video"));
                        movieList.setVoteAverage(mJsonObject.getDouble("vote_average"));
                        movieListsBuffer.add(movieList);

                    }
                    movieLists.addAll(movieListsBuffer);
                    movieAdapter.notifyDataSetChanged();

                } catch (Exception e) {
                    Log.d("Excception", e.toString());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(jsonObjectRequest1);
    }

}
