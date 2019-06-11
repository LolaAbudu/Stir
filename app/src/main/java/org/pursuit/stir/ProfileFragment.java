package org.pursuit.stir;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.pursuit.stir.homerv.HomeAdapter;
import org.pursuit.stir.models.ImageUpload;
import org.pursuit.stir.models.ProfileImage;
import org.pursuit.stir.models.User;
import org.pursuit.stir.profilerv.ProfileAdapter;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ProfileFragment extends Fragment {
    private ImageView profileImageView;
    private TextView profileName;
    private TextView profileCoffeePref;
    private String questionOne;
    private String questionTwo;
    // private List<ImageUpload> imageList = new ImageUpload();

    private FirebaseAuth firebaseAuth;
    private DatabaseReference userDBReference;
    private DatabaseReference databaseReference;

    private RecyclerView recyclerView;
    private ProfileAdapter profileAdapter;

    private DatabaseReference profileUserRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private String imageName;
    //private String imageUrl;
    private String userID;
    private String profileCurrentUserId;
    private static final String TAG = "stuff";
    private List<ProfileImage> profileImageList;



    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
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
        profileImageView = view.findViewById(R.id.profile_user_profile_image);

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

        userDBReference = FirebaseDatabase.getInstance().getReference("profilePhoto");
        userDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG, "Sanap"+dataSnapshot.getValue().toString());

                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
              //  dataSnapshot.getValue().toString();


                    if (postSnapShot.getKey().equals(currentUser.getUid())) {
                        String profilePhotoUrlString =postSnapShot.getValue().toString();
                                Picasso.get()
        .load(profilePhotoUrlString)
        .into(profileImageView);
                    }
                }
                recyclerView = view.findViewById(R.id.profile_itemview_recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(profileAdapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //imageNameTextView.setText(beanCount);
        //  Picasso.get().load(imageUrl).into(imageURLImageView);

//        recyclerView = view.findViewById(R.id.profile_itemview_recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(profileAdapter);

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