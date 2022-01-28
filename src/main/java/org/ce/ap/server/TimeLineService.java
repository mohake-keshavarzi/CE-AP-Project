package main.java.org.ce.ap.server;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public interface TimeLineService {


    /**
     * returns whole published tweets of given profile's followings(It contains retweets)
     * @param profile the given profile
     * @return whole published tweets of given profile's followings(It contains retweets)
     */
    ArrayList<Tweet> returnAllFollowingsTweets(Profile profile);

    /**
     * returns whole tweets which given profile's followings has liked in
     * a pair which contains the tweet and who has been liked that tweet
     * @param profile the given profile
     * @return returns whole tweets which given profile's followings has liked
     */
    ArrayList<Tweet> returnAllFollowingsLikes(Profile profile);

    /**
     * returns whole published tweets of given profile's followings(It contains retweets) also
     * whole tweets which given profile's followings has liked in order of submission date
     * but removes duplications
     * @param profile given profile
     * @return believe me. it returns all of them!!!!!!!!!!
     */
    ArrayList<Tweet> returnTimeline(Profile profile);
}
