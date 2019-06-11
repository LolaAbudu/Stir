package org.pursuit.stir;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import org.pursuit.stir.models.ImageUpload;
import org.pursuit.stir.models.User;

import java.util.ArrayList;

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
//    @BindView(R.id.radio_group_four)
//    RadioGroup groupFour;

    private SignUpListener signUpListener;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, databaseReferenceTwo;

    private StorageReference storageReference;

    private String groupOneAnswer;
    private String groupTwoAnswer;
    private String imageName;
    private String imageUrl;
    private String photoImageUrl;

    private RadioButton radioButtonOne;
    private RadioButton radioButtonTwo;


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

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonGroupOneClicked(view);
                onRadioButtonGroupTwoClicked(view);
                User user = new User(firebaseAuth.getCurrentUser().getDisplayName(),
                        firebaseAuth.getCurrentUser().getUid(), groupOneAnswer, groupTwoAnswer, new ArrayList<ImageUpload>(),imageName,imageUrl,photoImageUrl);
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("users").child(firebaseAuth.getUid())
                        .setValue(user);

                Intent intent = new Intent(getContext(), MainHostActivity.class);
                startActivity(intent);

            }
        });
    }

//    @OnClick(R.id.coff_pref_continue_button)
//    public void dostuff(){
//        Intent intent = new Intent(getContext(), MainHostActivity.class);
//        startActivity(intent);
//    }


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
            groupOneAnswer = radioButtonOne.getText().toString();

        }
    }

    public void onRadioButtonGroupTwoClicked(View view) {
        int id = groupTwo.getCheckedRadioButtonId();
        if (id == -1) {
            Toast.makeText(getContext(), "Please select your answer", Toast.LENGTH_SHORT).show();
        } else {
            radioButtonTwo = view.findViewById(id);
            groupTwoAnswer = radioButtonTwo.getText().toString();
            Log.d(TAG, "grouptwoanswer:" + groupTwoAnswer);
        }
    }

}
