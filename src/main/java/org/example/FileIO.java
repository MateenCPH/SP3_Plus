package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO {

    public ArrayList<String> readMediaData(String path){
        ArrayList<String> data = new ArrayList<>();
        //Instantier filerne
        File file = new File(path);

        try {
            Scanner scan = new Scanner(file);
            scan.nextLine();
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                data.add(s);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return data;
    }

    public static void saveUserData(ArrayList<User> users){
        try {
            FileWriter writer = new FileWriter("C:\\Users\\matee\\Documents\\Intellij\\SP3_Plus\\src\\main\\java\\data\\userList.txt");
            writer.write("Name,Password" + "\n");
            for (User user : users) {
                String textTosave = user.getUsername() + "," + user.getPassword();
                //String movieToSave = user;
                writer.write(textTosave + "\n");
            }
            writer.close();
        } catch(IOException e)
        {
            System.out.println("Something went wrong?");
            e.printStackTrace();
        }
    }

    public ArrayList<String> readUserData(String path) {
        ArrayList<String> data = new ArrayList<>();
        //instantier File
        File file = new File(path);

        try {
            Scanner scan = new Scanner(file);
            scan.nextLine();
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                data.add(s);
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
        return data;
    }


}

