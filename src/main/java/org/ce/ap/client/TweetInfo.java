package main.java.org.ce.ap.client;

import main.java.org.ce.ap.server.Tweet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

public class TweetInfo {
    private String context;
    private TweetInfo reTweetedTweet;
    private ProfileInfo sender;
    private LocalDateTime submissionDate;
    private boolean isDeleted;
    private HashSet<ProfileInfo> likers;
    private HashSet<TweetInfo>  tweetsWhomHaveRetweetedThisTweet;
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    public TweetInfo(String context,ProfileInfo sender){
        this.sender=sender;
        if (context==null)
            throw new NullPointerException("Context is Null");
        if(context.length()>256)
            throw  new IllegalArgumentException("context should be less than 256 characters");
        this.context=context;
        likers=new HashSet<>();
        tweetsWhomHaveRetweetedThisTweet=new HashSet<>();
        reTweetedTweet=null;
    }

    public void setPublishingDate(String date){
        submissionDate= LocalDateTime.parse(date,formatter);
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
