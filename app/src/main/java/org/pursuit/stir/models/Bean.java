package org.pursuit.stir.models;

public class Bean {

    public String imageName;
    private String beanCount;

    public Bean() {
    }

    public Bean(String imageName, String beanCount) {
        this.imageName = imageName;
        this.beanCount = beanCount;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getBeanCount() {
        return beanCount;
    }

    public void setBeanCount(String beanCount) {
        this.beanCount = beanCount;
    }

}