package org.example;


import java.util.ArrayList;
import java.util.Set;

public class Movies extends Media {

    public Movies(String mediaName, String releaseDate, ArrayList<String> genre, double rating) {
        super(mediaName, releaseDate, genre, rating);
    }

    public void addToUserList() {
        //user.addToWatchedMovies(ArrayList<>, movies);
    }

    public void removeFromUserList() {

    }

    @Override
    public String toString() {
        return "Movie name: " + getMediaName() + super.toString();
    }
}

