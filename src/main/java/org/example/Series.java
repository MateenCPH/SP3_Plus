package org.example;

import java.util.ArrayList;
import java.util.Map;

public class Series extends Media {

    private final String episodesPerSeason;
    private final int endYear;

    public Series(int mediaID, ArrayList<String> genre, String mediaName, int releaseDateStart, int endYear, String episodesPerSeason, double rating) {
        super(mediaID, genre, mediaName, releaseDateStart, rating);
        this.endYear = endYear;
        this.episodesPerSeason = episodesPerSeason;
    }



    public String getEpisodesPerSeason() {
        return episodesPerSeason;
    }

    @Override
    public String toString() {
        return "Series name: " + getMediaName() + super.toString() + " | Episodes per season: " + episodesPerSeason;
    }
}
