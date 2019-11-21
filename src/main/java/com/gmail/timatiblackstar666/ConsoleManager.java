package com.gmail.timatiblackstar666;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsoleManager {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    private Map<String, String> allCommand = new HashMap<>();
    private String userName = "Guest";

    public ConsoleManager() {
    }

    public ConsoleManager(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void addNewCommand(String command, String description){
        if (command != null)
            allCommand.put(command, description);
    }

    private void initCommandList(){
        addNewCommand("exit", "Exit");
        addNewCommand("help", "Show list of available commands");
        addNewCommand("time", "Show current date and time");
        addNewCommand("clear", "Clear console display");
    }

    private void cleanAndExit(int code){
        clearConsole();
        System.exit(code);
    }

    private void drawLoading(){
        try {

            System.out.print("Loading ");
            Thread.sleep(500);
            System.out.print('.');
            Thread.sleep(500);
            System.out.print('.');
            Thread.sleep(500);
            System.out.print('.');
            Thread.sleep(250);
            System.out.println(" Done");
            Thread.sleep(500);
            clearConsole();
        }catch (InterruptedException e){
            e.printStackTrace();
            cleanAndExit(1);
        }
    }

    private void printCurrentTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println(ANSI_BLUE+formatter.format(new Date())+ANSI_RESET);
    }

    private void executeCommand(String command){
        switch (command){
            case "exit":{
                cleanAndExit(0);
                break;
            }
            case "clear":{
                clearConsole();
                break;
            }
            case "time":{
                printCurrentTime();
                break;
            }
            case "help":{
                allCommand.forEach((k, v)-> System.out.println(ANSI_GREEN+k+" -- "+ANSI_BLUE+v+ANSI_RESET));
                break;
            }
        }
    }

    private boolean guessCommand(String command){
        String [] cmd = command.split(" ");
        for (Map.Entry<String, String> m : allCommand.entrySet()) {
            if (cmd[0].equals(m.getKey())){
                executeCommand(cmd[0]);
                return true;
            }
        }
        System.out.println("command '"+command+"' not found");
        return false;
    }

    private void clearConsole(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    protected void runConsole(){
        BufferedReader rd = null;
        String		line;

        initCommandList();
        clearConsole();
        drawLoading();
        System.out.println("\u001B[44m"+ANSI_YELLOW+"\tWelcome "+userName+" in admin console!"+ANSI_RESET);
        try{
            rd = new BufferedReader(new InputStreamReader(System.in));
            while((line = rd.readLine()) != null){
                guessCommand(line);
            }
        }catch(IOException e){
            System.err.println("\nError reading from console");
            e.printStackTrace();
            cleanAndExit(1);
        }finally{
            if (rd != null){
                try{
                    rd.close();
                }catch(IOException e){
                    e.printStackTrace();
                    cleanAndExit(1);
                }
            }
        }
        System.out.println("\u001B[44m"+ANSI_YELLOW+"\n\tGood by"+ANSI_RESET);
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
            cleanAndExit(1);
        }
        cleanAndExit(0);
    }
}
