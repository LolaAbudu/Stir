package org.pursuit.stir.models;

public class User {

    private String username;
    private String usrID;
    private String coffeeAnswerOne;
    private String coffeeAnswerTwo;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String usrID, String coffeeAnswerOne, String coffeeAnswerTwo) {
        this.username = username;
        this.usrID = usrID;
        this.coffeeAnswerOne = coffeeAnswerOne;
        this.coffeeAnswerTwo = coffeeAnswerTwo;
    }

    public String getCoffeeAnswerOne() {
        return coffeeAnswerOne;
    }

    public void setCoffeeAnswerOne(String coffeeAnswerOne) {
        this.coffeeAnswerOne = coffeeAnswerOne;
    }

    public String getCoffeeAnswerTwo() {
        return coffeeAnswerTwo;
    }

    public void setCoffeeAnswerTwo(String coffeeAnswerTwo) {
        this.coffeeAnswerTwo = coffeeAnswerTwo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsrID() {
        return usrID;
    }

    public void setUsrID(String usrID) {
        this.usrID = usrID;
    }
}
