package org.pursuit.stir.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ImageUpload {

    private String imageName;
    private String imageUrl;
    private String userID;

    public ImageUpload() {
    }

    public ImageUpload(String imageName, String imageUrl, String userID) {

        if (imageName.trim().equals("")) {
            imageName = "No Image Description";
        }
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.userID = userID;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
