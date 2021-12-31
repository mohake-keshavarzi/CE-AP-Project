package main.java.org.ce.ap.client.Impl;

import main.java.org.ce.ap.client.CommandParserService;

public class CommandParserServiceImpl {
    private String input;
    private static CommandParserServiceImpl INSTANCE;
    /**
     * for implanting  the singleton design.
     * if an instance of this class have been made returns that else makes a new one and returns that
     * @return current instance or newly created instance of this class
     */
    protected static CommandParserServiceImpl getInstance(){
        if(INSTANCE==null) {
            INSTANCE=new CommandParserServiceImpl();
        }
        return INSTANCE;
    }
}
