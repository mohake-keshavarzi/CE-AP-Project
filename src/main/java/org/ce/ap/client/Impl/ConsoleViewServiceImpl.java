package main.java.org.ce.ap.client.Impl;

import main.java.org.ce.ap.client.ConsoleViewService;
import main.java.org.ce.ap.client.ProfileInfo;
import main.java.org.ce.ap.client.TweetInfo;
import main.java.org.ce.ap.server.Tweet;
import main.java.org.ce.ap.server.Profile;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ConsoleViewServiceImpl implements ConsoleViewService {

    private static ConsoleViewServiceImpl INSTANCE;
    private ConsoleViewServiceImpl(){}
    private final int CONTEXT_LINE_LENGTH=50;
    /**
     * for implanting  the singleton design.
     * if an instance of this class have been made returns that else makes a new one and returns that
     * @return current instance or newly created instance of this class
     */
    public static ConsoleViewServiceImpl getInstance(){
        if(INSTANCE==null) {
            INSTANCE=new ConsoleViewServiceImpl();
        }
        return INSTANCE;
    }

    @Override
    public void printNormal(String input,char ending){
        try {
            if(input!=null)
                System.out.print(ConsoleColors.WHITE_BRIGHT+ input+ending+ConsoleColors.RESET);
            else {
                throw new NullPointerException("Null input to show Normal text");
            }
        }catch (NullPointerException ex){
            printError(ex.toString());
        }

    }
    public void printNormal(String input){
        printNormal(input,'\n');
    }

    @Override
    public void printOption(String input,char ending) {
        try {
            if (input != null)
                System.out.print( ConsoleColors.YELLOW + input+ ending + ConsoleColors.RESET);
            else {
                throw new NullPointerException("Null input to show option text");
            }
        }catch (NullPointerException ex){
            printError(ex.toString());
        }
    }

    @Override
    public void printOption(String input) {printOption(input,'\n');}

    @Override
    public void printHeading(String input) {
        try {
            if (input != null)
                System.out.println(ConsoleColors.WHITE_UNDERLINED + ConsoleColors.WHITE_BOLD_BRIGHT + input + ConsoleColors.RESET);
            else {
                throw new NullPointerException("Null input to show Normal text");
            }
        }catch (NullPointerException ex){
            printError(ex.toString());
        }
    }


    @Override
    public void printError(String input) {
        try {

            if (input != null)
                System.out.println(ConsoleColors.RED + ConsoleColors.RED_UNDERLINED + input + ConsoleColors.RESET);
            else {
                throw new NullPointerException("Null input to show Normal text");
            }
        }catch (NullPointerException ex){
            printError(ex.toString());
        }
    }

    public void printTweet(TweetInfo tweet,String viewerUsername)
    {
        if(tweet==null){
            try {
                throw new NullPointerException("Tweet is null. not able to print");
            }catch (NullPointerException e){
                printError(e.toString());
            }
            return;
        }
        StringBuffer strBuffer=new StringBuffer(256);
        DateTimeFormatter formatter=null;
//        if(!isPreview)
              formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd    HH:mm");
        TweetInfo retweet=tweet.getReTweetedTweet();
        ProfileInfo sender=tweet.getSender();

        System.out.print(ConsoleColors.WHITE_BRIGHT);
        System.out.println("▒ id:"+tweet.getId());
        System.out.print(ConsoleColors.RESET);
        System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT);


        System.out.print(ConsoleColors.WHITE_BRIGHT);
        System.out.print("▒ ");
        System.out.print(ConsoleColors.RESET);
        System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT);
        System.out.print(sender.getFirstname()+" "+sender.getLastname()+"   ");
        System.out.print(ConsoleColors.RESET);
        System.out.print(ConsoleColors.WHITE);
        System.out.print("@"+sender.getUsername()+"\t\t\t");
        System.out.print(ConsoleColors.RESET);
        System.out.print(ConsoleColors.GREEN);
//        if (!isPreview)
            System.out.print(tweet.getSubmissionDate().format(formatter));
        System.out.print(ConsoleColors.RESET);
        System.out.print(ConsoleColors.WHITE);
        if(retweet==null)
            System.out.println("  Tweeted:");
        else System.out.println("  reTweeted:");

        System.out.print(ConsoleColors.RESET);
        System.out.print(ConsoleColors.GREEN);
        System.out.println("║");

        // print retweet if exists
        if(retweet!= null){
            System.out.print(ConsoleColors.RESET);
            System.out.print(ConsoleColors.GREEN);
            System.out.print("║  ");
            System.out.print(ConsoleColors.WHITE);
            System.out.print("▒ ");
            System.out.print(ConsoleColors.RESET);
            System.out.println(" id:"+tweet.getReTweetedTweet().getId());

            System.out.print(ConsoleColors.RESET);
            System.out.print(ConsoleColors.GREEN);
            System.out.print("║  ");
            System.out.print(ConsoleColors.WHITE);
            System.out.print("▒ ");
            System.out.print(ConsoleColors.RESET);
            System.out.print(ConsoleColors.PURPLE_BOLD_BRIGHT);
            System.out.print(retweet.getSender().getFirstname()+" "+retweet.getSender().getLastname()+"   ");
            System.out.print(ConsoleColors.RESET);
            System.out.print(ConsoleColors.WHITE);
            System.out.print("@"+retweet.getSender().getUsername()+"\t\t\t");
            System.out.print(ConsoleColors.RESET);
            System.out.print(ConsoleColors.PURPLE);
//            if (!isPreview)
                System.out.print(retweet.getSubmissionDate().format(formatter));
            System.out.print(ConsoleColors.RESET);
            System.out.print(ConsoleColors.WHITE);
            System.out.println("  Tweeted:");

            System.out.print(ConsoleColors.RESET);
            System.out.print(ConsoleColors.GREEN);
            System.out.print("║  ");
            System.out.print(ConsoleColors.RESET);
            System.out.print(ConsoleColors.PURPLE);
            System.out.println("║  ");


            if(!retweet.isDeleted()) {
                strBuffer.append(retweet.getContext());
                while (!strBuffer.isEmpty()) {
                    if (strBuffer.length() > CONTEXT_LINE_LENGTH) {
                        System.out.print(ConsoleColors.RESET);
                        System.out.print(ConsoleColors.GREEN);
                        System.out.print("║  ");
                        System.out.print(ConsoleColors.RESET);
                        System.out.print(ConsoleColors.PURPLE);
                        System.out.print("║  ");
                        System.out.print(ConsoleColors.RESET);
                        System.out.print(ConsoleColors.WHITE_BRIGHT);
                        System.out.println(strBuffer.substring(0, CONTEXT_LINE_LENGTH));
                    } else {
                        System.out.print(ConsoleColors.RESET);
                        System.out.print(ConsoleColors.GREEN);
                        System.out.print("║  ");
                        System.out.print(ConsoleColors.RESET);
                        System.out.print(ConsoleColors.PURPLE);
                        System.out.print("║  ");
                        System.out.print(ConsoleColors.RESET);
                        System.out.print(ConsoleColors.WHITE_BRIGHT);
                        System.out.println(strBuffer);
                        break;
                    }

                    strBuffer.delete(0, CONTEXT_LINE_LENGTH);
                }
            }
            else {
                System.out.print(ConsoleColors.RESET);
                System.out.print(ConsoleColors.GREEN);
                System.out.print("║  ");
                System.out.print(ConsoleColors.RESET);
                System.out.print(ConsoleColors.PURPLE);
                System.out.print("║  ");
                System.out.print(ConsoleColors.RESET);
                System.out.print(ConsoleColors.WHITE+ConsoleColors.WHITE_UNDERLINED);
                System.out.println("This Tweet Have been Deleted");
            }
            System.out.print(ConsoleColors.RESET);
            System.out.print(ConsoleColors.GREEN);
            System.out.print("║  ");
            System.out.print(ConsoleColors.RESET);
            System.out.print(ConsoleColors.PURPLE);
            System.out.println("╚══════════════════════════════════════");

        }

        if(!tweet.isDeleted()) {
            strBuffer = new StringBuffer(256);
            strBuffer.append(tweet.getContext());
            while (!strBuffer.isEmpty()) {
                if (strBuffer.length() > CONTEXT_LINE_LENGTH) {
                    System.out.print(ConsoleColors.RESET);
                    System.out.print(ConsoleColors.GREEN);
                    System.out.print("║  ");
                    System.out.print(ConsoleColors.RESET);
                    System.out.print(ConsoleColors.WHITE_BRIGHT);
                    System.out.println(strBuffer.substring(0, CONTEXT_LINE_LENGTH));
                } else {
                    System.out.print(ConsoleColors.RESET);
                    System.out.print(ConsoleColors.GREEN);
                    System.out.print("║  ");
                    System.out.print(ConsoleColors.RESET);
                    System.out.print(ConsoleColors.WHITE_BRIGHT);
                    System.out.println(strBuffer);
                    break;
                }
                strBuffer.delete(0, CONTEXT_LINE_LENGTH);
            }
        }
        else {
            System.out.print(ConsoleColors.RESET);
            System.out.print(ConsoleColors.GREEN);
            System.out.print("║  ");
            System.out.print(ConsoleColors.RESET);
            System.out.print(ConsoleColors.WHITE+ConsoleColors.WHITE_UNDERLINED);
            System.out.println("This Tweet Have been Deleted");
        }
        System.out.print(ConsoleColors.RESET);
        System.out.print(ConsoleColors.GREEN);
        System.out.print("╚═══════════");
        System.out.print(ConsoleColors.RESET);
        System.out.print(ConsoleColors.GREEN_BRIGHT);
        System.out.print("Retweets:"+tweet.numOfRetweets());
        System.out.print(ConsoleColors.RESET);
        System.out.print(ConsoleColors.GREEN);
        System.out.print("═════");
        System.out.print(ConsoleColors.RESET);
        System.out.print(ConsoleColors.GREEN_BRIGHT);
        System.out.print("Likes:"+tweet.numOfLikes());
        if(viewerUsername!=null)
            if(tweet.getLikersUsernames().contains(viewerUsername))
                System.out.print("  ♥");

        System.out.print(ConsoleColors.RESET);
        System.out.print(ConsoleColors.GREEN);
        System.out.println("══════════════════════");

        System.out.println(ConsoleColors.RESET);
    }

    @Override
    public void printTweet(TweetInfo tweet) {
        printTweet(tweet,null);
    }

    //https://stackoverflow.com/a/45444716
    protected class ConsoleColors {
        // Reset
        public static final String RESET = "\033[0m";  // Text Reset

        // Regular Colors
        public static final String BLACK = "\033[0;30m";   // BLACK
        public static final String RED = "\033[0;31m";     // RED
        public static final String GREEN = "\033[0;32m";   // GREEN
        public static final String YELLOW = "\033[0;33m";  // YELLOW
        public static final String BLUE = "\033[0;34m";    // BLUE
        public static final String PURPLE = "\033[0;35m";  // PURPLE
        public static final String CYAN = "\033[0;36m";    // CYAN
        public static final String WHITE = "\033[0;37m";   // WHITE

        // Bold
        public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
        public static final String RED_BOLD = "\033[1;31m";    // RED
        public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
        public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
        public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
        public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
        public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
        public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

        // Underline
        public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
        public static final String RED_UNDERLINED = "\033[4;31m";    // RED
        public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
        public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
        public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
        public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
        public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
        public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

        // Background
        public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
        public static final String RED_BACKGROUND = "\033[41m";    // RED
        public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
        public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
        public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
        public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
        public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
        public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

        // High Intensity
        public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
        public static final String RED_BRIGHT = "\033[0;91m";    // RED
        public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
        public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
        public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
        public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
        public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
        public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

        // Bold High Intensity
        public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
        public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
        public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
        public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
        public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
        public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
        public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
        public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

        // High Intensity backgrounds
        public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
        public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
        public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
        public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
        public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
        public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
        public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
        public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE
    }

}

