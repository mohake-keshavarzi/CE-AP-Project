package Server;

import java.security.NoSuchAlgorithmException;

public abstract class  AuthenticationService {

    /**
     * returns the profile related to given username if the given password is correct
     * @param username wanted profile's username
     * @param password wanted profile's password
     * @return if no exception returns the matching profile
     * @throws NoSuchAlgorithmException  an internal error
     * @throws IllegalStateException wrong username or password
     */
    public static Profile login(String username,String password) throws NoSuchAlgorithmException,IllegalStateException,NullPointerException{
        if(!ProfilesManager.checkSimilarUsername(username))
            throw new IllegalArgumentException("No such a username exists");
        Profile prf=ProfilesManager.getProfileByUserName(username);
        assert prf != null : "null profile";
        if (!prf.checkPassword(password))
            throw new IllegalArgumentException("Wrong password");
        return prf;
    }

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
    public static Profile creatProfile(String firstName,String lastName,String username,String password) throws NoSuchAlgorithmException,IllegalStateException{
        //if (username==null || password==null || lastName)
           // throw new NullPointerException("password or username is null");

        if(ProfilesManager.checkSimilarUsername(username))
            throw new IllegalArgumentException("These username has been used before");
        Profile prf=new Profile(firstName,lastName,username,password);

        ProfilesManager.addProfile(prf);
        return  prf;
    }
}
