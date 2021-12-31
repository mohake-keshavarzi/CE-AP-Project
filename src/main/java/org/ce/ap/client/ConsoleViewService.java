package main.java.org.ce.ap.client;

import main.java.org.ce.ap.server.Tweet;

import java.time.LocalDateTime;

public interface ConsoleViewService {

    void printNormal(String input);

    void printHeading(String input);

    void printError(String input);

    void printTweet(Tweet tweet, int likes, int retweets);


}
