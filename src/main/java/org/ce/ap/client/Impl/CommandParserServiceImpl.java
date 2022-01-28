package main.java.org.ce.ap.client.Impl;

import main.java.org.ce.ap.client.*;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.NoSuchElementException;
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
                loggedInProfileInfo =packageParser.getLoggedInProfileBasicData();}
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
                loggedInProfileInfo =packageParser.getLoggedInProfileBasicData();
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
        console.printOption("2.Post new reTweet");
        console.printOption("3.View Tweet by its Id");
        console.printOption("4.Like Tweet by its Id");
        console.printOption("5.Follow Profile by its username");
        console.printOption("6.UnFollow Profile by its username");

        console.printOption("7.Get TimeLine");


        console.printOption("0.Exit");
        int choice=0;
        choice= scanner.nextInt();
        if(choice == 1) {
            runPostTweetInterface();
        }
        else if(choice == 2){
            runPostReTweetInterface();
        }
        else if(choice == 3){
            runViewTweetByIdInterface();
        }else if(choice==4){
            runLikeTweetByIdInterface();
        }else if(choice==5){
            runFollowByUsernameInterface();
        }else if(choice==6){
            runUnFollowByUsernameInterface();
        }
        else if(choice==7){
            showTimeline();
        }
        else {
            System.exit(0);
        }


    }
    private String get256CharText(){
        console.printError("Warning: Enter ◙ (alt+10) for end of your text");
        String text=new String();
        while (!text.contains("◙")){
            text += scanner.nextLine()+"\n";
        }
        return text;
    }
    @Override
    public void showTimeline(){
        scanner.nextLine();
        ResponsePackageParser responsePackage;
        console.printHeading("View TimeLine");
        RequestPackageMaker request=new RequestPackageMaker("Get Timeline");
        request.createTimelineRequestPackage();
        tryToSendToServer(request);
        try {
            responsePackage=new ResponsePackageParser(network.receiveFromServer());
            ArrayList<String> tIdArray;
            tIdArray=responsePackage.getTimeLineResults();
            for (String tId:tIdArray) {
                TweetInfo tweetInfo;
                try {
                    tweetInfo = getTweetFromServerById(tId);
                    console.printTweet(tweetInfo,loggedInProfileInfo.getUsername());
//                    if(tweetInfo.getSender().getUsername())
                } catch (Exception e) {
                    console.printError("Something went wrong.  " + e.getMessage());
                }

            }

        }catch (ParseException e) {
            console.printError("Failed to parse response package from server " + e);
            e.printStackTrace();
        }finally {
            showMainMenu();
        }
    }
    @Override
    public void runPostTweetInterface(){
        console.printHeading("New Tweet");
        TweetInfo tweet;
        console.printOption("Enter yours tweet's text (less than 256 characters):");
        String text=get256CharText();
//        System.out.print(text);
        try {
            tweet = new TweetInfo(text, loggedInProfileInfo);
            RequestPackageMaker request = new RequestPackageMaker("request to publish new tweet to");
            request.createPublishNewTweetPackage(tweet);
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
                        packageParser.completeTweetInfoData(tweet);
                    }catch (Exception ex){
                        console.printError(ex.toString());
                    }
//                    System.out.println("here");
                    console.printTweet(tweet);
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

    @Override
    public void runPostReTweetInterface() {
        scanner.nextLine();
        ResponsePackageParser packageParser;

        console.printHeading("New ReTweet");
        TweetInfo reTweetTarget;
        TweetInfo myTweet;
        console.printOption("Enter target tweet's id to retweet:");
        String targetTweetID = scanner.nextLine();
        try {
            reTweetTarget = getTweetFromServerById(targetTweetID);
            console.printNormal("The tweet which you want to post a retweet about");
            console.printTweet(reTweetTarget);

        }catch (IllegalArgumentException e){
            console.printError("Error! Please check given tweetId "+ e);
            return;
        }

        console.printOption("Enter your review on this tweet (less than 256 characters):");
        String text=get256CharText();
        try {
            myTweet=new TweetInfo(text,loggedInProfileInfo,reTweetTarget);
            RequestPackageMaker request = new RequestPackageMaker("request to publish new ReTweet");
            request.createPublishNewReTweetPackage(myTweet);
            tryToSendToServer(request);

            try {
                packageParser = new ResponsePackageParser(network.receiveFromServer());
                System.out.println(packageParser.wasTweetPublishingSuccessful());
                if (packageParser.hasError()) {
                    throw new IllegalStateException("Server Error | Error Type:" + packageParser.getErrorType().name() + " | Error Code" + packageParser.getErrorCode().name());
                }
                if(packageParser.wasTweetPublishingSuccessful()){
                    console.printNormal("Preview:");
                    try {
                        packageParser.completeTweetInfoData(myTweet);
                    }catch (Exception ex){
                        console.printError(ex.toString());
                    }
//                    System.out.println("here");
                    console.printTweet(myTweet);
                    console.printNormal("Your tweet posted successfully");
                }


            }catch (ParseException e){
                console.printError("Failed to parse response package from server " + e);
                e.printStackTrace();
            }
        }catch (IllegalArgumentException e){
            console.printError("Error:" + e);
            return;
        }finally {
            showMainMenu();
        }
    }

    public void creatRetweetByTargetIdRequest(String  id,String context){
        ResponsePackageParser packageParser;
        TweetInfo target;
        try {
            target = getTweetFromServerById(id);
            try {
                TweetInfo myTweet = new TweetInfo(context, loggedInProfileInfo, target);
                RequestPackageMaker request = new RequestPackageMaker("request to publish new ReTweet");
                request.createPublishNewReTweetPackage(myTweet);
                tryToSendToServer(request);
                try {
                    packageParser = new ResponsePackageParser(network.receiveFromServer());
                    System.out.println(packageParser.wasTweetPublishingSuccessful());
                    if (packageParser.hasError()) {
                        throw new IllegalStateException("Server Error | Error Type:" + packageParser.getErrorType().name() + " | Error Code" + packageParser.getErrorCode().name());
                    }
                    if (packageParser.wasTweetPublishingSuccessful()) {
                        console.printNormal("Your retweet posted successfully");
                    }
                } catch (ParseException e) {
                    console.printError("Failed to parse response package from server " + e);
                    e.printStackTrace();
                }

            } catch (IllegalArgumentException e) {
                console.printError("Error:Invalid Context" + e);
                return;
            }
        }catch (IllegalArgumentException e){
            console.printError("Invalid tweet id "+e);
        }
    }

    public void runViewTweetByIdInterface(){
        scanner.nextLine();
        ResponsePackageParser packageParser;

        console.printHeading("View tweet");
        TweetInfo targetTweet;
        console.printOption("Enter target tweet's id to observe:");
        String targetTweetID = scanner.nextLine();
        String choice="1";

        try {
            while (choice!="0") {
                targetTweet = getTweetFromServerById(targetTweetID);
                boolean isLikedByThisProfile =targetTweet.getLikersUsernames().contains(loggedInProfileInfo.getUsername());
                console.printNormal("Tweet you wanted to observe:");
                console.printTweet(targetTweet,loggedInProfileInfo.getUsername());
                console.printNormal("Choose your next Action");
                console.printOption("1.View Likes");
                console.printOption("2.View usernames of whom have retweeted this");
                console.printOption("3.View retweets on this tweet");
                if(!isLikedByThisProfile)
                    console.printOption("4.Like this Tweet");
                else
                    console.printOption("4.UnLike this Tweet");
                console.printOption("5.Write retweet on this tweet");
                console.printOption("0.Back");
                choice = scanner.nextLine();
                if (choice.equals("1")) {
                    for (String s : targetTweet.getLikersUsernames()) {
                        console.printNormal("@" + s);
                    }

                } else if (choice.equals("2")) {
                    for (String s : targetTweet.getUsernameOfWhomHaveRetweetedThisTweet()) {
                        console.printNormal("@" + s);
                    }
                } else if (choice.equals("3")) {
                    for (String s : targetTweet.getIdOftweetsWhomHaveRetweetedThisTweet()) {
                        console.printTweet(getTweetFromServerById(s),loggedInProfileInfo.getUsername());
                    }
                } else if (choice.equals("4")) {
                    if(!isLikedByThisProfile)
                        likeTweetById(targetTweet.getId());
                    else
                        unlikeTweetById(targetTweet.getId());
                } else if (choice.equals("5")) {
                    String text = get256CharText();
                    creatRetweetByTargetIdRequest(targetTweet.getId(), text);
                } else {
                    break;
                }
                console.printOption("write something to continue...");
                scanner.nextLine();
            }


        }catch (IllegalArgumentException e){
            console.printError("Error! Please check given tweetId "+ e);
            return;
        }finally {
            showMainMenu();
        }

    }

    public TweetInfo getTweetFromServerById(String id){
        RequestPackageMaker request = new RequestPackageMaker("Get tweet by id");
        request.createGetTweetByIdRequest(id);
        tryToSendToServer(request);
        ResponsePackageParser packageParserForTweet;
        ResponsePackageParser packageParserForTweetSender;
        ResponsePackageParser packageParserForReTweet;
        ResponsePackageParser packageParserForReTweetSender;

        ProfileInfo sender;
        String context;
        TweetInfo tweet=null;
        try {
            packageParserForTweet = new ResponsePackageParser(network.receiveFromServer());
            if(packageParserForTweet.hasError())
                throw new IllegalArgumentException("Wrong tweetId :"+packageParserForTweet.getErrorType()+" "+packageParserForTweet.getErrorType());
            request=new RequestPackageMaker("Get tweet sender's info");
            request.createGetProfileByUsernameRequest(packageParserForTweet.getTweetSenderUsername());
            tryToSendToServer(request);


            packageParserForTweetSender = new ResponsePackageParser(network.receiveFromServer());
//            System.out.print("here1");
            sender=packageParserForTweetSender.getResultProfile();
            context=packageParserForTweet.getResultTweetContext();
            if(packageParserForTweet.hasReTweet()){
                request=new RequestPackageMaker("Get tweet's inner retweet");
                request.createGetTweetByIdRequest(packageParserForTweet.getReTweetedTweetId());
                tryToSendToServer(request);

                packageParserForReTweet = new ResponsePackageParser(network.receiveFromServer());
                request=new RequestPackageMaker("Get retweet sender's info");
                request.createGetProfileByUsernameRequest(packageParserForReTweet.getTweetSenderUsername());
                tryToSendToServer(request);

                packageParserForReTweetSender=new ResponsePackageParser(network.receiveFromServer());

                ProfileInfo reTweetSender=packageParserForReTweetSender.getResultProfile();
                String reTweetContext=packageParserForReTweet.getResultTweetContext();
                TweetInfo reTweetedTweet=new TweetInfo(reTweetContext,reTweetSender);
                reTweetedTweet=packageParserForReTweet.completeResultTweetInfo(reTweetedTweet);
                tweet=new TweetInfo(context,sender,reTweetedTweet);
            }else {
                tweet=new TweetInfo(context,sender);
            }
            tweet=packageParserForTweet.completeResultTweetInfo(tweet);

        } catch (ParseException e) {
            console.printError("Failed to parse response package from server " + e);
            e.printStackTrace();
        }
        return tweet;
    }


    public void likeTweetById(String id){
        RequestPackageMaker request = new RequestPackageMaker("Like tweet by id");
        request.createLikeByIdRequest(id);
        tryToSendToServer(request);
        ResponsePackageParser responsePackage;
        try {
            responsePackage=new ResponsePackageParser(network.receiveFromServer());
            try {
                 if(responsePackage.likedSuccessfully()){
                     console.printNormal("Tweet (id:"+responsePackage.getResultTweetId()+") liked successfully");
                 }else{
                     console.printError("You Have been liked this tweet before");
                 }

            }catch (NoSuchElementException e) {
                console.printError("Id dose not exists");
            }
        }catch (ParseException e) {
            console.printError("Failed to parse response package from server " + e);
            e.printStackTrace();
        }

    }
    public void unlikeTweetById(String id){
        RequestPackageMaker request = new RequestPackageMaker("UnLike tweet by id");
        request.createUnLikeByIdRequest(id);
        tryToSendToServer(request);
        ResponsePackageParser responsePackage;
        try {
            responsePackage=new ResponsePackageParser(network.receiveFromServer());
            try {
                if(responsePackage.UnLikedSuccessfully()){
                    console.printNormal("Tweet (id:"+responsePackage.getResultTweetId()+") unliked successfully");
                }else{
                    console.printError("You Have not been liked this tweet before");
                }

            }catch (NoSuchElementException e) {
                console.printError("Id dose not exists");
            }
        }catch (ParseException e) {
            console.printError("Failed to parse response package from server " + e);
            e.printStackTrace();
        }

    }


    public void runLikeTweetByIdInterface(){
        console.printOption("Enter id of tweet you want to like:");
        scanner.nextLine();
        String id=scanner.nextLine();
        likeTweetById(id);
        showMainMenu();
    }

    public void runFollowByUsernameInterface(){
        console.printOption("Enter username of profile you want to follow:");
        scanner.nextLine();
        String username=scanner.nextLine();
        followProfileByUsername(username);
        showMainMenu();
    }
    public void runUnFollowByUsernameInterface(){
        console.printOption("Enter username of profile you want to unfollow:");
        scanner.nextLine();
        String username=scanner.nextLine();
        unFollowProfileByUsername(username);
        showMainMenu();
    }

    public void followProfileByUsername(String username){
        RequestPackageMaker request = new RequestPackageMaker("follow given username's account");
        request.creatFollowProfileByUsernameRequest(username);
        tryToSendToServer(request);
        ResponsePackageParser responsePackage;
        try {
            responsePackage=new ResponsePackageParser(network.receiveFromServer());
            try {
                if(responsePackage.followedSuccessfully()){
                    console.printNormal("You followed "+responsePackage.getResultProfileUsername()+" successfully");
                }

            }catch (NoSuchElementException | IllegalArgumentException e) {
                console.printError(e.getMessage());
            }
        }catch (ParseException e) {
            console.printError("Failed to parse response package from server " + e);
            e.printStackTrace();
        }
    }

    public void unFollowProfileByUsername(String username){
        RequestPackageMaker request = new RequestPackageMaker("unfollow given username's account");
        request.creatUnFollowProfileByUsernameRequest(username);
        tryToSendToServer(request);
        ResponsePackageParser responsePackage;
        try {
            responsePackage=new ResponsePackageParser(network.receiveFromServer());
            try {
                if(responsePackage.unFollowedSuccessfully()){
                    console.printNormal("You unfollowed "+responsePackage.getResultProfileUsername()+" successfully");
                }

            }catch (NoSuchElementException | IllegalArgumentException e) {
                console.printError(e.getMessage());
            }
        }catch (ParseException e) {
            console.printError("Failed to parse response package from server " + e);
            e.printStackTrace();
        }
    }
}
