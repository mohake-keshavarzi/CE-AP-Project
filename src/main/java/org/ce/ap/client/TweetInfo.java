package main.java.org.ce.ap.client;

import main.java.org.ce.ap.server.Tweet;

import java.time.LocalDateTime;
import java.util.HashSet;

public class TweetInfo {
    private String context;
    private TweetInfo reTweetedTweet;
    private ProfileInfo sender;
    private LocalDateTime submissionDate;
    private boolean isDeleted;
    private HashSet<ProfileInfo> likers;
    private HashSet<TweetInfo>  tweetsWhomHaveRetweetedThisTweet;

    public TweetInfo(String context){
        if(context.length()>256)
            throw  new IllegalArgumentException("context should be less than 256 characters");
        this.context=context;
    }


    public TweetInfo getReTweetedTweet() {
        return reTweetedTweet;
    }

    public ProfileInfo getSender() {
        return sender;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public String getContext() {
        return context;
    }

    public int numOfLikes(){
        return likers.size();
    }

    public int numOfRetweets(){
        return tweetsWhomHaveRetweetedThisTweet.size();

    }
}
