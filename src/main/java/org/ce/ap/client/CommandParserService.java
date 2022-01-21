package main.java.org.ce.ap.client;


import org.json.simple.parser.ParseException;

public interface CommandParserService {

    void showWelcome();

    void runSignInInterface()throws ParseException;

    void runSignUpInterface()throws ParseException;


    void showMainMenu();

    void showTimeline();

    void runPostTweetInterface();




}
