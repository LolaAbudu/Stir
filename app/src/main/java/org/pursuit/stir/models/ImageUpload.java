package org.pursuit.stir.models;

import com.google.firebase.database.Exclude;

public class ImageUpload {

    private String imageName;
    private String imageUrl;
//    private int beanCount;

    public User user;

    @Exclude
    public int likes = 0;

    @Exclude
    public boolean hasLiked = false;

    @Exclude
    public String userLike;

    public ImageUpload() {
    }

    public ImageUpload(String imageName, String imageUrl) {

        if (imageName.trim().equals("")) {
            imageName = "No Image Description";
        }
        this.imageName = imageName;
        this.imageUrl = imageUrl;
//        this.beanCount = beanCount;
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

//    public int getBeanCount() {
//        return beanCount;
//    }
//
//    public void setBeanCount(int beanCount) {
//        this.beanCount = beanCount;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addLike() {
        this.likes++;
    }

    public void removeLike() {
        this.likes--;
    }
}
