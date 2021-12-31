package main.java.org.ce.ap.client.Impl;

public class ConsoleViewServiceImpl {

    private static ConsoleViewServiceImpl INSTANCE;
    private ConsoleViewServiceImpl(){}
    private String test;
    /**
     * for implanting  the singleton design.
     * if an instance of this class have been made returns that else makes a new one and returns that
     * @return current instance or newly created instance of this class
     */
    protected static ConsoleViewServiceImpl getInstance(){
        if(INSTANCE==null) {
            INSTANCE=new ConsoleViewServiceImpl();
        }
        return INSTANCE;
    }
}
