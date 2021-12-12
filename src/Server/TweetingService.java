package Server;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TweetingService {
    ArrayList<Tweet> allTweets;
    private static TweetingService INSTANCE;

    private TweetingService(){allTweets= new ArrayList<>();}

    protected static TweetingService getInstance(){
        if(INSTANCE==null) {
            INSTANCE=new TweetingService();
        }
        return INSTANCE;
    }

    protected Tweet publishTweet(Profile sender,String context){
        Tweet tweet=new Tweet(context,sender);
        allTweets.add(tweet);
        return tweet;
    }

    protected Tweet reTweet(Profile sender,String context,Tweet reTweet){
        if(!allTweets.contains(reTweet))
            throw new IllegalArgumentException("No such a tweet found to be retweeted");
        Tweet tweet=new Tweet(context,sender,reTweet);
        allTweets.add(tweet);
        return tweet;
    }

    protected Tweet reply(Profile sender,String context){
        Tweet tweet=new Tweet(context,sender);
        allTweets.add(tweet);
        return tweet;
    }




    protected void deleteTweet(Tweet tweet) throws NoSuchElementException {
        if(allTweets.contains(tweet)){
            allTweets.remove(tweet);
        }
        else
            throw new NoSuchElementException("There isn't such a tweet");
    }

    protected void likeTweet(Profile profile ,Tweet tweet ) throws NoSuchElementException{
        if(!allTweets.contains(tweet))
            throw new NoSuchElementException("There isn't such a tweet");
        if(!tweet.didLiked(profile)){
            tweet.addLike(profile);
        }
    }

    protected void unLikeTweet(Profile profile,Tweet tweet) throws  NoSuchElementException{
        if(!allTweets.contains(tweet))
            throw new NoSuchElementException("There isn't such a tweet");
        if(tweet.didLiked(profile)){
            tweet.removeLike(profile);
        }
    }


}
