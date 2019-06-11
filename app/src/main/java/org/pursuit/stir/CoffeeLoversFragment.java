package org.pursuit.stir;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.pursuit.stir.models.Chat;

import java.util.ArrayList;
import java.util.List;

public class CoffeeLoversFragment extends Fragment {

    private static final int SIGN_IN_REQUEST_CODE = 1;
    private static final int RESULT_OK = -1;
    private CoffeeLoversAdapter adapter;
    private RecyclerView listOfMessages;
    private EditText input;
    private List<Chat> chatList;
    private MainHostListener mainHostListener;
    private static final String CHAT_KEY = "chat_key";
    private String chatKey;
    private static final String TAG = "evelyn";

    public CoffeeLoversFragment() {
        // Required empty public constructor
    }

    public static CoffeeLoversFragment newInstance(String chatKey) {
        CoffeeLoversFragment fragment = new CoffeeLoversFragment();
        Bundle args = new Bundle();
        args.putString(CHAT_KEY, chatKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainHostListener) {
            mainHostListener = (MainHostListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            chatKey = getArguments().getString(CHAT_KEY);
            Log.d(TAG, "onCreate: " + chatKey);
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
        listOfMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        chatList = new ArrayList<>();

        fab.setOnClickListener(v -> {
            if(input.getText().toString().trim().length() <= 0) {
                Snackbar snackbar = Snackbar.make(view, "Please enter a message!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }else {
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("chat")
                        .child(chatKey)
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
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("chat").child(chatKey);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    chatList.clear();

                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        Log.d(TAG, "onDataChange: " + postSnapshot.getValue());
                        Chat chat = postSnapshot.getValue(Chat.class);
                        chatList.add(chat);
                    }
                    adapter = new CoffeeLoversAdapter(chatList, mainHostListener);
                    listOfMessages.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    listOfMessages.scrollToPosition(chatList.size() - 1);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
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
