package main.java.org.ce.ap.client.Impl;

import main.java.org.ce.ap.client.CommandParserService;
import main.java.org.ce.ap.netWorkingParams;
import main.java.org.ce.ap.client.RequestPackageMaker;

import java.util.Scanner;

public class CommandParserServiceImpl implements CommandParserService{
    private String input;
    private static CommandParserServiceImpl INSTANCE;
    private static ConsoleViewServiceImpl console;
    private ConnectionServiceImpl network;
    private CommandParserServiceImpl(ConnectionServiceImpl connectionService){

        console=ConsoleViewServiceImpl.getInstance();
        network=connectionService;
    }
    /**
     * for implanting  the singleton design.
     * if an instance of this class have been made returns that else makes a new one and returns that
     * @return current instance or newly created instance of this class
     */
    public static CommandParserServiceImpl getInstance(ConnectionServiceImpl network){
        if(INSTANCE==null) {
            INSTANCE=new CommandParserServiceImpl(network);
        }
        return INSTANCE;
    }



    @Override
    public void runAuthenticationInterface() throws IllegalStateException{
        boolean retry =false;
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
        RequestPackageMaker request = new RequestPackageMaker(netWorkingParams.RequestPackage.Methods.AUTHENTICATION_REQUEST,"connect to @"+username+" Creat a new account");
        request.putParameter(netWorkingParams.RequestPackage.ParametersFields.PASSWORD,password);
        console.printNormal("Connecting to @"+username+" ...");
        console.printNormal("Logging in ...");
        do {
            try {
                network.sendToServer(request.getString());
            } catch (IllegalStateException ex) {
                console.printError(ex + " Connection to the server lost.");
                console.printNormal("Do you want to retry?(y/n)");
                retry = scanner.nextLine().equals("y");
                if (retry==false){
                    console.printError("Failed to Login");
                    throw new IllegalStateException("failed to connect. no connection");
                }
            }
        }while (retry);
        network.receiveFromServer();
        scanner.close();

    }
    @Override
    public void showMainMenu(){}
    @Override
    public void showTimeline(){}
    @Override
    public void runPostTweetInterface(){}


}
