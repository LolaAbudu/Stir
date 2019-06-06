package org.pursuit.stir.models;

import java.util.List;

public class User {

    private String username;
    private String usrID;
    private String coffeeAnswerOne;
    private String coffeeAnswerTwo;
    private List<ImageUpload> imageList;
    private String imageName;
    private String imageUrl;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String usrID, String coffeeAnswerOne, String coffeeAnswerTwo, List<ImageUpload> imageList, String imageName, String imageUrl) {
        this.username = username;
        this.usrID = usrID;
        this.coffeeAnswerOne = coffeeAnswerOne;
        this.coffeeAnswerTwo = coffeeAnswerTwo;
        this.imageList = imageList;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public List<ImageUpload> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageUpload> imageList) {
        this.imageList = imageList;
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
