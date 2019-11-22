package com.gmail.timatiblackstar666;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ConsoleFileWorker {
    private File file;
    private boolean isFile;

    public ConsoleFileWorker() {
    }

    public ConsoleFileWorker(File file){
        this.file = file;
        this.isFile = file.isFile();
    }

    public boolean isFile() {
        return isFile;
    }

    public String getFileName(){
        return file.getName();
    }

    private boolean successMessage(String name){
        System.out.println(ConsoleManager.ANSI_CYAN+name+ConsoleManager.ANSI_GREEN+" Successful create"+ConsoleManager.ANSI_RESET);
        return true;
    }

    private boolean errorMessage(String fileName, String message){
        System.out.println(ConsoleManager.ANSI_RED+"Error creating "+fileName+ConsoleManager.ANSI_RESET+": "+message);
        return false;
    }

    private boolean rewriteFile(String type){
        System.out.println("Do you want to rewrite "+type+"?");
        boolean yes = true;
        Scanner input = new Scanner(System.in);
        String ansv;
        while (yes){
            System.out.println("type: "+ConsoleManager.ANSI_BLUE+"Y "+ConsoleManager.ANSI_RESET+"or "+ConsoleManager.ANSI_GREEN+"N"+ConsoleManager.ANSI_RESET);
            ansv = input.nextLine();
            if (ansv.equals("Y"))
                break;
            else if (ansv.equals("N"))
                yes = false;
        }
        if (yes){
            if (file.delete())
                createFile();
        }
        return yes;
    }

    private boolean createFile(){
        try {
            if(this.isFile && this.file.createNewFile()){
                return successMessage(file.getPath());
            }
            if (!this.isFile && this.file.mkdir()){
                return successMessage(file.getPath());
            }
            else if (!this.isFile){
                errorMessage(this.file.getPath(), "Directory already exist");
                return rewriteFile("folder");
            }
            else {
                errorMessage(this.file.getPath(), "File already exist");
                return rewriteFile("file");
            }
        }
        catch (IOException e){
            e.printStackTrace();
            return errorMessage(this.file.getPath(), "You may not have enough right");
        }
    }

    protected boolean newFile(String[] vars) {
        if (vars.length == 3) {
            if (vars[1].equals("-f")) {
                this.file = new File(vars[2]);
                isFile = true;
            }
            else if(vars[1].equals("-d")){
                this.file = new File(vars[2]);
                isFile = false;
            }
            else {
                return errorMessage("", "Undefine options '"+vars[1]+"'");
            }
            return createFile();
        }
        else {
            return errorMessage("", "Wrong number of arguments");
        }
    }
}
