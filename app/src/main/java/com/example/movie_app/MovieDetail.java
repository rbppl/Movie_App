package com.example.movie_app;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieDetail implements Parcelable {
    @SerializedName("Plot")
    private String plot;

    @SerializedName("imdbRating")
    private String rating;

    @SerializedName("Released")
    private String releaseDate;

    @SerializedName("Actors")
    private String actors;

    // Add other fields for detailed movie information as needed

    // Constructor, getter, and setter methods for the fields

    protected MovieDetail(Parcel in) {
        plot = in.readString();
        rating = in.readString();
        releaseDate = in.readString();
        actors = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(plot);
        dest.writeString(rating);
        dest.writeString(releaseDate);
        dest.writeString(actors);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieDetail> CREATOR = new Creator<MovieDetail>() {
        @Override
        public MovieDetail createFromParcel(Parcel in) {
            return new MovieDetail(in);
        }

        @Override
        public MovieDetail[] newArray(int size) {
            return new MovieDetail[size];
        }
    };

    public String getPlot() {
        return plot;
    }


    public String getRating() {
        return rating;
    }


    public String getReleaseDate() {
        return releaseDate;
    }


    public String getActors() {
        return actors;
    }


    // Parcelable implementation (remaining methods are the same)
}
