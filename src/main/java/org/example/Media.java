package org.example;

import java.util.ArrayList;

public class Media {

    protected final int mediaID;
    private final String mediaName;
    private final int releaseDateStart;
    protected final ArrayList<String> genre;
    private final double rating;

    public Media(int mediaID, ArrayList<String> genre, String mediaName, int releaseDateStart, double rating) {
        this.mediaID = mediaID;
        this.genre = genre;
        this.mediaName = mediaName;
        this.releaseDateStart = releaseDateStart;
        this.rating = rating;
    }

    public String getMediaName() {
        return mediaName;
    }

    public int getReleaseDateStart() {
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

