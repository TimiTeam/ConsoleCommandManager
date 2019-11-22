package com.gmail.timatiblackstar666;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.ObjIntConsumer;

public class FolderManager {
    private Set<ConsoleFileWorker> folderConent = new LinkedHashSet<>();
    private File workFolder;
    private int size = 0;

    public FolderManager(String folderName) {
        this.workFolder = new File(folderName);
        if (!this.workFolder.exists()) {
            if(!this.workFolder.mkdir())
                System.out.println("Work directory already exist");
        }
        else
            listCurDir(this.workFolder, 5, null);
    }

    private void listCurDir(File dir, int deep, ObjIntConsumer<File> con){
        File[] allFile = dir.listFiles();
        if (allFile != null){
            for (File file : allFile){
                this.size++;
                if(con != null)
                    con.accept(file, deep);
                if (file.isDirectory())
                    listCurDir(file, deep + 5, con);
                this.folderConent.add(new ConsoleFileWorker(file));
            }
        }
    }

    public void listWorkFolder(){
        listCurDir(this.workFolder, 1, (file, deep)->
                System.out.printf("%"+deep+"c%s %c %s : "+ConsoleManager.ANSI_CYAN+"%d\n"+ConsoleManager.ANSI_RESET,
                        ' ', ConsoleManager.ANSI_CYAN+(file.isFile() ? "f" : "d")+ConsoleManager.ANSI_RESET, '-',
                        ConsoleManager.ANSI_YELLOW+file.getName()+ConsoleManager.ANSI_RESET, file.length())
        );
    }

    public void createNewFile(String []args){
        if (args.length > 2)
            args[2] = this.workFolder.getName()+"/"+args[2];
        else {
            args = new String[]{"new", "-f", this.workFolder.getName()+"/unknown.txt"};
        }
        ConsoleFileWorker cfw = new ConsoleFileWorker();
        if(cfw.newFile(args))
            this.folderConent.add(cfw);
    }
}
