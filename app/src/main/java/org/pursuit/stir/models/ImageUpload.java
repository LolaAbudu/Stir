package org.pursuit.stir.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class ImageUpload {

    private String imageName;
    private String imageUrl;
<<<<<<< Updated upstream
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();
//    private int beanCount;
=======
    private int beanCounter;
>>>>>>> Stashed changes

    public User user;

    @Exclude
    public int likes = 0;

    @Exclude
    public boolean hasLiked = false;

    @Exclude
    public String userLike;

    public ImageUpload() {
    }

    public ImageUpload(String imageName, String imageUrl, int beanCounter) {

        if (imageName.trim().equals("")) {
            imageName = "No Image Description";
        }
        this.imageName = imageName;
        this.imageUrl = imageUrl;
<<<<<<< Updated upstream
//        this.beanCount = beanCount;
=======
        this.beanCounter = beanCounter;
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
//    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("starCount", starCount);
//        result.put("stars", stars);
//
//        return result;
//    }


//    public int getBeanCount() {
//        return beanCount;
//    }
//
//    public void setBeanCount(int beanCount) {
//        this.beanCount = beanCount;
//    }
=======
    public int getBeanCounter() {
        return beanCounter;
    }

    public void setBeanCounter(int beanCounter) {
        this.beanCounter = beanCounter;
    }
>>>>>>> Stashed changes

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
