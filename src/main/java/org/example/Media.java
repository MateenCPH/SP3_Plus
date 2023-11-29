package org.example;

import java.util.ArrayList;

public class Media {

    private final String mediaName;
    private final String releaseDateStart;
    protected final ArrayList<String> genre;
    private final double rating;

    public Media(String mediaName, String releaseDateStart, ArrayList<String> genre, double rating) {
        this.mediaName = mediaName;
        this.releaseDateStart = releaseDateStart;
        this.genre = genre;
        this.rating = rating;
    }

    public String getMediaName() {
        return mediaName;
    }

    public String getReleaseDateStart() {
        return releaseDateStart;
    }

   /* public ArrayList<String> getGenre() {
        return genre.stream();
    }*/

    public double getRating() {
        return rating;
    }


    public String toString() {
        return " Release date: " + releaseDateStart + " | Genre: " + genre + " | Rating: " + getRating();
    }


}

