package com.huntongo.testupgradandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.huntongo.testupgradandroid.DetailPageActivity;
import com.huntongo.testupgradandroid.R;
import com.huntongo.testupgradandroid.core.MovieList;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by ashish on 18/6/16.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    ArrayList<MovieList> movieLists;
    Context mContext;

    public MovieAdapter(Context mContext, ArrayList<MovieList> movieLists) {
        this.movieLists = movieLists;
        this.mContext = mContext;

    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        DecimalFormat df = new DecimalFormat("#.##");
        WindowManager manager =(WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        Display disp = manager.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        int width = size.x/2;
        Picasso.with(mContext)
                .load(Uri.parse("https://image.tmdb.org/t/p/w185/" + movieLists.get(position).getPosterPath()))
                .resize(width, 0)
                .placeholder(R.drawable.movie_symbol)
                .into(holder.imageView);
        Double d = movieLists.get(position).getPopularity().doubleValue();
        holder.title.setText(movieLists.get(position).getTitle());
        holder.popularity.setText((df.format(d)).toString());
        holder.votecount.setText(movieLists.get(position).getVoteCount().toString());

    }

    @Override
    public int getItemCount() {
        return movieLists.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;
        private TextView popularity;
        private TextView votecount;

        public MovieViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            title = (TextView)itemView.findViewById(R.id.title);
            popularity = (TextView)itemView.findViewById(R.id.popularity);
            votecount = (TextView)itemView.findViewById(R.id.voteCount);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(mContext, DetailPageActivity.class);
                    int position = getPosition();
                    Bundle b =new Bundle();
                    b.putSerializable("Data",movieLists.get(position));
                    intent.putExtras(b);
                    mContext.startActivity(intent);
                }
            });

        }
    }
}
