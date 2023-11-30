package org.example;


import java.util.ArrayList;
import java.util.Set;

public class Movies extends Media {

    public Movies(int mediaID, ArrayList<String> genre, String mediaName, int releaseDate, double rating) {
        super(mediaID, genre, mediaName, releaseDate, rating);
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

