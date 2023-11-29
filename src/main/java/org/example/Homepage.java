package org.example;

import java.util.*;
import java.util.HashMap;


public class Homepage {
    FileIO io = new FileIO();
    private final ArrayList<Movies> movies;
    private final ArrayList<Series> series;
    private final TextUI ui = new TextUI();
    private final User u = new User("Username", "Password");
    ArrayList<User> userList = new ArrayList<>();
    ArrayList<String> menuOptions = new ArrayList<>();
    ArrayList<String> subMenuActions = new ArrayList<>();

    public Homepage() {
        this.movies = new ArrayList<>();
        this.series = new ArrayList<>();
    }

    public void setup() {
        ArrayList<String> readUserData = io.readUserData("ChillFlix/src/main/java/data/userList.txt");
        ArrayList<User> users = new ArrayList<>();
        for (String s : readUserData) {
            String[] parts = s.split(",");
            String username = parts[0].trim();
            String password = parts[1].trim();

            userList.add(new User(username, password));
        }
        io.saveUserData(userList);

        ArrayList<String> movieData = io.readMediaData("C:\\Users\\matee\\Documents\\Intellij\\SP3-StudieGruppeA\\ChillFlix\\src\\main\\java\\data\\movies.txt");

        for (String s : movieData) {
            String[] row = s.split(";");
            String name = row[0].trim();
            String releaseDate = row[1].trim();
            String[] genres = row[2].trim().strip().split(",");
            ArrayList<String> genre = new ArrayList<>(List.of(genres));
            String ratingString = row[3].trim().replace(",", ".");
            double rating = Double.parseDouble(ratingString);

            registerMovies(name, releaseDate, genre, rating);
        }

        ArrayList<String> seriesData = io.readMediaData("C:\\Users\\matee\\Documents\\Intellij\\SP3-StudieGruppeA\\ChillFlix\\src\\main\\java\\data\\series.txt");
        for (String s : seriesData) {
            String[] row = s.split(";");
            String name = row[0];
            String runTime = row[1].trim();
            String[] genres = row[2].split(",");
            ArrayList<String> genre = new ArrayList<>(List.of(genres));

            String ratingString = row[3].trim().replace(",", ".");
            double rating = Double.parseDouble(ratingString);

            String seasonsAndEpisodes = row[4];
            Map<Integer, Integer> episodesPerSeason = new HashMap<>();
            String[] seasonEpisodesPairs = seasonsAndEpisodes.trim().split(",");
            for (String seasonEpisodesPair : seasonEpisodesPairs) {
                String[] seasonEpisode = seasonEpisodesPair.split("-");
                int seasonNumber = Integer.parseInt(seasonEpisode[0].trim());
                int episodeNumber = Integer.parseInt(seasonEpisode[1].trim());
                episodesPerSeason.put(seasonNumber, episodeNumber);
            }
            registerSeries(name, runTime, genre, rating, episodesPerSeason);
        }
        mainMenuDialog_categoryMenu();
        playMovieDialog_categories();
        logInDialog();
    }


    public void logInDialog() {
        String input = "";
        ui.displayMsg("Welcome to ChillFlix");
        ui.displayMsg("Would you like to login or create a new user?");
        input = ui.getInput("Press 'L' for Login or 'N' to create a new user");
        ui.displayMsg(" ");
        if (input.equalsIgnoreCase("N")) {
            createAccount();
        } else if (input.equalsIgnoreCase("L")) {
            loginAccount();
        } else {
            ui.displayMsg("Please only enter 'N' or 'L'");
            logInDialog();
        }
    }

    public void createAccount() {
        String newUsername = ui.getInput("Enter a new username:");
        String newPassword = ui.getInput("Enter a new password:");

        User newUser = new User(newUsername, newPassword);
        userList.add(newUser);
        io.saveUserData(userList);
        ui.displayMsg("");
        ui.displayMsg("New user created successfully");
        loginAccount();
    }

    public void loginAccount() {
        String inputUserName = ui.getInput("Enter your username:");
        String inputPassword = ui.getInput("Enter your password:");

        for (User user : userList) {
            if (user.getUsername().equals(inputUserName) && user.getPassword().equals(inputPassword)) {
                ui.displayMsg("");
                System.out.println("Welcome " + inputUserName);
                mainMenuDialog();
                return;
            }
        }
        ui.displayMsg("");
        System.out.println("Invalid username or password. Please try again.");
        loginAccount();
    }

    public void mainMenuDialog() {
        ui.displayMsg("Choose one of the following category's ");
        ui.displayMsg("");
        System.out.println(menuOptions);
        String input = ui.getInput("");
        if (input.equalsIgnoreCase("1") || input.equalsIgnoreCase("Search movie")) { // søg efter en bestemt film
            mainMenuDialog_searchMovie();
        } else if (input.equalsIgnoreCase("2") || input.equalsIgnoreCase("Categorys")) { // søg efter alle genre
            mainMenuDialog_movieCategory();
        } else if (input.equalsIgnoreCase("3") || input.equalsIgnoreCase("Watched Movies")) { // alle sete film for brugeren
            mainMenuDialog_showWatchedMovies();
        } else if (input.equalsIgnoreCase("4") || input.equalsIgnoreCase("Saved Movies")) { // alle gemte film for brugeren
            mainMenuDialog_showSavedMovies();
        } else {
            ui.displayMsg("You didn't choose an existing category, try again noob");
            ui.displayMsg("");
            mainMenuDialog();
        }
    }

    public void mainMenuDialog_categoryMenu() {
        menuOptions.add("[1] Search specific movie");
        menuOptions.add("[2] Category's");
        menuOptions.add("[3] Watched Movies");
        menuOptions.add("[4] Saved Movies");
    }

    public void mainMenuDialog_searchMovie() {
        ui.displayMsg("");
        String input = ui.getInput("Choose a movie");

        boolean found = false; // Flag to check if movie is found

        for (Movies movie : movies) {
            if (movie.getMediaName().equalsIgnoreCase(input)) {
                playMovieDialog(movie);
                found = true; // Set flag to true if movie is found
                break;
            }
        }

        if (!found) {
            ui.displayMsg("Could not find movie. Please try something else.");
            mainMenuDialog_searchMovie(); // Prompt again if movie is not found
        }
    }

    public void mainMenuDialog_movieCategory() {
        Set<String> categories = new LinkedHashSet<>(); // Use LinkedHashSet to maintain order
        for (Movies movie : movies) {
            categories.addAll(movie.genre);
        }
        ui.displayMsg("");
        ui.displayMsg("Available Categories:");
        for (String category : categories) {
            ui.displayMsg("- " + category);
        }

        ui.displayMsg("");
        String selectedCategory = ui.getInput("Pick a category: ");

        ui.displayMsg("");
        ui.displayMsg("Movies in the '" + selectedCategory + "' category:");
        boolean foundMovies = false;
        for (Movies movie : movies) {
            if (movie.genre.contains(selectedCategory)) {
                ui.displayMsg(movie.toString());
                foundMovies = true;
            }
        }
        if (!foundMovies) {
            ui.displayMsg("No movies found in the '" + selectedCategory + "' category.");
        }

        ui.displayMsg("");
        while (true) {
            String chosenMovieFromCategory = ui.getInput("Enter the movie name you'd like to see: ");
            Movies chosenMovie = null;
            for (Movies movie : movies) {
                if (movie.getMediaName().equalsIgnoreCase(chosenMovieFromCategory) && movie.genre.contains(selectedCategory)) {
                    chosenMovie = movie;
                    playMovieDialog(chosenMovie);
                    break;
                }
            }
            if (chosenMovie != null) {
                //playMovieDialog(chosenMovie);
                break;
            } else {
                ui.displayMsg("The movie you have chosen is invalid. Please try again.");
            }
        }
    }


    public void mainMenuDialog_showWatchedMovies() {

        if (u.watchedMovies.isEmpty()) {
            ui.displayMsg("You have not watched any movies yet\nReturning to the main menu");
            ui.displayMsg("");
            mainMenuDialog();
        } else {
            ui.displayMsg("this are the movies you have seen");

            for (Movies movie : u.watchedMovies) {
                ui.displayMsg(movie.toString());
            }
        }
    }

    public void mainMenuDialog_showSavedMovies() {
        if (u.savedMovies.isEmpty()) {
            ui.displayMsg(u.getUsername() + ". You have not saved any movies yet\nReturning to the main menu");
            ui.displayMsg("");
            mainMenuDialog();
        } else {
            ui.displayMsg(u.getUsername() + ", this are the movies you have saved");

            for (Movies movie : u.savedMovies) {
                ui.displayMsg(movie.toString());
            }
        }
    }

    public void playMovieDialog(Movies movie) {

        System.out.println(subMenuActions);
        String input = ui.getInput("");

        if (input.equalsIgnoreCase("1") || input.equalsIgnoreCase("Play movie")) {
            ui.displayMsg(movie.getMediaName() + " is now playing...");
            ui.displayMsg("");
            ui.displayMsg("press 'x' to pause");
            String stopInput = ui.getInput("");
            if (stopInput.equalsIgnoreCase("x")) {
                ui.displayMsg("");
                subMenuActions.set(0, "[1] Resume movie");
                playMovieDialog(movie);
            } else {
                ui.getInput("Please try again");
                playMovieDialog(movie);
            }
        } else if (input.equalsIgnoreCase("2") || input.equalsIgnoreCase("Save movie") || input.equalsIgnoreCase("Remove")) {
            if (!movieSaved(movie)) {
                ui.displayMsg(movie.getMediaName() + " has been added to 'Saved movies'\nReturning to the menu...");
                subMenuActions.set(1, "[2] Remove movie from 'Saved list'");

                ui.displayMsg("");
                u.addToSavedMovies(movie);
                playMovieDialog(movie);
            } else if (movieSaved(movie)) {
                subMenuActions.set(1, "[2] Save Movie");
                ui.displayMsg(movie.getMediaName() + " has been removed from 'Saved moves'\nReturning to the menu...");
                ui.displayMsg("");
                u.removeFromSavedMovies(movie);
                playMovieDialog(movie);
            }
        } else if (input.equalsIgnoreCase("3") || input.equalsIgnoreCase("Back")) {
            ui.displayMsg("Returning to the main menu...");
            subMenuActions.set(0, "[1] Play Movie");
            mainMenuDialog();
        } else if (input.equalsIgnoreCase("4") || input.equalsIgnoreCase("Logout")) {
            String logoutInput = ui.getInput("Are you sure you want to log out? Y/N");
            if (logoutInput.equalsIgnoreCase("y")) {
                ui.displayMsg("Logging out...");
                logInDialog();
            } else if (input.equalsIgnoreCase("N")) {
                ui.displayMsg("Returning to the menu...");
                playMovieDialog(movie);
            } else {
                subMenuActions.set(0, "[1] Play Movie");
                subMenuActions.set(1, "[2] Save Movie");
                playMovieDialog(movie);
            }
        } else {
            ui.displayMsg("Please try again");
            subMenuActions.set(0, "[1] Play Movie");
            subMenuActions.set(1, "[2] Save Movie");
            playMovieDialog(movie);
        }
    }

    public void playMovieDialog_categories() {
        subMenuActions.add("[1] Play movie");
        subMenuActions.add("[2] Save Movie");
        subMenuActions.add("[3] Back");
        subMenuActions.add("[4] Logout");
    }

    public boolean movieSaved(Movies movie) {
        if (u.savedMovies.contains(movie)) {
            return true;
        } else {
            return false;
        }
    }

    private void registerMovies(String name, String releaseDate, ArrayList<String> genres, double rating) {
        Movies m = new Movies(name, releaseDate, genres, rating);
        movies.add(m);
    }

    private void registerSeries(String seriesName, String releaseDateStart, ArrayList<String> genre, double rating, Map<Integer, Integer> episodesPerSeason) {
        Series s = new Series(seriesName, releaseDateStart, genre, rating, episodesPerSeason);
        series.add(s);
    }

    public ArrayList<Movies> getMovies() {
        return movies;
    }
}
