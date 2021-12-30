package main.java.org.ce.ap.server;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;

//ram based--> should upgrade to Hard Storage mode
public interface ProfilesManager {


    /**
     * If profiles username is not duplicated, adds the given profile to the list of profiles.
     * @param profile The given profile
     * @throws IllegalArgumentException when already there is another profile with same username.
     */
    void addProfile(Profile profile)throws IllegalArgumentException;

    /**
     * Checks there is such a given username, if true removes it from list
     * @param profile profile that we want to delete
     * @throws NoSuchElementException if there is not such a profile
     */
     void removeProfile(Profile profile) throws NoSuchElementException;

    /**
     * Finds the profile which has the given username and returns it.
     * @param username username of desired profile
     * @return the profile with the same username
     */
     Profile getProfileByUserName(String username);

    /**
     * checks weather there is a profile with the given username in the list
     * @param username username that we want to check
     * @return the profile which has this username
     */
     boolean checkSimilarUsername(String username);

    /**
     * checks weather given profiles exists in the list of profiles
     * @param prf the profile to be checked
     * @return true if profile exists
     */
     boolean profileExists(Profile prf);

}
