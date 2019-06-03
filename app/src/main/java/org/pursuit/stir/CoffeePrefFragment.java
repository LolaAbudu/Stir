package org.pursuit.stir;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;

public class CoffeePrefFragment extends Fragment {
    @BindView(R.id.coff_pref_continue_button)
    Button continueButton;
    @BindView(R.id.radio_group_one)
    RadioGroup groupOne;
    @BindView(R.id.radio_group_two)
    RadioGroup groupTwo;
    @BindView(R.id.radio_group_three)
    RadioGroup groupThree;
    @BindView(R.id.radio_group_four)
    RadioGroup groupFour;

    private SignUpListener signUpListener;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String groupOneAnswer;
    private String groupTwoAnswer;
    private String groupThreeAnswer;
    private String groupFourAnswer;
    private RadioButton radioButtonOne;
    private RadioButton radioButtonTwo;
    private RadioButton radioButtonThree;
    private RadioButton radioButtonFour;


    public CoffeePrefFragment() {
    }

    public static CoffeePrefFragment newInstance() {
        CoffeePrefFragment fragment = new CoffeePrefFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignUpListener) {
            signUpListener = (SignUpListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coffee_pref, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        onRadioButtonGroupOneClicked(view);
        onRadioButtonGroupTwoClicked(view);
        onRadioButtonGroupThreeClicked(view);
        onRadioButtonGroupFourClicked(view);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //coffeePrefNewUsers(x and y);
                Intent intent = new Intent(getContext(), MainHostActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        signUpListener = null;
    }

    public void onRadioButtonGroupOneClicked(View view) {
        int id = groupOne.getCheckedRadioButtonId();
        if (id == -1) {
            Toast.makeText(getContext(), "Please select your answer", Toast.LENGTH_SHORT).show();
        } else {
            radioButtonOne = view.findViewById(id);
        }
    }

    public void onRadioButtonGroupTwoClicked(View view) {
        int id = groupTwo.getCheckedRadioButtonId();
        if (id == -1) {
            Toast.makeText(getContext(), "Please select your answer", Toast.LENGTH_SHORT).show();
        } else {
            radioButtonTwo = view.findViewById(id);
        }
    }

    public void onRadioButtonGroupThreeClicked(View view) {
        int id = groupThree.getCheckedRadioButtonId();
        if (id == -1) {
            Toast.makeText(getContext(), "Please select your answer", Toast.LENGTH_SHORT).show();
        } else {
            radioButtonThree = view.findViewById(id);
        }
    }

    public void onRadioButtonGroupFourClicked(View view) {
        int id = groupFour.getCheckedRadioButtonId();
        if (id == -1) {
            Toast.makeText(getContext(), "Please select your answer", Toast.LENGTH_SHORT).show();
        } else {
            radioButtonFour = view.findViewById(id);
        }
    }

    public void coffeePrefNewUsers(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                        groupOneAnswer = radioButtonOne.getText().toString();
                        groupTwoAnswer = radioButtonTwo.getText().toString();
                        groupThreeAnswer = radioButtonThree.getText().toString();
                        groupFourAnswer = radioButtonFour.getText().toString();
                        UserProfileChangeRequest requestOne = new UserProfileChangeRequest.Builder()
                                .setDisplayName(groupOneAnswer)
                                .build();
                        UserProfileChangeRequest requestTwo = new UserProfileChangeRequest.Builder()
                                .setDisplayName(groupTwoAnswer)
                                .build();
                        UserProfileChangeRequest requestThree = new UserProfileChangeRequest.Builder()
                                .setDisplayName(groupThreeAnswer)
                                .build();
                        UserProfileChangeRequest requestFour = new UserProfileChangeRequest.Builder()
                                .setDisplayName(groupFourAnswer)
                                .build();
                        currentUser.updateProfile(requestOne);
                        currentUser.updateProfile(requestTwo);
                        currentUser.updateProfile(requestThree);
                        currentUser.updateProfile(requestFour);
                        Log.d(TAG, "coffeePrefNewUsers: " + groupOneAnswer);
                        Log.d(TAG, "createUserWithEmail:success" + currentUser.getDisplayName());
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(getContext(), "Oops! Something went wrong. Please try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
