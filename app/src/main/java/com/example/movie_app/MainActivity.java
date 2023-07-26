package com.example.movie_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieItemClickListener {

    private EditText searchEditText;
    private MovieAdapter movieAdapter;

    private List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.searchEditText);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        movieAdapter = new MovieAdapter(movieList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(movieAdapter);

        // Обработчик для ввода текста в EditText
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Выполнять поиск фильмов при каждом изменении текста в EditText
                String searchTerm = charSequence.toString().trim();
                if (!searchTerm.isEmpty()) {
                    searchMovies(searchTerm);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void searchMovies(String searchTerm) {
        ApiClient.getInstance().searchMovies(searchTerm, new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    MoviesResponse moviesResponse = response.body();
                    if (moviesResponse != null && moviesResponse.getMovies() != null) {
                        List<Movie> movies = moviesResponse.getMovies();
                        // Обновить адаптер с отсортированным списком фильмов
                        movieList.clear();
                        movieList.addAll(movies);
                        movieAdapter.notifyDataSetChanged();

                        // Вывести информацию о каждом фильме в Logcat
                        for (Movie movie : movies) {
                            Log.d("MovieInfo", "Title: " + movie.getTitle());
                            Log.d("MovieInfo", "Year: " + movie.getYear());
                            Log.d("MovieInfo", "Poster URL: " + movie.getPosterUrl());
                            // Выводить остальные поля, если необходимо
                        }
                    } else {
                        Log.e("MovieInfo", "No movies found");
                    }
                } else {
                    Log.e("MovieInfo", "Failed to fetch movies");
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e("MovieInfo", "Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void onMovieItemClick(Movie movie) {
        // Make another API request using Retrofit to get more details about the selected movie
        ApiClient.getInstance().getMovieDetails(movie.getTitle(), new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                if (response.isSuccessful()) {
                    MovieDetail movieDetail = response.body();
                    if (movieDetail != null) {
                        // Successfully fetched movie details
                        // Pass the movie object and its details to MovieDetailActivity
                        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                        intent.putExtra("movie", movie);
                        intent.putExtra("movieDetail", movieDetail);
                        startActivity(intent);
                    } else {
                        // Handle the case when movieDetail is null or empty
                        Log.e("MovieInfo", "No movie details found");
                    }
                } else {
                    // Handle API response error
                    Log.e("MovieInfo", "Failed to fetch movie details");
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                // Handle API request failure
                Log.e("MovieInfo", "Error: " + t.getMessage());
            }
        });
    }
}
