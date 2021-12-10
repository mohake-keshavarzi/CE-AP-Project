package Server;

import java.security.NoSuchAlgorithmException;

public class TweetingService {
    private Profile profile;


    public TweetingService(Profile profile, String password) {
           this.profile=profile;
    }

    //public publishNewTweet()

    public void likeTweet(Tweet tweet ){
        if(!tweet.didLiked(this.profile)){
            tweet.addLike(this.profile);
        }
    }

    public void unLikeTweet(Tweet tweet){
        if(tweet.didLiked(this.profile)){
            tweet.removeLike(this.profile);
        }
    }

}
