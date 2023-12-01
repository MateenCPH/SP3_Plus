package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBConnector {
    //mysql:mysql-connector-java:RELEASE

    static final String DB_URL = "jdbc:mysql://localhost/my_streamingmjar";
    static final String USER = "root";
    static final String PASS = "Teknologisk2023!";
    protected final ArrayList<Media> media;

    public DBConnector() {
        this.media = new ArrayList<>();
    }

    public void readDataMovies() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting do database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //System.out.println("Creating statement...");
            String sql = "SELECT * FROM movie";
            stmt = conn.prepareStatement((sql));

            ResultSet rs = stmt.executeQuery();

            System.out.println("Printing result...");
            while (rs.next()) {
                int mediaID = rs.getInt("movieID");
                String[] genres = rs.getString("genre").trim().strip().split("\\.");
                ArrayList<String> genre = new ArrayList<>(List.of(genres));
                String name = rs.getString("name");
                int year = rs.getInt("year");
                double rating = rs.getDouble("rating");
                Movies movie = new Movies(mediaID, genre, name, year, rating);
                media.add(movie);
                //System.out.println(movie);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
        }
    }


    public void readDataSeries() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting do database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //System.out.println("Creating statement...");
            String sql = "SELECT * FROM series";
            stmt = conn.prepareStatement((sql));

            ResultSet rs = stmt.executeQuery();
            //TIL USER SKAL VI SKRIVE executeUpdate!! + vi skal ikke SELECT men INSERT INTO + vi skal ikke bruge getString men setString

            System.out.println("Printing result...");
            while (rs.next()) {
                int mediaID = rs.getInt("seriesID");
                String[] genres = rs.getString("genre").trim().strip().split("\\.");
                ArrayList<String> genre = new ArrayList<>(List.of(genres));
                String name = rs.getString("Name");
                int startYear = rs.getInt("startYear");
                int endYear = rs.getInt("endYear");

                String seasonsAndEpisodes = rs.getString("seasonsAndEpisode");
                /*Map<Integer, Integer> episodesPerSeason = new HashMap<>();
                String[] seasonEpisodesPairs = seasonsAndEpisodes.trim().split("\\.");
                for (String seasonEpisodesPair : seasonEpisodesPairs) {
                    String[] seasonEpisode = seasonEpisodesPair.split("-");
                    int seasonNumber = Integer.parseInt(seasonEpisode[0].trim());
                    int episodeNumber = Integer.parseInt(seasonEpisode[1].trim());
                    episodesPerSeason.put(seasonNumber, episodeNumber);*/

                    double rating = rs.getDouble("rating");
                    Series series = new Series(mediaID, genre, name, startYear, endYear, seasonsAndEpisodes, rating);
                    media.add(series);
                    //System.out.println(series);
                }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
        }
    }

    public ArrayList<Media> getMedia() {
        return media;
    }
}


