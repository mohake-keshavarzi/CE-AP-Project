package Server;

import java.time.LocalDateTime;
import java.util.HashSet;

public class Tweet {
    private final String context;
    private final LocalDateTime sublimationDate;
    private final Profile sender;
    private HashSet<Profile> likers;
    //private int numOfLikes=0;

    /**
     * makes a new Tweet
     * @param context the text of Tweet
     * @param sender profile of the sender of the Tweet
     * @throws  IllegalArgumentException weather if context is valid
     */
    public Tweet(String context,Profile sender) throws IllegalArgumentException{
        this.sender=sender;
        if (context==null)
            throw new IllegalArgumentException("Context is Null");
        else if(context.length()>256)
            throw new IllegalArgumentException("Tweet context should br less than 256 characters");
        likers= new HashSet<>();
        this.context=context;
        this.sublimationDate =LocalDateTime.now();
    }

    /**
     * adds given profile to the list of whom had liked this Tweet
     * @param profile profile of whom wants to like
     * @throws  IllegalArgumentException If the profile had liked this Tweet before
     */
    protected void addLike(Profile profile) throws IllegalArgumentException{
        if(!likers.contains(profile)){
            //numOfLikes++;
            likers.add(profile);
        }
        else throw new IllegalArgumentException("This profile had liked this massage before");

    }

    /**
     * if profile had liked this Tweet removes the profile from list of whom had liked this Tweet
     * @param profile the profile of whom wants to remove his like on this Tweet
     * @throws IllegalStateException if this profile hadn't liked this Tweet
     */
    protected  void removeLike(Profile profile) throws IllegalStateException{
        if(likers.contains(profile)) {
            //numOfLikes--;
            likers.remove(profile);
        }
        else throw new IllegalStateException("you hadn't liked this massage");
    }

    public int numOfLikes(){
        return likers.size();
    }

    protected boolean didLiked(Profile profile){
        return likers.contains(profile);
    }
}
