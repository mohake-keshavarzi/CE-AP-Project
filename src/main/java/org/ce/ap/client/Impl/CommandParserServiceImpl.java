package main.java.org.ce.ap.client.Impl;

import main.java.org.ce.ap.client.CommandParserService;
import main.java.org.ce.ap.client.ResponsePackageParser;
import main.java.org.ce.ap.client.RequestPackageMaker;
import org.json.simple.parser.ParseException;

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
    public void showWelcome(){
        Scanner scanner = new Scanner(System.in);
        console.printHeading("Welcome");
        console.printNormal("If you already have an account choose Sign In option");
        console.printNormal("If you don't have any account feel free to creat one from Sign Up option");
        console.printNormal("");
        console.printOption("1. Sign In");
        console.printOption("2. Sign Up");
        console.printOption("3. Exit");

        console.printNormal("");
        console.printNormal("print the number of your option:",' ');
        int choice= scanner.nextInt();
        scanner.close();
        if(choice == 1){
            runSignInInterface();
        }else if(choice == 2){
            runSignUpInterface();
        }else {
            System.exit(0);
        }
    }

    @Override
    public void runSignInInterface() throws IllegalStateException{
        boolean retry =false;
        Scanner scanner = new Scanner(System.in);
        console.printHeading("Sign In");
        console.printNormal("If you already have an account Enter your username and password");
        console.printNormal("");
        console.printNormal("Username:  ",' ');
        String username;
        username = scanner.nextLine();
        while (username==null || username.equals("") || username.contains(" ")){
            console.printError("Enter a valid username");
            console.printNormal("Username:  ",' ');
            username = scanner.nextLine();
        }
        console.printNormal("Password:  ",' ');
        String password;
        password = scanner.nextLine();
        RequestPackageMaker request = new RequestPackageMaker("request to connect to @"+username);
        request.creatSignInRequestPackage(username,password);
        console.printNormal("Connecting to @"+username+" ...");
        console.printNormal("Logging in ...");
        do {
            try {
                network.sendToServer(request.getString());
            } catch (IllegalStateException ex) {
                console.printError(ex + " Connection to the server lost.");
                console.printNormal("Do you want to retry?(y/n)");
                retry = scanner.nextLine().equals("y");
                if (!retry){
                    console.printError("Failed to Login");
                    throw new IllegalStateException("failed to connect. no connection");
                }
            }
        }while (retry);
        ResponsePackageParser packageParser;
        try {
            packageParser = new ResponsePackageParser(network.receiveFromServer());
            if (packageParser.wasSignInSuccessful())
                console.printNormal("Logged in. WelcomeBack");
            else {
                console.printError("Authentication Failed wrong username or password");
                throw new IllegalArgumentException("Wong username or password");
            }
        }catch (ParseException e){
            console.printError("Failed to parse response package from server "+e);
        }

        scanner.close();

    }

    @Override
    public void runSignUpInterface() throws IllegalStateException{

    }

    @Override
    public void showMainMenu(){}
    @Override
    public void showTimeline(){}
    @Override
    public void runPostTweetInterface(){}


}
