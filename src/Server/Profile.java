package Server;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
public class Profile {
    private String firstName;
    private String lastName;
    private String bio="";
    final private String username;
    final private LocalDateTime registerDate;
    private BigInteger password;
    final private String CODING_FORMAT="SHA-256";

    public Profile(String firstName,String lastName,String username,String notCoded_Pass) throws NoSuchAlgorithmException{
        this.firstName=firstName;
        this.lastName=lastName;
        this.username=username;
        MessageDigest msd= MessageDigest.getInstance(CODING_FORMAT);
        this.password=new BigInteger(1,msd.digest(notCoded_Pass.getBytes(StandardCharsets.UTF_8)));
        this.registerDate=LocalDateTime.now();

    }

    public String getUsername() {
        return username;
    }

    protected void setBio(String bio) {
        if(bio==null) this.bio="";
        else if(bio.length()<=256)
            this.bio = bio;
        else
            throw new IllegalStateException("Bio should be less than 256 characters");
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

    protected boolean checkPassWord(String pass) throws NoSuchAlgorithmException{
        MessageDigest msd= MessageDigest.getInstance(CODING_FORMAT);
        return password.equals(new BigInteger(1, msd.digest(pass.getBytes(StandardCharsets.UTF_8))));
    }
}
