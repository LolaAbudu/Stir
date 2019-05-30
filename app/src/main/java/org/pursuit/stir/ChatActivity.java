package org.pursuit.stir;

import android.support.v7.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {

//    private FirebaseListAdapter<ChatMessage> adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//
//        FloatingActionButton fab =
//                (FloatingActionButton)findViewById(R.id.fab);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText input = (EditText)findViewById(R.id.input);
//
//                // Read the input field and push a new instance
//                // of ChatMessage to the Firebase database
//                FirebaseDatabase.getInstance()
//                        .getReference()
//                        .push()
//                        .setValue(new Chat(input.getText().toString(),
//                                FirebaseAuth.getInstance()
//                                        .getCurrentUser()
//                                        .getDisplayName())
//                        );
//
//                // Clear the input
//                input.setText("");
//            }
//        });
//
//    }
//
//    private void displayChatMessages() {
//        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);
//
//        adapter = new FirebaseListAdapter<Chat>(this, ChatMessage.class,
//                R.layout.message, FirebaseDatabase.getInstance().getReference()) {
//            @Override
//            protected void populateView(View v, Chat model, int position) {
//                // Get references to the views of message.xml
//                TextView messageText = (TextView)v.findViewById(R.id.message_text);
//                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
//                TextView messageTime = (TextView)v.findViewById(R.id.message_time);
//
//                // Set their text
//                messageText.setText(model.getMessageText());
//                messageUser.setText(model.getMessageUser());
//
//                // Format the date before showing it
//                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
//                        model.getMessageTime()));
//            }
//        };
//
//        listOfMessages.setAdapter(adapter);
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode,
//                                    Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == SIGN_IN_REQUEST_CODE) {
//            if(resultCode == RESULT_OK) {
//                Toast.makeText(this,
//                        "Successfully signed in. Welcome!",
//                        Toast.LENGTH_LONG)
//                        .show();
//                displayChatMessages();
//            } else {
//                Toast.makeText(this,
//                        "We couldn't sign you in. Please try again later.",
//                        Toast.LENGTH_LONG)
//                        .show();
//
//                // Close the app
//                finish();
//            }
//        }
//
//    }
}
