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
}