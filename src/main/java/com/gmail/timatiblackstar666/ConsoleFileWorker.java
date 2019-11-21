package com.gmail.timatiblackstar666;

import java.io.File;
import java.io.IOException;

public class ConsoleFileWorker {
    private File file;
    private boolean isFile;
    private static final String udefName = "undefine.txt";

    public ConsoleFileWorker() {
    }

    private boolean successMessage(String name){
        System.out.println(ConsoleManager.ANSI_CYAN+name+ConsoleManager.ANSI_GREEN+" Successful create"+ConsoleManager.ANSI_RESET);
        return true;
    }

    private boolean errorMessage(String fileName, String message){
        System.out.println(ConsoleManager.ANSI_RED+"Error creating "+fileName+": "+message+ConsoleManager.ANSI_RESET);
        return false;
    }

    private boolean createFile(){
        try {
            if(isFile && file.createNewFile()){
                return successMessage(file.getPath());
            }
            if (!isFile && file.mkdir()){
                return successMessage(file.getPath());
            }
            else if (!isFile){
                return errorMessage(file.getPath(), "Directory already exist");
            }
            else
                return errorMessage(file.getPath(), "File already exist");
        }
        catch (IOException e){
            e.printStackTrace();
            return errorMessage(file.getPath(), "You may not have enough right");
        }
    }

    public boolean newFile(String[] vars) {
        if (vars.length == 1){
            this.file = new File(udefName);
            this.isFile = true;
            return createFile();
        }
        else if (vars.length == 3) {
            if (vars[1].equals("-f")) {
                this.file = new File(vars[2]);
                isFile = true;
                return createFile();
            }
            else if(vars[1].equals("-d")){
                this.file = new File(vars[2]);
                isFile = false;
                return createFile();
            }
            else {
                return errorMessage("", "Undefine options '"+vars[1]+"'");
            }
        }
        else {
            return errorMessage("", "Wrong number of arguments");
        }
    }
}
