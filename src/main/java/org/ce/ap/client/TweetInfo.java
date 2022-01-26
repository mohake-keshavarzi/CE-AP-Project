package main.java.org.ce.ap.client;

import main.java.org.ce.ap.server.Profile;
import main.java.org.ce.ap.server.Tweet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

public class TweetInfo {
    private String id;
    private String context;
    private TweetInfo reTweetedTweet;
    private ProfileInfo sender;
    private LocalDateTime submissionDate;
    private boolean isDeleted;
    private HashSet<String> likersUsernames;
    private HashSet<String>  idOftweetsWhomHaveRetweetedThisTweet;
    private HashSet<String>  usernameOfWhomHaveRetweetedThisTweet;

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    public TweetInfo(String context,ProfileInfo sender){
        this.sender=sender;
        if (context==null)
            throw new NullPointerException("Context is Null");
        if(context.length()>256)
            throw  new IllegalArgumentException("context should be less than 256 characters");
        this.context=context;
        likersUsernames=new HashSet<>();
        idOftweetsWhomHaveRetweetedThisTweet=new HashSet<>();
        usernameOfWhomHaveRetweetedThisTweet=new HashSet<>();
        reTweetedTweet=null;
    }
    public TweetInfo(String context, ProfileInfo sender, TweetInfo reTweet) throws  IllegalArgumentException{
        this.sender=sender;
        if (context==null)
            context="";
        else if(context.length()>256)
            throw new IllegalArgumentException("Tweet context should br less than 256 characters");

        this.context=context;
        likersUsernames=new HashSet<>();
        idOftweetsWhomHaveRetweetedThisTweet=new HashSet<>();
        usernameOfWhomHaveRetweetedThisTweet=new HashSet<>();
        reTweetedTweet=reTweet;
        //this.parentTweet=parentTweet;
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
        return likersUsernames.size();
    }

    public int numOfRetweets(){
        return idOftweetsWhomHaveRetweetedThisTweet.size();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addLiker(String username){
        likersUsernames.add(username);
    }
    public void addReTweeterTweetId(String tweetId){
        idOftweetsWhomHaveRetweetedThisTweet.add(tweetId);
    }
    public void addReTweeterUsername(String username){
        usernameOfWhomHaveRetweetedThisTweet.add(username);
    }
}
