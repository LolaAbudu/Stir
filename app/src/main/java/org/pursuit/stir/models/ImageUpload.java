package org.pursuit.stir.models;

public class ImageUpload {

    private String imageName;
    private String imageUrl;

    public ImageUpload() {
        //empty constructor needed
    }

    public ImageUpload(String imageName, String imageUrl) {

        if(imageName.trim().equals("")){
            imageName = "No Image Description";
        }
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
}
