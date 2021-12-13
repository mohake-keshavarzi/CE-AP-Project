package Server;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;

public class Profile {
    private String firstName;
    private String lastName;
    private String bio="";
    final private String username;
    final private LocalDateTime registerDate;
    private BigInteger password;
    final private String CODING_FORMAT="SHA-256";
    private final HashSet<Profile> followers;
    private final HashSet<Profile> followings;


    /**
     * makes the profile and encodes the password
     * @param firstName firstName
     * @param lastName  lastName
     * @param username  unique username
     * @param notCoded_Pass not encoded password to be encoded and stored
     * @throws NoSuchAlgorithmException error in encoding password
     * @throws IllegalArgumentException at least one of input parameters is null
     */
    public Profile(String firstName,String lastName,String username,String notCoded_Pass) throws NoSuchAlgorithmException,IllegalArgumentException{
        if(firstName==null || lastName==null || username==null || notCoded_Pass==null)
            throw new IllegalArgumentException("At least one of Arguments is Null");
        this.firstName=firstName;
        this.lastName=lastName;
        this.username=username;

        MessageDigest msd= MessageDigest.getInstance(CODING_FORMAT);
        this.password=new BigInteger(1,msd.digest(notCoded_Pass.getBytes(StandardCharsets.UTF_8)));
        this.registerDate=LocalDateTime.now();

        followers=new HashSet<>();
        followings=new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    /**
     * sets the bio and checks to be less than 256 characters.
     * null inputs causes empty string for bio;
     * @param bio a text to be set as bio for profile
     * @throws IllegalArgumentException if input string is larger than 256 characters
     */
    protected void setBio(String bio) throws IllegalArgumentException{
        if(bio==null) this.bio="";
        else if(bio.length()<=256)
            this.bio = bio;
        else
            throw new IllegalArgumentException("Bio should be less than 256 characters");
    }

    public String getBio() {
        return bio;
    }

    public String getFirstName() {
        return firstName;
    }

    protected void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    protected void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    /**
     * gets a string and encodes it with similar encoding of profile's password and checks weather encoded input string
     * is equal to profile's password
     * @param pass the input string to check its equality to current password
     * @return returns true if pass is equal to current password
     * @throws NoSuchAlgorithmException if encoding of input password's string was not successful
     */
    protected boolean checkPassword(String pass) throws NoSuchAlgorithmException{
        MessageDigest msd= MessageDigest.getInstance(CODING_FORMAT);
        try {
            return password.equals(new BigInteger(1, msd.digest(pass.getBytes(StandardCharsets.UTF_8))));
        }
        catch (Exception ex){
            return false;}
    }

    /**
     * changes password if current password is inserted correctly in this method
     * @param currentPass the current password that this profile is using
     * @param newPass new password to be set instead of current password
     * @throws NoSuchAlgorithmException error in encoding password
     */
    protected void changePassword(String currentPass,String newPass) throws NoSuchAlgorithmException{
        if(checkPassword(currentPass)){
            MessageDigest msd= MessageDigest.getInstance(CODING_FORMAT);
            this.password=new BigInteger(1,msd.digest(newPass.getBytes(StandardCharsets.UTF_8)));
        }
    }

    /**
     * add profile to the set of followers
     * @param prf profile to be added
     */
    protected void addFollower(Profile prf){
        followers.add(prf);
    }

    /**
     * remove given profile from set of followers
     * @param prf profile to be removed
     * @throws NoSuchElementException if there isn't such a profile in the set of followers
     */
    protected void removeFollower(Profile prf) throws NoSuchElementException{
        if(!followers.contains(prf))
            throw new NoSuchElementException("Profile is not in the followers list to remove");
        followers.remove(prf);
    }

    /**
     * add the profile to the set of followings
     * @param prf the profile to be added
     */
    protected void addFollowing(Profile prf){
        followings.add(prf);
    }

    /**
     * remove the given profile from the set of followings
     * @param prf the profile to be removed
     * @throws NoSuchElementException if given profile not found in the set of followings
     */
    protected void removeFollowing(Profile prf) throws NoSuchElementException{
        if(!followings.contains(prf))
            throw new NoSuchElementException("Profile is not in the followings list to remove");
        followings.remove(prf);
    }

    /**
     * returns the Followings list in the format of ArrayList
     * @return the ArrayList of followings
     */
    protected ArrayList<Profile> getListOfFollowings(){
        return new ArrayList<>(followings);
    }
}
