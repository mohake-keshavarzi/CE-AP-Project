package main.java.org.ce.ap.client;

public class ProfileInfo {
    private String firstname,lastname,username,bio;

    ProfileInfo(String firstname, String lastname, String username, String bio){
        this.firstname=firstname;
        this.lastname=lastname;
        this.username=username;
        this.bio=bio;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }
}
