package org.example;

import java.util.ArrayList;
import java.util.Map;

public class User {
    private String username;
    private String password;
    private final TextUI ui = new TextUI();
    protected ArrayList<Media> watchedMedia;
    protected final ArrayList<Media> savedMedia;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.watchedMedia = new ArrayList<>();
        this.savedMedia = new ArrayList<>();
    }

    public void registerUser() {
        System.out.println("Please create an account with a username & password.");
    }

    public void playMovie(Movies movie) {
        ui.displayMsg(movie.getMediaName() + " is now playing...");

        //playMovieDialog();
    }

    public void addToWatchedMedia(Media media) {
        watchedMedia.add(media);
    }

    public void addToSavedMedia(Media media) {
        savedMedia.add(media);
    }

    public void removeFromSavedMedia(Media media) {
        savedMedia.remove(media);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}