package org.example;
import java.util.ArrayList;
import java.util.Scanner;


public class TextUI {
    private Scanner scan = new Scanner(System.in);

    public String getInput(String msg){
        displayMsg(msg);
        return scan.nextLine();
    }

    public void displayMsg(String msg){
        System.out.println(msg);
    }

    public void displayMsg(ArrayList<String> list){
        System.out.println(list);
    }


}
