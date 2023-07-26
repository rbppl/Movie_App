package com.example.movie_app;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie implements Parcelable {

    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("Poster")
    private String posterUrl;

    @SerializedName("Plot")
    private String plot;

    @SerializedName("Rating")
    private String rating;

    @SerializedName("ReleaseDate")
    private String releaseDate;

    @SerializedName("Actors")
    private List<String> actors;

    private boolean isFavorite;

    // Геттеры и сеттеры для полей

    public String getTitle() {
        return title;
    }


    public String getYear() {
        return year;
    }


    public String getPosterUrl() {
        return posterUrl;
    }


    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", plot='" + plot + '\'' +
                ", rating='" + rating + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", actors=" + actors +
                '}';
    }

    // Методы интерфейса Parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(year);
        dest.writeString(posterUrl);
        dest.writeString(plot);
        dest.writeString(rating);
        dest.writeString(releaseDate);
        dest.writeStringList(actors);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    // Constructor for reading from a Parcel
    private Movie(Parcel in) {
        title = in.readString();
        year = in.readString();
        posterUrl = in.readString();
        plot = in.readString();
        rating = in.readString();
        releaseDate = in.readString();
        actors = in.createStringArrayList();
        isFavorite = in.readByte() != 0;
    }
}
