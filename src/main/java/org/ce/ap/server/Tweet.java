package main.java.org.ce.ap.server;

import java.time.LocalDateTime;
import java.util.HashSet;

public class Tweet {
    private  String context;
    private boolean isDeleted=false;
    private final LocalDateTime submissionDate;
    private final Profile sender;
    private final Tweet reTweetedTweet;
    //private final Tweet parentTweet;
    private HashSet<Profile> likers; // Profiles of whom have liked this post
    private HashSet<Tweet> tweetsWhomHaveRetweetedThisTweet;
    //private int numOfLikes=0;

    /**
     * makes a new Tweet
     * @param context the text of Tweet
     * @param sender profile of the sender of the Tweet
     * @throws  IllegalArgumentException  if context has more than 256 characters
     * @throws  NullPointerException if context is Null
     */
    public Tweet(String context,Profile sender) throws IllegalArgumentException,NullPointerException{
        this.sender=sender;
        tweetsWhomHaveRetweetedThisTweet=new HashSet<>();

        if (context==null)
            throw new NullPointerException("Context is Null");
        else if(context.length()>256)
            throw new IllegalArgumentException("Tweet context should br less than 256 characters");
        likers= new HashSet<>();
        this.context=context;
        this.submissionDate =LocalDateTime.now();
        reTweetedTweet=null;
        //this.parentTweet=parentTweet;
    }

    /**
     * makes a new tweet which contains another tweet as retweet
     * here if context is null is replaced with an empty string
     * @param context the context of main tweet
     * @param sender  profile of who is retweeting
     * @param reTweet the Tweet that we want to be retweeted in this tweet
     * @throws IllegalStateException if context has more than 256 characters
     */
    public Tweet(String context,Profile sender,Tweet reTweet) throws  IllegalArgumentException{
        this.sender=sender;
        tweetsWhomHaveRetweetedThisTweet=new HashSet<>();
        reTweet.addRetweeter(this);
        if (context==null)
            context="";
        else if(context.length()>256)
            throw new IllegalArgumentException("Tweet context should br less than 256 characters");
        likers= new HashSet<>();
        this.context=context;
        this.submissionDate =LocalDateTime.now();
        reTweetedTweet=reTweet;
        //this.parentTweet=parentTweet;
    }


    /**
     * adds given profile to the list of whom have liked this Tweet
     * @param profile profile of whom wants to like
     * @throws  IllegalArgumentException If the profile had liked this Tweet before
     */
    public void addLike(Profile profile) throws IllegalArgumentException{
        if(!likers.contains(profile)){
            //numOfLikes++;
            likers.add(profile);
        }
        else throw new IllegalArgumentException("This profile had liked this massage before");


    }

    /**
     * if profile have liked this Tweet removes the profile from list of whom had liked this Tweet
     * @param profile the profile of whom wants to remove his like on this Tweet
     * @throws IllegalStateException if this profile hadn't liked this Tweet
     */
    public  void removeLike(Profile profile) throws IllegalStateException{
        if(likers.contains(profile)) {
            //numOfLikes--;
            likers.remove(profile);
        }
        else throw new IllegalStateException("you hadn't liked this massage");
    }

    public int numOfLikes(){
        return likers.size();
    }

    public boolean didLiked(Profile profile){
        return likers.contains(profile);
    }

    public Profile getSender() {
        return sender;
    }

    public Tweet getReTweetedTweet() {
        return reTweetedTweet;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public String getContext() {
        return context;
    }

    public void addRetweeter(Tweet tweet){
        tweetsWhomHaveRetweetedThisTweet.add(tweet);
    }
    public int numOfRetweeters(){
        return tweetsWhomHaveRetweetedThisTweet.size();

    }

    public void deleteThisTweet(){
        context=null;
        isDeleted=true;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
}
