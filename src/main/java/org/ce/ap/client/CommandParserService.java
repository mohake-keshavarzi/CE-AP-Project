package main.java.org.ce.ap.client;


import org.json.simple.parser.ParseException;

import java.util.Scanner;

public interface CommandParserService {

    void showWelcome();

    void runSignInInterface()throws ParseException;

    void runSignUpInterface()throws ParseException;


    void showMainMenu();

    void showTimeline();

    void runPostTweetInterface();

    void runPostReTweetInterface();

     TweetInfo getTweetFromServerById(String id);





}
