package org.pursuit.stir.models;

public class Bean {

//    private FirebaseAuth firebaseAuth;

    public String imageName;
    private int beanCount;
//    public String username;

    public Bean() {
        // Default constructor required for calls to DataSnapshot.getValue(Bean.class)
    }

    public Bean(String imageName, int beanCount) {
        this.imageName = imageName;
       this.beanCount = beanCount;
    }


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getBeanCount() {
        return beanCount;
    }

    public void setBeanCount(int beanCount) {
        this.beanCount = beanCount;
    }

//    private int getBeanCount(DataSnapshot dataSnapshot) {
//        int beanCount = 0;
//        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//            User uInfo = new User();
//            uInfo.setUsername(ds.child(firebaseAuth.getCurrentUser().getUid()).getValue(User.class).getUsername());
//
//            Log.d(TAG, "showData: name: " + uInfo.getUsername());
//            ArrayList<String> array = new ArrayList<>();
//            array.add(uInfo.getUsername());
//            beanCount = array.size();
//        }
//            return beanCount;
//    }

}