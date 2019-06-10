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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.pursuit.stir.models.ImageUpload;
import org.pursuit.stir.models.User;

public class ProfileFragment extends Fragment {

    private TextView profileName;
    private TextView profileCoffeePref;
    private String questionOne;
    private String questionTwo;


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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileName = view.findViewById(R.id.profile_username_textView);
        profileCoffeePref = view.findViewById(R.id.profile_coffee_pref_textView);
        Log.d("LOOKHERE profilefrag", "onViewCreated: " + currentUser.getDisplayName());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questionOne = dataSnapshot.getValue(User.class).getCoffeeAnswerOne();
                Log.d(TAG, "answerONE" + questionOne);

                questionTwo = dataSnapshot.getValue(User.class).getCoffeeAnswerTwo();
                if (currentUser != null) {
                    profileName.setText(currentUser.getDisplayName());
                    profileCoffeePref.setText("Favorite coffee: " + questionOne + " roast & " + questionTwo);
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child("users").child(mAuth.getUid())
                            .child("coffeeAnswerOne");

                    Log.d(TAG, "onViewCreated: " + currentUser.getDisplayName());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if (currentUser != null) {
            profileName.setText(currentUser.getDisplayName());
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child("users").child(mAuth.getUid())
                    .child("coffeeAnswerOne");

            Log.d(TAG, "onViewCreated: " + currentUser.getDisplayName());

        } else {
            Log.d(TAG, "onViewCreated: currentuser = null");
        }
        // finding current user's images only
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();
        Query imagesQuery = databaseReference2.child("imageUploads").orderByChild("userID").equalTo(currentUser.getUid());
        imagesQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final ImageUpload image = dataSnapshot.getValue(ImageUpload.class);
                Log.d(TAG, "onChildAdded: " + image.getImageName() + image.getUserID());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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