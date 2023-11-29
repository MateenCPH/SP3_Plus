package org.example;

import java.sql.*;

public class DBConnector {
    //mysql:mysql-connector-java:RELEASE

    static final String DB_URL = "jdbc:mysql://localhost/my_streamingmjar";
    static final String USER = "root";
    static final String PASS = "Teknologisk2023!";

    public void readData() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting do database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement...");
            String sql = "SELECT * FROM movie";
            stmt = conn.prepareStatement((sql));

            ResultSet rs = stmt.executeQuery();

            System.out.println("Printing result...");
            while (rs.next()) {
                int mediaID = rs.getInt("movieID");
                String genre = rs.getString("genre");
                String name = rs.getString("name");
                int year = rs.getInt("year");
                double rating = rs.getDouble("rating");
                Movies movie = new Movies(mediaID, name, genre, year, rating);
                System.out.println(movie);
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
}


