package com.example.movie_app;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ImageButton backBtn = findViewById(R.id.backBtn);
        ImageView posterImageView = findViewById(R.id.posterImageView);
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView plotTextView = findViewById(R.id.plotTextView);
        TextView ratingTextView = findViewById(R.id.ratingTextView);
        TextView releaseDateTextView = findViewById(R.id.releaseDateTextView);
        RecyclerView actorsRecyclerView = findViewById(R.id.actorsRecyclerView);

        // Установка слушателя для кнопки "Back"
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Завершаем текущую активность и возвращаемся на предыдущий экран
            }
        });

        // Получение данных о фильме из переданных аргументов
        Movie movie = getIntent().getParcelableExtra("movie");
        if (movie != null) {
            // Заполнение информации о фильме в соответствующие элементы интерфейса
            titleTextView.setText(movie.getTitle());

            // Загрузка изображения с помощью библиотеки Picasso
            Picasso.get().load(movie.getPosterUrl()).into(posterImageView);

            // Загрузка дополнительных данных о фильме с помощью нового запроса к API
            ApiClient.getInstance().getMovieDetails(movie.getTitle(), new Callback<MovieDetail>() {
                @Override
                public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                    if (response.isSuccessful()) {
                        MovieDetail movieDetail = response.body();
                        if (movieDetail != null) {
                            // Здесь можно заполнить дополнительную информацию о фильме в интерфейсе
                            titleTextView.setText(movie.getTitle());
                            plotTextView.setText("Plot: " + movieDetail.getPlot());
                            ratingTextView.setText("Rating: " + movieDetail.getRating());
                            releaseDateTextView.setText("Release Date: " + movieDetail.getReleaseDate());

                            // Загрузка изображения с помощью библиотеки Picasso
                            Picasso.get().load(movie.getPosterUrl()).into(posterImageView);

                            // Отображение списка актеров
                            List<String> actorsList = getActorsList(movieDetail.getActors());
                            ActorAdapter actorsAdapter = new ActorAdapter(actorsList);
                            actorsRecyclerView.setLayoutManager(new LinearLayoutManager(MovieDetailActivity.this));
                            actorsRecyclerView.setAdapter(actorsAdapter);
                        } else {
                            // Обработка, если детальная информация о фильме не получена
                        }
                    } else {
                        // Обработка ошибки при запросе к API для детальной информации о фильме
                    }
                }

                @Override
                public void onFailure(Call<MovieDetail> call, Throwable t) {
                    // Обработка ошибки при запросе к API для детальной информации о фильме
                }
            });
        }
    }

    // Метод для разбивки строки с именами актеров на список отдельных имен
    private List<String> getActorsList(String actors) {
        return Arrays.asList(actors.split(", "));
    }
    private void updateFavoriteButton(boolean isFavorite) {
        ImageButton favoriteButton = findViewById(R.id.favoriteButton);
        if (isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_favorite_filled);
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite_border);
        }
    }
}
