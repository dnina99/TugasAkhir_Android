package umn.ac.id.tugasakhir_android.Model;

public class User {
    private String username;
    private String password;
    private String name;
    private String email;
    private String mobileNumber;
    private String picture;

    public User(){

    }

    public User(String username, String password, String name, String email, String mobileNumber, String picture) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.picture = picture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
