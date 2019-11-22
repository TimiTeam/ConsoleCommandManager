package com.gmail.timatiblackstar666;

import java.io.File;
import java.util.*;
import java.util.function.*;

public class FolderManager {
    private Set<ConsoleFileWorker> folderContent = new LinkedHashSet<>();
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
                this.folderContent.add(new ConsoleFileWorker(file));
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

    public void deleteFile(String []var){
    	if (var.length < 2)
		System.out.println(ConsoleManager.ANSI_RED+"Need ender file name"+ConsoleManager.ANSI_RESET);
	else if (var.length > 2){
		System.out.print(ConsoleManager.ANSI_RED+"Unknown options: "+ConsoleManager.ANSI_RESET);
		for(String s :var)
			System.out.print(s+" ");
		System.out.println("");
	}
	else{
		String name = var[1];
		boolean del = false;
		Iterator<ConsoleFileWorker> iter = this.folderContent.iterator();
		File file;
		while(iter.hasNext() && !del){
			file = iter.next().getFile();
			if (file.getName().equals(name)){
				iter.remove();
				file.delete();
				del = true;
			}
		}
		System.out.print(ConsoleManager.ANSI_YELLOW+name+ConsoleManager.ANSI_RESET+": ");
		if (del){
			System.out.println(ConsoleManager.ANSI_CYAN+"Successful delete");
		}else
			System.out.println(ConsoleManager.ANSI_RED+"Not Found");
		System.out.print(ConsoleManager.ANSI_RESET);
	}
    }

    public void createNewFile(String []args){
        if (args.length > 2)
            args[2] = this.workFolder.getName()+"/"+args[2];
        else {
            args = new String[]{"new", "-f", this.workFolder.getName()+"/unknown.txt"};
        }
        ConsoleFileWorker cfw = new ConsoleFileWorker();
        if(cfw.newFile(args))
            this.folderContent.add(cfw);
    }
}
