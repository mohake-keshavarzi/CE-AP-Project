package main.java.org.ce.ap.client.Impl;

import main.java.org.ce.ap.client.CommandParserService;

import java.util.Scanner;

public class CommandParserServiceImpl implements CommandParserService{
    private String input;
    private static CommandParserServiceImpl INSTANCE;
    private static ConsoleViewServiceImpl console;
    private CommandParserServiceImpl(){
        console=ConsoleViewServiceImpl.getInstance();
    }
    /**
     * for implanting  the singleton design.
     * if an instance of this class have been made returns that else makes a new one and returns that
     * @return current instance or newly created instance of this class
     */
    public static CommandParserServiceImpl getInstance(){
        if(INSTANCE==null) {
            INSTANCE=new CommandParserServiceImpl();
        }
        return INSTANCE;
    }



    @Override
    public void runAuthenticationInterface(){
        Scanner scanner = new Scanner(System.in);
        console.printHeading("Login");
        console.printNormal("If you already have an account Enter your username and password");
        console.printNormal("If you don't have any account enter a username and a password to creat one");
        console.printNormal("");
        console.printNormal("Username:  ",' ');
        String username=null;
        username = scanner.nextLine();
        while (username==null || username.equals("") || username.contains(" ")){
            console.printError("Enter a valid username");
            console.printNormal("Username:  ",' ');
            username = scanner.nextLine();
        }
        console.printNormal("Password:  ",' ');
        String password=null;
        password = scanner.nextLine();
        scanner.close();

        console.printError(username);
        console.printError(password);
    }
    @Override
    public void showMainMenu(){}
    @Override
    public void showTimeline(){}
    @Override
    public void runPostTweetInterface(){}


}
