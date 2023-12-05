package org.example;

import java.util.*;
import java.util.HashMap;


public class Homepage {
    private final TextUI ui = new TextUI();
    private final User u = new User("Username", "Password");
    ArrayList<String> menuOptions = new ArrayList<>();
    ArrayList<String> subMenuActions = new ArrayList<>();
    DBConnector db = new DBConnector();
    private String userName2 = "";

    public Homepage() {
    }

    public void setup() {
        db.readUserData();
        db.readDataSeries();
        db.readDataMovies();
        mainMenuDialog_categoryMenu();
        playMovieDialog_categories();
        logInDialog();
    }


    public void logInDialog() {
        String input = "";
        ui.displayMsg("\nWelcome to ChillFlix");
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

        ui.displayMsg("");
        db.saveUserData(newUsername, newPassword);
        ui.displayMsg("New user created successfully");
        db.readUserData();
        loginAccount();
    }

    public void loginAccount() {
        String inputUserName = ui.getInput("Enter your username:");
        String inputPassword = ui.getInput("Enter your password:");

        for (User user : db.userList) {
            if (user.getUsername().equals(inputUserName) && user.getPassword().equals(inputPassword)) {
                ui.displayMsg("");
                userName2 = inputUserName;
                System.out.println("Welcome " + userName2);
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
        if (input.equalsIgnoreCase("1") || input.equalsIgnoreCase("Search Media")) { // søg efter en bestemt film
            mainMenuDialog_searchMedia();
        } else if (input.equalsIgnoreCase("2") || input.equalsIgnoreCase("Categorys")) { // søg efter alle genre
            mainMenuDialog_mediaCategory();
        } else if (input.equalsIgnoreCase("3") || input.equalsIgnoreCase("Watched Media")) { // alle sete film for brugeren
            mainMenuDialog_showWatchedMovies();
            String backInput = ui.getInput("[3] Back");
            if (backInput.equalsIgnoreCase("3")) {
                mainMenuDialog();
            }
        } else if (input.equalsIgnoreCase("4") || input.equalsIgnoreCase("Saved Media")) { // alle gemte film for brugeren
            mainMenuDialog_showSavedMedia();
            String backInput = ui.getInput("[3] Back");
            if (backInput.equalsIgnoreCase("3")) {
                mainMenuDialog();
            }
        } else if (input.equalsIgnoreCase("5") || input.equalsIgnoreCase("Logout")) {
            String logoutInput = ui.getInput("Are you sure you want to log out? Y/N");
            if (logoutInput.equalsIgnoreCase("y")) {
                ui.displayMsg("Logging out...");
                logInDialog();
            } else if (logoutInput.equalsIgnoreCase("N")) {
                ui.displayMsg("Returning to the menu...");
                mainMenuDialog();

            } else {
                ui.displayMsg("You didn't choose an existing category, try again noob");
                ui.displayMsg("");
                mainMenuDialog();
            }
        }
    }

    public void mainMenuDialog_categoryMenu() {
        menuOptions.add("[1] Search specific Media");
        menuOptions.add("[2] Category's");
        menuOptions.add("[3] Watched Media");
        menuOptions.add("[4] Saved Media");
        menuOptions.add("[5] Logout");
    }

    public void mainMenuDialog_searchMedia() {
        ui.displayMsg("");
        String input = ui.getInput("Choose a Media");

        boolean found = false; // Flag to check if movie is found

        for (Media media : db.getMedia()) {
            if (media.getMediaName().equalsIgnoreCase(input)) {
                playMovieDialog(media);
                found = true; // Set flag to true if movie is found
                break;
            }
        }

        if (!found) {
            ui.displayMsg("Could not find movie. Please try something else.");
            mainMenuDialog_searchMedia(); // Prompt again if movie is not found
        }
    }

    public void mainMenuDialog_mediaCategory() {
        Set<String> categories = new LinkedHashSet<>(); // Use LinkedHashSet to maintain order
        for (Media media : db.getMedia()) {
            categories.addAll(media.genre);
        }
        ui.displayMsg("");
        ui.displayMsg("Available Categories:");
        for (String category : categories) {
            ui.displayMsg("-" + category.trim());
        }

        ui.displayMsg("");
        String selectedCategory = ui.getInput("Pick a category: ");
        selectedCategory = selectedCategory.substring(0, 1).toUpperCase() + selectedCategory.substring(1);
        ui.displayMsg("");
        ui.displayMsg("Media in the '" + selectedCategory + "' category:");
        //String typedCategory = ui.getInput("Movies in the '" + selectedCategory + "' category:");
        boolean foundMedia = false;
        for (Media media : db.getMedia()) {
            if (media.genre.contains(selectedCategory)) {
                ui.displayMsg(media.toString());
                foundMedia = true;
            }
        }
        if (!foundMedia) {
            ui.displayMsg("No Media found in the '" + selectedCategory + "' category.");
        }

        ui.displayMsg("");
        while (true) {
            String chosenMovieFromCategory = ui.getInput("Enter the Media name you'd like to see: ");
            Media chosenMedia = null;
            for (Media media : db.getMedia()) {
                if (media.getMediaName().equalsIgnoreCase(chosenMovieFromCategory) && media.genre.contains(selectedCategory)) {
                    chosenMedia = media;
                    playMovieDialog(chosenMedia);
                    break;
                }
            }
            if (chosenMedia != null) {
                //playMovieDialog(chosenMovie);
                break;
            } else {
                ui.displayMsg("The media you have chosen is invalid. Please try again.");
            }
        }
    }


    public void mainMenuDialog_showWatchedMovies() {

        if (u.watchedMedia.isEmpty()) {
            ui.displayMsg("You have not watched any media yet\nReturning to the main menu");
            ui.displayMsg("");
            mainMenuDialog();
        } else {
            ui.displayMsg("these are the media you have seen");

            for (Media media : u.watchedMedia) {
                ui.displayMsg(media.toString());
            }
        }
    }

    public void mainMenuDialog_showSavedMedia() {
        if (u.savedMedia.isEmpty()) {
            ui.displayMsg(userName2 + ". You have not saved any media yet\nReturning to the main menu");
            ui.displayMsg("");
            mainMenuDialog();
        } else {
            ui.displayMsg(userName2 + ", these are the media you have saved");
            ui.displayMsg("");
            for (Media media : u.savedMedia) {
                ui.displayMsg(media.toString());
            }
            ui.displayMsg("");
        }
    }

    public void playMovieDialog(Media media) {

        ui.displayMsg(subMenuActions);
        String input = ui.getInput("");

        if (input.equalsIgnoreCase("1") || input.equalsIgnoreCase("Play media")) {
            ui.displayMsg(media.getMediaName() + " is now playing...");
            if (!u.watchedMedia.contains(media)) {
                u.watchedMedia.add(media);
            }
            ui.displayMsg("");
            ui.displayMsg("press 'x' to pause");
            String stopInput = ui.getInput("");
            if (stopInput.equalsIgnoreCase("x")) {
                ui.displayMsg("");
                subMenuActions.set(0, "[1] Resume Media");
                playMovieDialog(media);
            } else {
                ui.getInput("Please try again");
                playMovieDialog(media);
            }
        } else if (input.equalsIgnoreCase("2") || input.equalsIgnoreCase("Save media") || input.equalsIgnoreCase("Remove")) {
            if (!mediaSaved(media)) {
                ui.displayMsg(media.getMediaName() + " has been added to 'Saved Media'\nReturning to the menu...");
                subMenuActions.set(1, "[2] Remove Media from 'Saved Media'");

                ui.displayMsg("");
                u.addToSavedMedia(media);
                playMovieDialog(media);
            } else if (mediaSaved(media)) {
                subMenuActions.set(1, "[2] Save Media");
                ui.displayMsg(media.getMediaName() + " has been removed from 'Saved Media'\nReturning to the menu...");
                ui.displayMsg("");
                u.removeFromSavedMedia(media);
                playMovieDialog(media);
            }
        } else if (input.equalsIgnoreCase("3") || input.equalsIgnoreCase("Back")) {
            ui.displayMsg("Returning to the main menu...");
            subMenuActions.set(0, "[1] Play Media");
            mainMenuDialog();
        } else if (input.equalsIgnoreCase("4") || input.equalsIgnoreCase("Logout")) {
            String logoutInput = ui.getInput("Are you sure you want to log out? Y/N");
            if (logoutInput.equalsIgnoreCase("y")) {
                ui.displayMsg("Logging out...");
                logInDialog();
            } else if (logoutInput.equalsIgnoreCase("N")) {
                ui.displayMsg("Returning to the menu...");
                playMovieDialog(media);
            } else {
                subMenuActions.set(0, "[1] Play Media");
                subMenuActions.set(1, "[2] Save Media");
                playMovieDialog(media);
            }
        } else {
            ui.displayMsg("Please try again");
            subMenuActions.set(0, "[1] Play Media");
            subMenuActions.set(1, "[2] Save Media");
            playMovieDialog(media);
        }
    }

    public void playMovieDialog_categories() {
        subMenuActions.add("[1] Play Media");
        subMenuActions.add("[2] Save Media");
        subMenuActions.add("[3] Back");
        subMenuActions.add("[4] Logout");
    }

    public boolean mediaSaved(Media media) {
        if (u.savedMedia.contains(media)) {
            return true;
        } else {
            return false;
        }
    }
}
