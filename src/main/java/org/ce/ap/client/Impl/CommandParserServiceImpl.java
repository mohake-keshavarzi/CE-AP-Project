package main.java.org.ce.ap.client.Impl;

import main.java.org.ce.ap.client.*;
import org.json.simple.parser.ParseException;

import java.util.Scanner;

public class CommandParserServiceImpl implements CommandParserService{
    private String input;
    private static CommandParserServiceImpl INSTANCE;
    private static ConsoleViewServiceImpl console;
    private ConnectionServiceImpl network;
    private final Scanner scanner;
    private ProfileInfo loggedInProfileInfo;
    private CommandParserServiceImpl(ConnectionServiceImpl connectionService,Scanner scanner){
        this.scanner=scanner;
        console=ConsoleViewServiceImpl.getInstance();
        network=connectionService;
    }
    /**
     * for implanting  the singleton design.
     * if an instance of this class have been made returns that else makes a new one and returns that
     * @return current instance or newly created instance of this class
     */
    public static CommandParserServiceImpl getInstance(ConnectionServiceImpl network,Scanner scanner){
        if(INSTANCE==null) {
            INSTANCE=new CommandParserServiceImpl(network,scanner);
        }
        return INSTANCE;
    }

    private void tryToSendToServer(RequestPackageMaker request){
        boolean retry=false;
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

    }
    @Override
    public void showWelcome(){
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
        scanner.nextLine();
        boolean retry =false;
        console.printHeading("Sign In");
        console.printNormal("Enter your username and password");
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
        tryToSendToServer(request);
        ResponsePackageParser packageParser;
        try {
            packageParser = new ResponsePackageParser(network.receiveFromServer());
            if (packageParser.wasSignInSuccessful()){
                console.printNormal("Logged in. WelcomeBack");
                loggedInProfileInfo =packageParser.getLoggedInProfileData();}
            else {
                console.printError("Authentication Failed wrong username or password");
                throw new IllegalArgumentException("Wong username or password");
            }
        }catch (ParseException e){
            console.printError("Failed to parse response package from server "+e);
        }
        showMainMenu();


    }

    @Override
    public void runSignUpInterface() throws IllegalStateException {
        scanner.nextLine();
        console.printHeading("Sign Up");
        console.printNormal("Please enter following fields to make a new account");
        console.printNormal("");
        console.printOption("First Name:  ", ' ');
        String firstname;
        firstname = scanner.nextLine();
        console.printOption("Last Name:  ", ' ');
        String lastname;
        lastname = scanner.nextLine();
        console.printOption("Username:  ", ' ');
        String username;
        username = scanner.nextLine();
        while (username == null || username.equals("") || username.contains(" ")) {
            console.printError("Enter a valid username");
            console.printNormal("Username:  ", ' ');
            username = scanner.nextLine();
        }
        console.printOption("Password:  ", ' ');
        String password;
        password = scanner.nextLine();
//        System.out.println(firstname+" "+lastname+" "+username+" "+password);
        RequestPackageMaker request = new RequestPackageMaker("creat a new account with username: @" + username);
        request.creatSignUpRequestPackage(firstname, lastname, username, password);
        console.printNormal("Sending your request to the server...");
        tryToSendToServer(request);
        ResponsePackageParser packageParser;
        try {
            packageParser = new ResponsePackageParser(network.receiveFromServer());
            if(packageParser.hasError()){
                throw new IllegalStateException("Server Error | Error Type:"+packageParser.getErrorType().name()+" | Error Code"+packageParser.getErrorCode().name());
            }
            if (packageParser.wasSignUpSuccessful()){
                console.printNormal("Your account successfully created");
                loggedInProfileInfo =packageParser.getLoggedInProfileData();
            }else if (packageParser.wasUsernameDuplicated()){
                console.printError("This username has been already used");
                throw new IllegalArgumentException("duplicated username");
            }
        } catch (ParseException e) {
            console.printError("Failed to parse response package from server " + e);
            e.printStackTrace();
        }
        showMainMenu();

    }

    @Override
    public void showMainMenu(){
        console.printHeading("Main Menu");
        console.printNormal("Logged in as @"+ loggedInProfileInfo.getUsername());
        console.printNormal("Name: "+ loggedInProfileInfo.getFirstname()+" "+ loggedInProfileInfo.getLastname());
        console.printNormal("Bio :"+ loggedInProfileInfo.getBio());

        console.printNormal("--------------------------------------");

        console.printNormal("Choose your action:");
        console.printOption("1.Post new Tweet");
        console.printOption("2.Post new reTweet !!!!!!!!!!!!!!!!!!!");

        console.printOption("0.Exit");
        int choice=0;
        choice= scanner.nextInt();
        if(choice == 1){
            runPostTweetInterface();
        }else {
            System.exit(0);
        }


    }
    @Override
    public void showTimeline(){}
    @Override
    public void runPostTweetInterface(){
        console.printHeading("New Tweet");
        TweetInfo tweet;
        console.printOption("Enter yours tweet's text (less than 256 characters):");
        console.printError("Warning: Enter ◙ (alt+10) for end of your text");
        String text=new String();
        while (!text.contains("◙")){
        text += scanner.nextLine()+"\n";
        }
//        System.out.print(text);
        try {
            tweet = new TweetInfo(text, loggedInProfileInfo);
            RequestPackageMaker request = new RequestPackageMaker("request to publish new tweet to");
            request.createPublishNewTweetPackage(tweet, false);
            tryToSendToServer(request);
            ResponsePackageParser packageParser;
            try {
                packageParser = new ResponsePackageParser(network.receiveFromServer());
                System.out.println(packageParser.wasTweetPublishingSuccessful());
                if (packageParser.hasError()) {
                    throw new IllegalStateException("Server Error | Error Type:" + packageParser.getErrorType().name() + " | Error Code" + packageParser.getErrorCode().name());
                }
                if(packageParser.wasTweetPublishingSuccessful()){
                    console.printNormal("Preview:");
                    try {
                        tweet.setPublishingDate(packageParser.getPostedTweetSubmissionDate());
                    }catch (Exception ex){
                        console.printError(ex.toString());
                    }
//                    System.out.println("here");
                    console.printTweet(tweet,false);
                    console.printNormal("Your tweet posted successfully");
                }


            } catch (ParseException e) {
                console.printError("Failed to parse response package from server " + e);
                e.printStackTrace();
            }


        } catch (IllegalArgumentException e) {
            console.printError("Error:" + e);
            return;
        }catch (NullPointerException e){
            console.printError("Error:" + e);
            return;
        }finally {
            showMainMenu();
        }

    }


}
