package com.example.movie_app;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/")
    Call<MoviesResponse> searchMovies(
            @Query("apikey") String apiKey,
            @Query("s") String searchTerm
    );
    @GET("/")
    Call<MovieDetail> getMovieDetails(
            @Query("apikey") String apiKey,
            @Query("t") String movieTitle
    );
}
