package com.centaurs.cinemaapp.model;


import android.os.AsyncTask;
import android.util.Log;

import com.centaurs.cinemaapp.view.MainActivity;

import java.util.HashMap;
import java.util.Map;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

import static com.centaurs.cinemaapp.presenter.MoviePresenter.DEFAULT_SIZE;

public class MovieLoader extends AsyncTask<Integer, Void, Map<MovieFieldsEnum, String>> {
    private final String LICENSE_KAY = "ab5fd4c24928a00b2f0d09efdcdaa083";
    private final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    public final static int EN = 0;
    private String country;
    private TmdbMovies tmdbMovies;
    private MovieResultsPage movie;
    private Map<MovieFieldsEnum, String> movieMap;
    private int movieWidth;
    private int listMoviesSize;

    public Map<MovieFieldsEnum, String> getMovieMap(){
        return movieMap;
    }

    @Override
    protected Map<MovieFieldsEnum, String> doInBackground(Integer... integers) {
        Log.d("******", "asynk in backgr");
        if (integers.length >= 2) {
            this.country = getCountry(integers[1]);
        } else {
            this.country = getCountry(EN);
        }
        if (integers.length >= 3) {
            this.movieWidth = integers[2];
        } else {
            this.movieWidth = DEFAULT_SIZE;
        }
        tmdbMovies = new TmdbApi(LICENSE_KAY).getMovies();
        TmdbMovies movies = new TmdbApi(LICENSE_KAY).getMovies();
        try{
            movie = movies.getTopRatedMovies(country, integers[0]);
        }catch (IndexOutOfBoundsException ex) {
            movie = movies.getTopRatedMovies(country, 0);
        }

        if (integers.length >= 1) {
            Map<MovieFieldsEnum, String> movieMap = new HashMap<>();
            movieMap.put(MovieFieldsEnum.OVERVIEW, movie.getResults().get(integers[0]).getOverview());
            movieMap.put(MovieFieldsEnum.DATE, movie.getResults().get(integers[0]).getReleaseDate());
            movieMap.put(MovieFieldsEnum.RATING, String.valueOf(movie.getResults().get(integers[0]).getVoteAverage()));
            movieMap.put(MovieFieldsEnum.TITLE, movie.getResults().get(integers[0]).getTitle());
            movieMap.put(MovieFieldsEnum.GENRE, getGenres(movies, integers[0]));
            String posterUrl = POSTER_BASE_URL.concat(getPosterWidthSize(integers[2])).concat(movie.getResults().get(integers[0]).getPosterPath());
            ;
            movieMap.put(MovieFieldsEnum.POSTER_URL, posterUrl);
            return movieMap;
        } else {
            listMoviesSize = movie.getResults().size();
            return null;
        }

    }

    @Override
    protected void onPostExecute(Map<MovieFieldsEnum, String> movieResultMap) {
        super.onPostExecute(movieResultMap);
        Log.d("******", "on post exec");
        if (movieResultMap != null){
            movieMap = movieResultMap;
            MainActivity.moviePresenter.updat(movieMap);
        } else {
            MainActivity.moviePresenter.setMoviesNumber(listMoviesSize);
        }
    }

    private String getCountry(int countryCode){
        switch (countryCode){
            case EN:
                return "en";
            default:
                return "en";
        }
    }

    private String getGenres(TmdbMovies movies, int movieId){
        MovieDb movie1 = movies.getMovie(movie.getResults().get(movieId).getId(), country);
        StringBuilder movieGenresBuilder = new StringBuilder();
        for (int i = 0; i < movie1.getGenres().size(); i++){
            if (i != 0 && i < movie1.getGenres().size() - 1){
                movieGenresBuilder.append(", ");
            }
            movieGenresBuilder.append(movie1.getGenres().get(i));
        }
        return movieGenresBuilder.toString();
    }


    private String getPosterWidthSize(){
        String posterWidthStr = "w300/";
        return posterWidthStr;
    }

    private String getPosterWidthSize(int width){
        String posterWidthStr = "w" + width + "/";
        return posterWidthStr;
    }
}
