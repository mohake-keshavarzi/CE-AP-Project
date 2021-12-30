package main.java.org.ce.ap.server;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public interface TweetingService {


    /**
     * makes a new tweet and adds it to the list of tweets.
     * @param sender profile of who wants to send
     * @param context context of tweet
     * @return returns the created tweet
     * @throws IllegalArgumentException if tweet length is not valid
     * @throws NullPointerException if context is null
     */
     Tweet publishTweet(Profile sender,String context)throws IllegalArgumentException,NullPointerException;
    /**
     * makes a new tweet with a reTweet from previously published tweets (in this case context could be null)
     * @param sender profile of who wants to send a tweet
     * @param context context of tweet
     * @param reTweet the tweet that is going to be retweeted in this tweet
     * @return returns the created tweet
     * @throws IllegalArgumentException if tweet length is not valid
     */
     Tweet reTweet(Profile sender,String context,Tweet reTweet) throws IllegalArgumentException;


    /**
     * checks weather the given tweet exists if true removes it from list
     * otherwise throws exception
     * @param tweet the tweet that should ce deleted
     * @throws NoSuchElementException if given tweet not exists
     */
     void deleteTweet(Tweet tweet) throws NoSuchElementException;


    /**
     * likes a previously created tweet if it hadn't been liked by the profile
     * @param profile profile of who wants to like
     * @param tweet the tweet to be liked
     * @throws NoSuchElementException if given tweet not exists
     */
     void likeTweet(Profile profile ,Tweet tweet ) throws NoSuchElementException;

    /**
     * unlikes a previously created tweet if had been liked by the profile
     * @param profile profile of who wants to unlike
     * @param tweet the tweet to be unliked
     * @throws NoSuchElementException if given tweet not exists
     */
     void unLikeTweet(Profile profile,Tweet tweet) throws  NoSuchElementException;


    /**
     * returns All published tweets of the given profile it also contains the retweets
     * @param prf given profile
     * @return all published tweets
     */
     ArrayList<Tweet> getAllTweetsOfProfile(Profile prf);

    /**
     * returns all tweets that given profile has liked
     * @param prf given profile
     * @return all tweets that given profile has liked
     */
     ArrayList<Tweet> getAllLikesOfProfile(Profile prf);


}
