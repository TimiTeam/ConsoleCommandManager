package com.gmail.timatiblackstar666;
import java.lang.String;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;
/**
 * Hello world!
 *
 */
public class App 
{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
    	

	public static void main( String[] args )
    	{
		BufferedReader	rd = null;
		String		line;

		clearConsole();
        	System.out.println("\u001B[44m"+ANSI_YELLOW+"\n\tWeclone in admin console!"+ANSI_RESET);
		
		try{
			rd = new BufferedReader(new InputStreamReader(System.in));
			while((line = rd.readLine()) != null){
				clearConsole();
				System.out.println("\u001B[44m"+ANSI_YELLOW+"\n\tWeclone in admin console!"+ANSI_RESET);
				if ("exit".equals(line.toLowerCase())){
					break ;
				}else{
					System.out.println("commad '"+line+"' not found");
				}
			}
		}catch(IOException e){
			System.err.println("\nError reading from console");
			e.printStackTrace();
			System.exit(1);
		}finally{
			if (rd != null){
				try{
					rd.close();
				}catch(IOException e){
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
        	System.out.println("\u001B[44m"+ANSI_YELLOW+"\n\tGood by"+ANSI_RESET);
    	}

	private static void guessCommand(String command){
		System.out.println("commad '"+command+"' not found");
	}

	private static void clearConsole(){
		System.out.print("\033[H\033[2J");  
    		System.out.flush(); 
	}
}
