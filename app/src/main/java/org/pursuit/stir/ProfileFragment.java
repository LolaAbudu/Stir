package org.pursuit.stir;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ProfileFragment extends Fragment {

    private TextView profileName;

    private DatabaseReference profileUserRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private String profileCurrentUserId;
    private static final String TAG = "stuff";


    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        mAuth = FirebaseAuth.getInstance();
//        profileCurrentUserId = mAuth.getCurrentUser().getUid();
//        profileUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(profileCurrentUserId);
//        profileName = view.findViewById(R.id.profile_username_textView);
//
//        profileUserRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//           if(dataSnapshot.exists()){
//               String userProfileName = dataSnapshot.child("username").getValue().toString();
//
//              // userProfileName.setText(profileName);
//
//           }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileName = view.findViewById(R.id.profile_username_textView);
        if (currentUser != null) {
            profileName.setText(currentUser.getDisplayName());
            Log.d(TAG, "onViewCreated: " + currentUser.getDisplayName());
        } else {
            Log.d(TAG, "onViewCreated: currentuser = null");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }
}
