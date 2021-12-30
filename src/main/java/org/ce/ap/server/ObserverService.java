package main.java.org.ce.ap.server;

import java.util.ArrayList;

public interface ObserverService{

    /**
     * gets two profile one is follower and another who is going to be followed
     * and makes follower to follow the other profile.
     * this function dose this when follower or to be followed hadn't had each others in their list
     * @param toBeFollowed profile which is going to be followed
     * @param follower profile of who wants to follow
     * @throws IllegalStateException if follower and to_be_followed are similar
     * @throws IllegalArgumentException when at least one of the profiles hasn't been found
     * @throws NullPointerException if there is at least one null input
     */
      void follow(Profile toBeFollowed,Profile follower)throws IllegalStateException,IllegalArgumentException,NullPointerException;

    /**
     * gets two profile one is follower and another who is going to be unfollowed
     * and makes follower to unfollow the other profile
     * this function dose this when follower or to be unfollowed have had each others in their list
     *
     * @param toBeUnFollowed profile which is going to be unfollowed
     * @param follower profile of who wants to follow
     * @throws IllegalStateException if follower and to_be_unfollowed are similar
     * @throws IllegalArgumentException when at least one of the profiles hasn't been found
     * @throws NullPointerException if there is at least one null input
     */
     void unfollow(Profile toBeUnFollowed,Profile follower)throws IllegalStateException,IllegalArgumentException,NullPointerException;


     /**
     * searches through all tweets and returns all tweets which given profile had published
     * @param profile the profile to return all its tweets
     * @return all tweets of given profile
     */
    ArrayList<Tweet> returnAllFollowingsTweets(Profile profile);

}
