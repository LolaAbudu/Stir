package org.pursuit.stir.models;

import com.google.firebase.database.IgnoreExtraProperties;

import org.jetbrains.annotations.NotNull;

@IgnoreExtraProperties
public class ImageUpload {

    private String imageName;
    private String shopName;
    private String imageUrl;
    private String userID;
    private int beanCount;

    public ImageUpload() {
    }

    public ImageUpload(@NotNull String imageName, String shopName, String imageUrl, String userID, int beanCount) {

        if (imageName.trim().equals("")) {
            imageName = "No Image Description";
        }
        this.imageName = imageName;
        this.shopName = shopName;
        this.imageUrl = imageUrl;
        this.userID = userID;
        this.beanCount = beanCount;

    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public int getBeanCount() {
        return beanCount;
    }
}
