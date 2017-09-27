package com.centaurs.cinemaapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.centaurs.cinemaapp.R;
import com.centaurs.cinemaapp.model.MovieFieldsEnum;
import com.centaurs.cinemaapp.model.MovieLoader;
import com.centaurs.cinemaapp.presenter.MoviePresenter;
import com.squareup.picasso.Picasso;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity {
    private TextView test;
    private Button getButton;
    private ImageView imageView;
    public static MoviePresenter moviePresenter;
    private Observer observer;
    private Map<MovieFieldsEnum, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        observer = new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                Log.d("******", "observer update");
                map = (Map<MovieFieldsEnum, String>) o;
                String teststr = map.get(MovieFieldsEnum.TITLE);
                test.setText(teststr);
                Picasso.with(MainActivity.this).load(map.get(MovieFieldsEnum.POSTER_URL)).into(imageView);
            }
        };

        moviePresenter = new MoviePresenter(observer, MovieLoader.EN, MoviePresenter.DEFAULT_SIZE);
        imageView = (ImageView) findViewById(R.id.imageView);

        test = (TextView) findViewById(R.id.test_text_view);
        getButton = (Button) findViewById(R.id.button_get);
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("******", "clicked");
                moviePresenter.getMovie(18);
            }
        });
    }
}
