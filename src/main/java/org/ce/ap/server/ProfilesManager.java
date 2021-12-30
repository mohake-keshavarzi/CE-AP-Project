package main.java.org.ce.ap.server;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;

//ram based--> should upgrade to Hard Storage mode
public class ProfilesManager {
    private static ArrayList<Profile> allProfiles;
    private static ProfilesManager INSTANCE=null;
    private ProfilesManager(){
        allProfiles=new ArrayList<>();
    }

    /**
     * for implanting  the singleton design.
     * if an instance of this class have been made returns that else makes a new one and returns that
     * @return current instance or newly created instance of this class
     */
    protected static ProfilesManager getInstance(){
        if(INSTANCE==null) {
            INSTANCE=new ProfilesManager();
        }
        return INSTANCE;
    }

    /**
     * If profiles username is not duplicated, adds the given profile to the list of profiles.
     * @param profile The given profile
     * @throws IllegalArgumentException when already there is another profile with same username.
     */
    protected  void addProfile(Profile profile)throws IllegalArgumentException {
        if(!checkSimilarUsername(profile.getUsername()))
            allProfiles.add(profile);
        else
            throw new IllegalArgumentException("A profile with this Username Already exists");
    }

    /**
     * Checks there is such a given username, if true removes it from list
     * @param profile profile that we want to delete
     * @throws NoSuchElementException if there is not such a profile
     */
    protected void removeProfile(Profile profile) throws NoSuchElementException{
        if(allProfiles.contains(profile)){
            allProfiles.remove(profile);
        }
        else
            throw new NoSuchElementException("The profile not found to remove");
    }

    /**
     * Finds the profile which has the given username and returns it.
     * @param username username of desired profile
     * @return the profile with the same username
     */
    protected Profile getProfileByUserName(String username){
        for (Profile prf:allProfiles) {
            if(prf.getUsername().equals(username))
                return prf;
        }
        return null;
    }

    /**
     * checks weather there is a profile with the given username in the list
     * @param username username that we want to check
     * @return the profile which has this username
     */
    protected boolean checkSimilarUsername(String username){
        for (Profile prf:allProfiles) {
            if(prf.getUsername().equals(username))
                return true;
        }
        return false;
    }

    /**
     * checks weather given profiles exists in the list of profiles
     * @param prf the profile to be checked
     * @return true if profile exists
     */
    protected boolean profileExists(Profile prf){
        return allProfiles.contains(prf);
    }

}
