package Server;

import java.time.LocalDateTime;

public class Tweet {
    private final String context;
    private final LocalDateTime sublimationDate;
    private final Profile sender;
    private int numOfLikes=0;

    public  Tweet(String context,Profile sender){
        this.sender=sender;
        if (context==null)
            throw new IllegalArgumentException("Context is Null");
        else if(context.length()>256)
            throw new IllegalArgumentException("Tweet context should br less than 256 characters");

        this.context=context;
        this.sublimationDate =LocalDateTime.now();
    }
}
