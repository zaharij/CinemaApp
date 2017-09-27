package com.centaurs.cinemaapp.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.centaurs.cinemaapp.R;

import static com.centaurs.cinemaapp.view.MainActivity.moviePresenter;

public class MoviesListFragment extends Fragment {
    private RecyclerView moviesRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid_movies, container, false);
        moviesRecyclerView = (RecyclerView) view.findViewById(R.id.movies_recyclerview);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return view;
    }

    class ListGridHolder extends RecyclerView.ViewHolder {
        private ImageView posterImageView;
        private TextView titleTextView, ratingTextView;

        public ListGridHolder(View view) {
            super(view);
            posterImageView = (ImageView) view.findViewById(R.id.poster_mini_imageView);
            titleTextView = (TextView) view.findViewById(R.id.title_grid_textView);
            ratingTextView = (TextView) view.findViewById(R.id.rating_grid_textView);
        }
    }

    class ListGridAdapter extends RecyclerView.Adapter<ListGridHolder>{

        @Override
        public ListGridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.movie_grid, parent, false);
            return new ListGridHolder(view);
        }

        @Override
        public void onBindViewHolder(ListGridHolder holder, int position) {
            moviePresenter.getMovie(position);
        }

        @Override
        public int getItemCount() {
            return moviePresenter.getMoviesNumber();
        }
    }
}
