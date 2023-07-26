package com.example.movie_app;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiClient {

    private static final String BASE_URL = "http://www.omdbapi.com/";
    private static final String API_KEY = "df7bf2f8";

    private static ApiClient instance;
    private ApiService apiService;

    private ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static synchronized ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public void searchMovies(String searchTerm, Callback<MoviesResponse> callback) {
        apiService.searchMovies(API_KEY, searchTerm).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    MoviesResponse moviesResponse = response.body();
                    if (moviesResponse != null && moviesResponse.getMovies() != null) {
                        List<Movie> movies = moviesResponse.getMovies();
                        callback.onResponse(call, response);
                    } else {
                        callback.onFailure(call, new Throwable("No movies found"));
                    }
                } else {
                    callback.onFailure(call, new Throwable("Failed to fetch movies"));
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void getMovieDetails(String movieTitle, Callback<MovieDetail> callback) {
        apiService.getMovieDetails(API_KEY, movieTitle).enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                if (response.isSuccessful()) {
                    MovieDetail movieDetail = response.body();
                    if (movieDetail != null) {
                        callback.onResponse(call, response);
                    } else {
                        callback.onFailure(call, new Throwable("No movie details found"));
                    }
                } else {
                    callback.onFailure(call, new Throwable("Failed to fetch movie details"));
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }
}
