package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.*;

import java.util.ArrayList;

public class ObserverServiceImpl implements ObserverService {
    private ProfilesManagerImpl prfM;
    private TweetingServiceImpl TS;
    private static ObserverServiceImpl INSTANCE=null;

    private ObserverServiceImpl(ProfilesManagerImpl prfM,TweetingServiceImpl TS){
        this.prfM=prfM;
        this.TS=TS;
    }

    /**
     * for implanting  the singleton design.
     * if an instance of this class have been made returns that else makes a new one and returns that
     * @return current instance or newly created instance of this class
     */
    public static ObserverServiceImpl getInstance(){
        if(INSTANCE==null) {
            INSTANCE = new ObserverServiceImpl(ProfilesManagerImpl.getInstance(),TweetingServiceImpl.getInstance());
        }
        return INSTANCE;
    }

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
    public void follow(Profile toBeFollowed, Profile follower)throws IllegalStateException,IllegalArgumentException,NullPointerException{
        if(follower==toBeFollowed)
            throw new IllegalStateException("follower and to be followed could not be similar");
        if(follower==null || toBeFollowed==null)
            throw new NullPointerException("at least one of inputs is null");
        if(!prfM.profileExists(toBeFollowed) || !prfM.profileExists(follower))
            throw new IllegalArgumentException("follower's profile or to_be_followed's profile dose not exist");
        if(!toBeFollowed.getListOfFollowers().contains(follower))
            toBeFollowed.addFollower(follower);
        if(!follower.getListOfFollowings().contains(toBeFollowed))
            follower.addFollowing(toBeFollowed);
    }

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
    public   void unfollow(Profile toBeUnFollowed,Profile follower)throws IllegalStateException,IllegalArgumentException,NullPointerException{
        if(follower==toBeUnFollowed)
            throw new IllegalStateException("follower and to be unfollowed could not be similar");
        if(follower==null || toBeUnFollowed==null)
            throw new NullPointerException("at least one of inputs is null");
        if(!prfM.profileExists(toBeUnFollowed) || !prfM.profileExists(follower))
            throw new IllegalArgumentException("follower's profile or to_be_unfollowed's profile dose not exist");
        if(toBeUnFollowed.getListOfFollowers().contains(follower))
            toBeUnFollowed.removeFollower(follower);
        if(follower.getListOfFollowings().contains(toBeUnFollowed))
            follower.removeFollowing(toBeUnFollowed);
    }

    /**
     * searches through all tweets and returns all tweets which given profile had published
     * @param profile the profile to return all its tweets
     * @return all tweets of given profile
     */
    public ArrayList<Tweet> returnAllFollowingsTweets(Profile profile){
        if(!prfM.profileExists(profile))
            throw new IllegalArgumentException("The profile not exists");
        ArrayList<Tweet> tweets=new ArrayList<>();
        for (Profile prf:profile.getListOfFollowings()) {
            tweets.addAll(TS.getAllTweetsOfProfile(prf));
        }
        return tweets;
    }

}
