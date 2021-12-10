package Server;
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

    protected static ProfilesManager getInstance(){
        if(INSTANCE==null) {
            INSTANCE=new ProfilesManager();
        }
        return INSTANCE;
    }

    protected static void addProfile(Profile profile)throws IllegalArgumentException {
        if(!checkSimilarUsername(profile.getUsername()))
            allProfiles.add(profile);
        else
            throw new IllegalArgumentException("A profile with this Username Already exists");
    }

    protected void removeProfile(Profile profile) throws NoSuchElementException{
        if(allProfiles.contains(profile)){
            allProfiles.remove(profile);
        }
        else
            throw new NoSuchElementException("The profile not found to remove");
    }
    
    protected static Profile getProfileByUserName(String username){
        for (Profile prf:allProfiles) {
            if(prf.getUsername().equals(username))
                return prf;
        }
        return null;
    }

    protected static boolean checkSimilarUsername(String username){
        for (Profile prf:allProfiles) {
            if(prf.getUsername().equals(username))
                return true;
        }
        return false;
    }

}
