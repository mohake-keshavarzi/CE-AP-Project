package Server;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
public class Profile {
    private String firstName;
    private String lastName;
    private String bio;
    final private String username;
    final private LocalDateTime registerDate;
    private BigInteger password;

    public Profile(String firstName,String lastName,String username,String notCoded_Pass) throws NoSuchAlgorithmException{
        this.firstName=firstName;
        this.lastName=lastName;
        this.username=username;
        MessageDigest msd= MessageDigest.getInstance("SHA-256");
        this.password=new BigInteger(1,msd.digest(notCoded_Pass.getBytes(StandardCharsets.UTF_8)));
        this.registerDate=LocalDateTime.now();

    }

    public String getUsername() {
        return username;
    }
}
