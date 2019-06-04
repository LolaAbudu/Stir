package org.pursuit.stir;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.pursuit.stir.models.Chat;

public class CoffeeLoversFragment extends Fragment {

    private static final int SIGN_IN_REQUEST_CODE = 1;
    private static final int RESULT_OK = -1;
    private FirebaseListAdapter<Chat> adapter;
    private ListView listOfMessages;
    private EditText input;
    private MainHostListener mainHostListener;
//    private StorageReference storageReference;
//    private DatabaseReference databaseReference;

    public CoffeeLoversFragment() {
        // Required empty public constructor
    }

    public static CoffeeLoversFragment newInstance() {
        CoffeeLoversFragment fragment = new CoffeeLoversFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainHostListener){
            mainHostListener = (MainHostListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coffee_lovers, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainHostListener = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        input = view.findViewById(R.id.input);
        listOfMessages = view.findViewById(R.id.list_of_messages);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("chat")
                        .push()
                        .setValue(new Chat(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                    .getCurrentUser()
                                    .getDisplayName()));
                //clear the input
                input.setText("");
            }
        });
        // added line below because msgs weren't showing in app
        displayChatMessages();
    }

    private void displayChatMessages() {
        adapter = new FirebaseListAdapter<Chat>(getActivity(), Chat.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference().child("chat")) {
            @Override
            protected void populateView(View v, Chat model, int position) {
                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);

                //set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };
        listOfMessages.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(getContext(),
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG)
                        .show();
                displayChatMessages();
            } else {
                Toast.makeText(getContext(),
                        "We couldn't sign you in. Please try again later.",
                        Toast.LENGTH_LONG)
                        .show();

                // Close the app
                getActivity().finish();
            }
        }
    }
}
