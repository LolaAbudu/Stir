package org.pursuit.stir.models;

public class ProfileImage {
    private String imageUrl;
    private String userID;

    public ProfileImage(String imageUrl, String userID) {
        this.imageUrl = imageUrl;
        this.userID = userID;
    }

    ProfileImage() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
