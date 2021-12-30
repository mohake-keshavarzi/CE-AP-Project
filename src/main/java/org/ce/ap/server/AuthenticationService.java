package main.java.org.ce.ap.server;

import java.security.NoSuchAlgorithmException;

public interface  AuthenticationService {




    /**
     * returns the profile related to given username if the given password is correct
     * @param username wanted profile's username
     * @param password wanted profile's password
     * @return if no exception returns the matching profile
     * @throws NoSuchAlgorithmException  an internal error
     * @throws IllegalStateException wrong username or password
     */
      Profile login(String username,String password) throws NoSuchAlgorithmException,IllegalStateException,NullPointerException;


    /**
     * creates a new profile if the username is not same as any other profile and adds it to Profiles manager and returns that
     * @param firstName first name for profile
     * @param lastName last name for profile
     * @param username username for profile
     * @param password password for profile
     * @return created profile
     * @throws NoSuchAlgorithmException an internal error
     * @throws IllegalStateException duplicated username
     */
      Profile creatProfile(String firstName,String lastName,String username,String password) throws NoSuchAlgorithmException,IllegalStateException;
}
