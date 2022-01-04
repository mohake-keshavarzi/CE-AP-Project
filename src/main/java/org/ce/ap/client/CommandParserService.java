package main.java.org.ce.ap.client;


import org.json.simple.parser.ParseException;

public interface CommandParserService {

    void runAuthenticationInterface()throws ParseException;

    void showMainMenu();

    void showTimeline();

    void runPostTweetInterface();




}
