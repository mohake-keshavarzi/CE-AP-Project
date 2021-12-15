package Server;

import java.util.ArrayList;
import java.util.Comparator;

public class TimeLineService {
    private static TimeLineService INSTANCE;
    private TweetingService TS;

    private TimeLineService(TweetingService tweetingService){
        this.TS=tweetingService;
    }

    /**
     * for implanting  the singleton design.
     * if an instance of this class have been made returns that else makes a new one and returns that
     * @return current instance or newly created instance of this class
     */
    protected static TimeLineService getInstance(){
        if(INSTANCE==null) {
            INSTANCE = new TimeLineService(TweetingService.getInstance());
        }
        return INSTANCE;
    }

    /**
     * returns whole published tweets of given profile's followings(It contains retweets)
     * @param profile the given profile
     * @return whole published tweets of given profile's followings(It contains retweets)
     */
    private ArrayList<Tweet> returnAllFollowingsTweets(Profile profile){
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
    private ArrayList<Tweet> returnAllFollowingsLikes(Profile profile){
        ArrayList<Tweet> tweets=new ArrayList<>();
        for (Profile prf:profile.getListOfFollowings()) {
            tweets.addAll(TS.getAllLikesOfProfile(prf));
        }
        return tweets;
    }

    /**
     * returns whole published tweets of given profile's followings(It contains retweets) also
     * whole tweets which given profile's followings has liked in order of submission date
     * @param profile given profile
     * @return believe me. it returns all of them!!!!!!!!!!
     */
    protected ArrayList<Tweet> returnTimeline(Profile profile){
        ArrayList<Tweet> tweets=new ArrayList<>();
        tweets.addAll(returnAllFollowingsLikes(profile));
        tweets.addAll(returnAllFollowingsTweets(profile));
        tweets.sort(Comparator.comparing(Tweet::getSubmissionDate)); //https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property
        return tweets;
    }
}
