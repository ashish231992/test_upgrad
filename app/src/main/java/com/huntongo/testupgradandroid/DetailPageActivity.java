package com.huntongo.testupgradandroid;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.huntongo.testupgradandroid.core.MovieList;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailPageActivity extends AppCompatActivity {

    private ImageView topView;
    private Toolbar toolbar;
    private CollapsingToolbarLayout mCoordinatorLayout;
    private MovieList movieList;
    private TextView synopsis;
    private TextView movie_name,original_title,release_date,popularity,vote_count,vote_avg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
        Bundle b= getIntent().getExtras();
        if(b!=null) {
            movieList = (MovieList) b.getSerializable("Data");
            if(movieList!=null){
                initViews();
            }
        }
    }

    public void initViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCoordinatorLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCoordinatorLayout.setTitle(movieList.getTitle());
        topView = (ImageView)findViewById(R.id.backdrop);
        synopsis = (TextView)findViewById(R.id.synopsis);
        movie_name = (TextView)findViewById(R.id.movie_name);
        original_title = (TextView)findViewById(R.id.original_title);
        release_date = (TextView)findViewById(R.id.release_date);
        popularity = (TextView)findViewById(R.id.popularity);
        vote_count = (TextView)findViewById(R.id.vote_count);
        vote_avg = (TextView)findViewById(R.id.vote_avg);

        setData();
    }
    public void setData(){
        Picasso.with(this).load(Uri.parse("https://image.tmdb.org/t/p/w185/"+movieList.getBackdropPath())).resize(getWidth(),0).into(topView);
        movie_name.setText(movieList.getTitle());
        original_title.setText(movieList.getOriginalTitle());
        release_date.setText(movieList.getReleaseDate());
        popularity.setText(movieList.getPopularity()+"");
        vote_count.setText(movieList.getVoteCount()+"");
        vote_avg.setText(movieList.getVoteAverage()+"");
        synopsis.setText(movieList.getOverview());
    }
    public int getWidth(){
        DecimalFormat df = new DecimalFormat("#.##");
        WindowManager manager =(WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        Display disp = manager.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        int width = size.x;

        return width;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return false;
    }
}
