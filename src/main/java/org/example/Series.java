package org.example;

import java.util.ArrayList;
import java.util.Map;

public class Series extends Media {

    private final Map<Integer, Integer> episodesPerSeason;
    private final int endYear;

    public Series(int mediaID, ArrayList<String> genre, String mediaName, int releaseDateStart, int endYear, Map<Integer, Integer> episodesPerSeason, double rating) {
        super(mediaID, genre, mediaName, releaseDateStart, rating);
        this.endYear = endYear;
        this.episodesPerSeason = episodesPerSeason;
    }

    public void addToUserList() {
        //user.addToSavedSeries(ArrayList<>, series);
    }

    public void removeFromUserList() {

    }

    public Map<Integer, Integer> getEpisodesPerSeason() {
        return episodesPerSeason;
    }

    @Override
    public String toString() {
        return "Series name: " + getMediaName() + super.toString() + " | Episodes per season: " + episodesPerSeason;
    }
}
