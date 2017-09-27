package com.centaurs.cinemaapp.presenter;


import android.util.Log;

import com.centaurs.cinemaapp.model.MovieFieldsEnum;
import com.centaurs.cinemaapp.model.MovieLoader;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class MoviePresenter extends Observable {
    public final static int EN = 0;
    public final static int DEFAULT_SIZE = 500;
    private MovieLoader movieLoader;
    private int country;
    private Map<MovieFieldsEnum, String> movieMap;
    private int size;
    private int moviesNumber;

    public MoviePresenter(Observer observer, int country, int size){
        this.addObserver(observer);
        this.country = country;
        this.size = size;
        movieLoader = new MovieLoader();
        movieLoader.execute();
    }

    public int getMoviesNumber(){
        return moviesNumber;
    }

    public void getMovie(int id){
        Log.d("******", "get movie presenter");
        movieLoader = new MovieLoader();
        movieLoader.execute(id, country, size);
    }

    public void updat(Map<MovieFieldsEnum, String> movieMap){
        Log.d("******", "update");
        this.setChanged();
        this.notifyObservers(movieMap);
    }

    public void setMoviesNumber(int number){
        moviesNumber = number;
    }
}
