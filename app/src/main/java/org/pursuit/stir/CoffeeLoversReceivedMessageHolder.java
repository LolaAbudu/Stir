package org.pursuit.stir;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.pursuit.stir.models.Chat;
import org.pursuit.stir.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoffeeLoversReceivedMessageHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_message_profile)
    ImageView userImageView;
    @BindView(R.id.message_user_rec)
    TextView userTextView;
    @BindView(R.id.message_text_rec)
    TextView textTextView;
    @BindView(R.id.message_time_rec)
    TextView timeTextView;
    private String otherChatId;
    private String profilePhotoUrlString;

    public CoffeeLoversReceivedMessageHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(final Chat chat) {
        findOtherChatId(chat);
        userTextView.setText(chat.getMessageUser());
        textTextView.setText(chat.getMessageText());
        timeTextView.setText(DateFormat.format("MMMM dd yyyy (hh:mm aa)",
                chat.getMessageTime()));
    }

    public void findOtherChatId(Chat chat) {
        String otherUserName = chat.getMessageUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query imagesQuery = databaseReference.child("users").orderByChild("username").equalTo(otherUserName);
        imagesQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final User user = dataSnapshot.getValue(User.class);
                otherChatId = user.getChatId();
                findProfilePhoto(otherChatId);
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

    public String findProfilePhoto(String otherChatId) {
        DatabaseReference userDBReference = FirebaseDatabase.getInstance().getReference("profilePhoto");
        userDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    if (postSnapShot.getKey().contains(otherChatId)) {
                        profilePhotoUrlString = postSnapShot.getValue().toString();
                        Log.d("helpme", "onDataChange: " + profilePhotoUrlString);
                        Picasso.get().load(profilePhotoUrlString).into(userImageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return profilePhotoUrlString;
    }
}
