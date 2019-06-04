package org.pursuit.stir;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.pursuit.stir.models.Chat;

import java.util.ArrayList;
import java.util.List;

public class CoffeeLoversFragment extends Fragment {

    private static final int SIGN_IN_REQUEST_CODE = 1;
    private static final int RESULT_OK = -1;
//    private FirebaseRecyclerAdapter<Chat, CoffeeLoversViewHolder> adapter;
    private CoffeeLoversAdapter adapter;
    private RecyclerView listOfMessages;
    private EditText input;
    private List<Chat> chatList;
    private MainHostListener mainHostListener;
    private static final String TAG = "evelyn";
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
        if (context instanceof MainHostListener) {
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
        listOfMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        chatList = new ArrayList<>();

        fab.setOnClickListener(v -> {

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
        });
        // added line below because msgs weren't showing in app
        displayChatMessages();
    }

    private void displayChatMessages() {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("chat");

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

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        Query query = reference.child("chat");
//        FirebaseRecyclerOptions<Chat> options = new FirebaseRecyclerOptions.Builder<Chat>()
//                .setQuery(query, Chat.class)
//                .build();
//        Log.d(TAG, "displayChatMessages: test");

//        adapter = new FirebaseRecyclerAdapter<Chat, CoffeeLoversViewHolder>(options) {
//            @NonNull
//            @Override
//            public CoffeeLoversViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message, viewGroup, false);
//                return new CoffeeLoversViewHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull CoffeeLoversViewHolder holder, int position, @NonNull Chat model) {
//                holder.onBind(model);
//                Log.d(TAG, "onBindViewHolder: " + model.getMessageText());
//            }

//            @Override
//            protected void populateView(View v, Chat model, int position) {
//                TextView messageText = v.findViewById(R.id.message_text);
//                TextView messageUser = v.findViewById(R.id.message_user);
//                TextView messageTime = v.findViewById(R.id.message_time);
//
//                //set their text
//                messageText.setText(model.getMessageText());
//                messageUser.setText(model.getMessageUser());
//                // Format the date before showing it
//                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
//                        model.getMessageTime()));
//            }
//        };
//        listOfMessages.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
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
