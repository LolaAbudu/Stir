package org.pursuit.stir;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class SignUpFragment extends Fragment {

    private SignUpListener signUpListener;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private SharedPreferences prefs;
    private static final String USERNAMEKEY = "USERNAMEKEY";
    private static final String EMAILKEY = "EMAILKEY";

    @BindView(R.id.sign_up_username_editText)
    EditText usernameEditText;
    @BindView(R.id.sign_up_email_editText)
    EditText emailEditText;
    @BindView(R.id.sign_up_password_editText)
    EditText passwordEditText;
    @BindView(R.id.sign_up_confirm_editText)
    EditText confirmEditText;
    @BindView(R.id.sign_up_checkbox)
    CheckBox acknowledgeCheckbox;
    @BindView(R.id.sign_up_continue_button)
    Button continueButton;
    @BindView(R.id.privacy_checkbox)
    CheckBox privacyCheckbox;
    @BindView(R.id.privacy_policy_text)
    TextView privacyPolicyText;

    private String username;
    private String email;
    private String password;
    private String confirm;
    private String userID;

    public SignUpFragment() {
    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
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
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());



//        if (savedInstanceState != null) {
//            username = savedInstanceState.getString(USERNAMEKEY);
//            email = savedInstanceState.getString(EMAILKEY);
//            Log.d(TAG, "onCreate: " + username + email);
//            usernameEditText.setText(username);
//            emailEditText.setText(email);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Log.d(TAG, "onCreate: " + username + email);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        usernameEditText.setText(prefs.getString(USERNAMEKEY, ""));
        emailEditText.setText(prefs.getString(EMAILKEY, ""));

        if (savedInstanceState != null) {
            username = savedInstanceState.getString(USERNAMEKEY);
            email = savedInstanceState.getString(EMAILKEY);
            Log.d(TAG, "onCreate: " + username + email);
            usernameEditText.setText(username);
            emailEditText.setText(email);
        }
        continueButton.setOnClickListener(v -> {
            //TODO user authentication
//            if (!username.equals("") && !email.equals("") && !password.equals("") && !confirm.equals("")) {
//                User user = new User(username);
//                databaseReference.child("users").child(userID).setValue(user);
            if (validateUserInput()) {
                Toast.makeText(v.getContext(), "New Information has been saved.",
                        Toast.LENGTH_SHORT).show();
                signUpNewUsers(email, password);
                signUpListener.replaceWithCoffeePrefFragment();
            }
        });


        privacyPolicyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpListener.moveToPrivacyPolicyActivity();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        signUpListener = null;

    }

    private boolean validateUserInput() {
        boolean valid = true;
        username = usernameEditText.getText().toString();
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        confirm = confirmEditText.getText().toString();
        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("Required.");
            valid = false;
        } else {
            usernameEditText.setError(null);
        }
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Required.");
            valid = false;
        } else {
            emailEditText.setError(null);
        }
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Required.");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }
        if (TextUtils.isEmpty(confirm) || !confirm.equals(password)) {
            confirmEditText.setError("Required. Must match password");
            valid = false;
        } else {
            confirmEditText.setError(null);
        }
        if (!acknowledgeCheckbox.isChecked()) {
            acknowledgeCheckbox.setError("Required.");
            valid = false;
        } else {
            acknowledgeCheckbox.setError(null);
        }
        if (!privacyCheckbox.isChecked()) {
            privacyCheckbox.setError("Required.");
            valid = false;
        } else {
            privacyCheckbox.setError(null);
        }
        return valid;
    }

    public void signUpNewUsers(String email, String password) {
        if (!validateUserInput()) {
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                        username = usernameEditText.getText().toString();
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build();
                        currentUser.updateProfile(request);
                        Log.d(TAG, "signUpNewUsers: " + username);
                        Log.d(TAG, "createUserWithEmail:success" + currentUser.getDisplayName());
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(getContext(), "Oops! Something went wrong. Please try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        username = usernameEditText.getText().toString();
        email = emailEditText.getText().toString();
        Log.d(TAG, "onSaveInstanceState: " + username + email);
        outState.putString(USERNAMEKEY, username);
        outState.putString(EMAILKEY, email);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USERNAMEKEY, username);
        editor.putString(EMAILKEY, email);
        editor.apply();


        super.onSaveInstanceState(outState);
    }


}

