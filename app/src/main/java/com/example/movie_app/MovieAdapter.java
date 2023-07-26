package com.example.movie_app;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movieList;
    private OnMovieItemClickListener listener;



    public interface OnMovieItemClickListener {
        void onMovieItemClick(Movie movie);
    }

    public MovieAdapter(List<Movie> movieList, OnMovieItemClickListener listener) {
        this.movieList = movieList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Movie movie = movieList.get(position);
        holder.titleTextView.setText(movie.getTitle());
        holder.yearTextView.setText(movie.getYear());

        // Загрузка изображения с помощью Picasso
        Picasso.get().load(movie.getPosterUrl()).into(holder.posterImageView);

        // Обработчик нажатия на элемент списка
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onMovieItemClick(movie);
                }
            }
        });

        // Обработчик нажатия на иконку избранное
        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Изменить состояние isFavorite у фильма
                movie.setFavorite(!movie.isFavorite());
                notifyItemChanged(position);
            }
        });

        // Установить цвет картинки в зависимости от состояния "избранного"
        if (movie.isFavorite()) {
            holder.favoriteButton.setImageResource(R.drawable.ic_favorite_filled);
        } else {
            holder.favoriteButton.setImageResource(R.drawable.ic_favorite_border);
        }
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImageView;
        private TextView titleTextView;
        private TextView yearTextView;
        private ImageButton favoriteButton;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.posterImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }
    }
}
