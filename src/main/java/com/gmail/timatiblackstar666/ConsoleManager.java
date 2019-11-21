package com.gmail.timatiblackstar666;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsoleManager {

    protected static final String ANSI_RESET = "\u001B[0m";
    protected static final String ANSI_BLACK = "\u001B[30m";
    protected static final String ANSI_RED = "\u001B[31m";
    protected static final String ANSI_GREEN = "\u001B[32m";
    protected static final String ANSI_YELLOW = "\u001B[33m";
    protected static final String ANSI_BLUE = "\u001B[34m";
    protected static final String ANSI_PURPLE = "\u001B[35m";
    protected static final String ANSI_CYAN = "\u001B[36m";
    protected static final String ANSI_WHITE = "\u001B[37m";

    private Map<String, String> allCommand = new HashMap<>();
    private ArrayList<ConsoleFileWorker> userFiles = new ArrayList<>();
    private String userName = "Guest";

    public ConsoleManager() {
    }

    public ConsoleManager(String userName) {
        this.userName = userName;
        if (!ConsoleFileWorker.createWorkDirector(userName))
            cleanAndExit(1);
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

    private boolean loadFilesFromWorkDir(){
        String []files = ConsoleFileWorker.getFilesNameOfDir(this.userName);
        if (files != null){
            for(String name : files){
                System.out.println(name);
                userFiles.add(new ConsoleFileWorker(this.userName, name));
            }
            return true;
        }
        return false;
    }

    private void initCommandList(){
        addNewCommand("exit", "Exit");
        addNewCommand("help", "Show list of available commands");
        addNewCommand("time", "Show current date and time");
        addNewCommand("clear", "Clear console display");
        addNewCommand("new", "Create a new file, by default its file named 'unknown.txt'. To specify tye, name and expansion add options -f/-d(directory)  'name'." +
                "\n The file will created on your working directory");
        addNewCommand("pwd", "Show current directory");
        addNewCommand("workDir", "Show yur work directory");
        addNewCommand("ls", "List your current directory");
    }

    private void cleanAndExit(int code){
        clearConsole();
        System.exit(code);
    }

    private void exitFromConsole(){
        System.out.println("\u001B[44m"+ANSI_WHITE+"\n\tGood by "+userName+"\t"+ANSI_RESET);
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
            cleanAndExit(1);
        }
        cleanAndExit(0);
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
        System.out.println(ANSI_PURPLE+formatter.format(new Date())+ANSI_RESET);
    }

    private void executeCommand(String[] command){
        switch (command[0]){
            case "pwd": {
                System.out.println(ANSI_PURPLE+System.getProperty("user.dir")+ANSI_RESET);
                break;
            }
            case "workDir":{
                System.out.println(ANSI_PURPLE+System.getProperty("user.dir")+"/"+userName+ANSI_RESET);
                break;
            }
            case "exit":{
                exitFromConsole();
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
                allCommand.forEach((k, v)-> System.out.println(ANSI_BLUE+k+ANSI_WHITE+" -- "+v+ANSI_RESET));
                break;
            }
            case "ls":{
                userFiles.forEach((f) -> System.out.println(f.isFile() ? "f -  " : "d - " + ANSI_CYAN + f.getFileName() + ANSI_RESET));
                break;
            }
            case "new":{
                ConsoleFileWorker cfw = new ConsoleFileWorker(userName);
                if(cfw.newFile(command))
                    userFiles.add(cfw);
                break;
            }
        }
    }

    private boolean guessCommand(String command){
        String [] cmd = command.split(" ");
        for (Map.Entry<String, String> m : allCommand.entrySet()) {
            if (cmd[0].equals(m.getKey())){
                executeCommand(cmd);
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
        loadFilesFromWorkDir();

        clearConsole();
        drawLoading();
        System.out.println("\u001B[44m"+ANSI_YELLOW+"\tWelcome "+userName+" in admin console!\t"+ANSI_RESET);
        System.out.println(ANSI_CYAN+"type 'help' to see all options"+ANSI_RESET);
        try{
            rd = new BufferedReader(new InputStreamReader(System.in));
            while((line = rd.readLine()) != null){
                if(!line.equals(""))
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

    }
}
