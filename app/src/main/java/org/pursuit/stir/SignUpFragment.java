package org.pursuit.stir;

import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;

public class SignUpFragment extends Fragment {

    private SignUpListener signUpListener;
    private FirebaseAuth firebaseAuth;

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

    private String username;
    private String email;
    private String password;
    private String confirm;

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
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUserInput();
                signUpNewUsers(email, password);
                signUpListener.replaceWithCoffeePrefFragment();
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
        if (TextUtils.isEmpty(confirm)) {
            confirmEditText.setError("Required.");
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
        return valid;
    }

    public void signUpNewUsers(String email, String password) {
        if (!validateUserInput()) {
            return;
        }
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
}
