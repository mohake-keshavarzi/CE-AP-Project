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

    private ArrayList<Tweet> returnAllFollowingsTweets(Profile profile){
        ArrayList<Tweet> tweets=new ArrayList<>();
        for (Profile prf:profile.getListOfFollowings()) {
            tweets.addAll(TS.getAllTweetsOfProfile(prf));
        }
        return tweets;
    }

    private ArrayList<Tweet> returnAllFollowingsLikes(Profile profile){
        ArrayList<Tweet> tweets=new ArrayList<>();
        for (Profile prf:profile.getListOfFollowings()) {
            tweets.addAll(TS.getAllLikesOfProfile(prf));
        }
        return tweets;
    }

    protected ArrayList<Tweet> returnTimeline(Profile profile){
        ArrayList<Tweet> tweets=new ArrayList<>();
        tweets.addAll(returnAllFollowingsLikes(profile));
        tweets.addAll(returnAllFollowingsTweets(profile));
        tweets.sort(Comparator.comparing(Tweet::getSubmissionDate));
        return tweets;
    }
}
