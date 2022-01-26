package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.Profile;
import main.java.org.ce.ap.server.Tweet;
import main.java.org.ce.ap.server.TweetingService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TweetingServiceImpl implements TweetingService{
    ArrayList<Tweet> allTweets;
    private static TweetingServiceImpl INSTANCE;

    private TweetingServiceImpl(){allTweets= new ArrayList<>();}

    private String makeTweetId(Tweet tweet){
        int num=getAllTweetsOfProfile(tweet.getSender()).size();
        num++;
        String id=tweet.getSubmissionDate()+"num"+num +"@"+tweet.getSender().getUsername();
        return id;
    }
    /**
     * Implants singleton for this class.
     * if an instance from this class had been created, returns that
     * else creates a new instance and returns that
     * @return returns the previously created or newly created instance.
     */
    public static TweetingServiceImpl getInstance(){
        if(INSTANCE==null) {
            INSTANCE=new TweetingServiceImpl();
        }
        return INSTANCE;
    }

    /**
     * makes a new tweet and adds it to the list of tweets.
     * @param sender profile of who wants to send
     * @param context context of tweet
     * @return returns the created tweet
     * @throws IllegalArgumentException if tweet length is not valid
     * @throws NullPointerException if context is null
     */
    public Tweet publishTweet(Profile sender, String context)throws IllegalArgumentException,NullPointerException{
        Tweet tweet=new Tweet(context,sender);
        tweet.setId(makeTweetId(tweet));
        allTweets.add(tweet);
        return tweet;
    }

    public Tweet publishTweet(Tweet tweet)throws IllegalArgumentException,NullPointerException {
        tweet.setId(makeTweetId(tweet));
        allTweets.add(tweet);
        return tweet;
    }

        /**
         * makes a new tweet with a reTweet from previously published tweets (in this case context could be null)
         * @param sender profile of who wants to send a tweet
         * @param context context of tweet
         * @param reTweet the tweet that is going to be retweeted in this tweet
         * @return returns the created tweet
         * @throws IllegalArgumentException if tweet length is not valid
         */
    public Tweet reTweet(Profile sender,String context,Tweet reTweet) throws IllegalArgumentException{
        if(!allTweets.contains(reTweet))
            throw new IllegalArgumentException("No such a tweet found to be retweeted");
        Tweet tweet=new Tweet(context,sender,reTweet);
        tweet.setId(makeTweetId(tweet));
        allTweets.add(tweet);
        return tweet;
    }


    /**
     * checks weather the given tweet exists if true removes it from list
     * otherwise throws exception
     * @param tweet the tweet that should ce deleted
     * @throws NoSuchElementException if given tweet not exists
     */
    public void deleteTweet(Tweet tweet) throws NoSuchElementException {
        if(allTweets.contains(tweet)){
            allTweets.remove(tweet);
        }
        else
            throw new NoSuchElementException("There isn't such a tweet");
    }

    /**
     * likes a previously created tweet if it hadn't been liked by the profile
     * @param profile profile of who wants to like
     * @param tweet the tweet to be liked
     * @throws NoSuchElementException if given tweet not exists
     */
    public void likeTweet(Profile profile ,Tweet tweet ) throws NoSuchElementException{
        if(!allTweets.contains(tweet))
            throw new NoSuchElementException("There isn't such a tweet");
        if(!tweet.didLiked(profile)){
            tweet.addLike(profile);
        }
    }

    /**
     * unlikes a previously created tweet if had been liked by the profile
     * @param profile profile of who wants to unlike
     * @param tweet the tweet to be unliked
     * @throws NoSuchElementException if given tweet not exists
     */
    public void unLikeTweet(Profile profile,Tweet tweet) throws  NoSuchElementException{
        if(!allTweets.contains(tweet))
            throw new NoSuchElementException("There isn't such a tweet");
        if(tweet.didLiked(profile)){
            tweet.removeLike(profile);
        }
    }

    /**
     * returns All published tweets of the given profile it also contains the retweets
     * @param prf given profile
     * @return all published tweets
     */
    public ArrayList<Tweet> getAllTweetsOfProfile(Profile prf){
        ArrayList<Tweet> tweets=new ArrayList<>();
        for (Tweet tweet:allTweets) {
            if(tweet.getSender()==prf)
                tweets.add(tweet);
        }
        return tweets;
    }

    /**
     * returns all tweets that given profile has liked
     * @param prf given profile
     * @return all tweets that given profile has liked
     */
    public ArrayList<Tweet> getAllLikesOfProfile(Profile prf){
        ArrayList<Tweet> tweets=new ArrayList<>();
        for (Tweet tweet:allTweets) {
            if(tweet.didLiked(prf))
                tweets.add(tweet);
        }
        return tweets;
    }

    @Override
    public Tweet getTweetById(String id) {
        Tweet target=null;
        for (Tweet tweet:allTweets) {
            if(tweet.getId().equals(id)) {
                target = tweet;
                break;
            }
        }
    return target;
    }
}
