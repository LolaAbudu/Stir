package org.pursuit.stir.models;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class Bean {

    private FirebaseAuth firebaseAuth;

    public String imageName;
    public String username;

    public Bean() {
        // Default constructor required for calls to DataSnapshot.getValue(Bean.class)
    }

    public Bean(String imageName, String username) {
        this.imageName = imageName;
        this.username = username;
    }

    private int getBeanCount(DataSnapshot dataSnapshot) {
        int beanCount = 0;
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            User uInfo = new User();
            uInfo.setUsername(ds.child(firebaseAuth.getCurrentUser().getUid()).getValue(User.class).getUsername());

            Log.d(TAG, "showData: name: " + uInfo.getUsername());
            ArrayList<String> array = new ArrayList<>();
            array.add(uInfo.getUsername());
            beanCount = array.size();
        }
            return beanCount;
    }

}