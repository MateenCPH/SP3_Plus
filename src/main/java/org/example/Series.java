package org.example;

import java.util.ArrayList;
import java.util.Map;

public class Series extends Media {

    private final Map<Integer, Integer> episodesPerSeason;

    public Series(int mediaID, ArrayList<String> genre, String mediaName, int releaseDateStart, double rating, Map<Integer, Integer> episodesPerSeason) {
        super(mediaID, genre, mediaName, releaseDateStart, rating);
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
