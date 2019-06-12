package org.pursuit.stir.chatlistrv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.pursuit.stir.DetailFragment;
import org.pursuit.stir.MainHostListener;
import org.pursuit.stir.R;
import org.pursuit.stir.models.Chat;
import org.pursuit.stir.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;

public class ChatListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.chatlist_itemview_username)
    TextView usernameTextView;
    @BindView(R.id.chatlist_itemview_last_message)
    TextView lastMessage;

    private String otherChatId;
    private String username;

    public ChatListViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(final String chatKeyString, MainHostListener mainHostListener) {

        getUsername(chatKeyString);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainHostListener.replaceWithCoffeeLoversFragment(chatKeyString);
            }
        });
    }

    public String getOtherChatId(String chatKey) {
        String myChatId = FirebaseAuth.getInstance().getUid().substring(22);
        if (myChatId.equals(chatKey.substring(7))) {
            otherChatId = chatKey.substring(0, 6);
        } else {
            otherChatId = chatKey.substring(7);
        }
        return otherChatId;
    }

    private String getUsername(String chatKeyString) {
        otherChatId = getOtherChatId(chatKeyString);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    if (otherChatId.equals(user.getChatId())) {
                        username = user.getUsername();
                        usernameTextView.setText(username);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return username;
    }
}
