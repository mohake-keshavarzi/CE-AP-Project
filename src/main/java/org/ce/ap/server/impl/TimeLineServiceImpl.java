package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class TimeLineServiceImpl implements TimeLineService {
    private static TimeLineServiceImpl INSTANCE;
    private TweetingServiceImpl TS;

    private TimeLineServiceImpl(TweetingServiceImpl tweetingService){
        this.TS=tweetingService;
    }

    /**
     * for implanting  the singleton design.
     * if an instance of this class have been made returns that else makes a new one and returns that
     * @return current instance or newly created instance of this class
     */
    public static TimeLineServiceImpl getInstance(){
        if(INSTANCE==null) {
            INSTANCE = new TimeLineServiceImpl(TweetingServiceImpl.getInstance());
        }
        return INSTANCE;
    }

    /**
     * returns whole published tweets of given profile's followings(It contains retweets)
     * @param profile the given profile
     * @return whole published tweets of given profile's followings(It contains retweets)
     */
    public ArrayList<Tweet> returnAllFollowingsTweets(Profile profile){
        ArrayList<Tweet> tweets=new ArrayList<>();
        for (Profile prf:profile.getListOfFollowings()) {
            tweets.addAll(TS.getAllTweetsOfProfile(prf));
        }
        return tweets;
    }

    /**
     * returns whole tweets which given profile's followings has liked
     * @param profile the given profile
     * @return returns whole tweets which given profile's followings has liked
     */
    public ArrayList<Tweet> returnAllFollowingsLikes(Profile profile){
        ArrayList<Tweet> tweets=new ArrayList<>();

        for (Profile prf:profile.getListOfFollowings()) {
            tweets.addAll(TS.getAllLikesOfProfile(prf));
        }
        return tweets;
    }

    /**
     * returns whole published tweets of given profile's followings(It contains retweets) also
     * whole tweets which given profile's followings has liked in order of submission date
     * but removes duplications
     * @param profile given profile
     * @return believe me. it returns all of them!!!!!!!!!!
     */
    public ArrayList<Tweet> returnTimeline(Profile profile){
        HashSet<Tweet> tweetHashSet=new HashSet<>();
        tweetHashSet.addAll(returnAllFollowingsTweets(profile));
        tweetHashSet.addAll(returnAllFollowingsLikes(profile));
//        tweetsAndProfiles.addAll(returnAllFollowingsLikes(profile));
        ArrayList<Tweet> tweets=new ArrayList<>(tweetHashSet);
        tweets.sort(Comparator.comparing(Tweet::getSubmissionDate)); //https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property
        return tweets;
    }

}
