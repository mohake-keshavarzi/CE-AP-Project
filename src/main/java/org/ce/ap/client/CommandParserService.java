package main.java.org.ce.ap.client;


public class CommandParserService {
    private String input;
    private static CommandParserService INSTANCE;
    /**
     * for implanting  the singleton design.
     * if an instance of this class have been made returns that else makes a new one and returns that
     * @return current instance or newly created instance of this class
     */
    protected static CommandParserService getInstance(){
        if(INSTANCE==null) {
            INSTANCE=new CommandParserService();
        }
        return INSTANCE;
    }


}
