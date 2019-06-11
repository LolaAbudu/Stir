package org.pursuit.stir;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.pursuit.stir.models.Chat;

import java.util.List;

public class CoffeeLoversAdapter extends RecyclerView.Adapter {
    private List<Chat> chatList;
    private MainHostListener mainHostListener;
    CoffeeLoversReceivedMessageHolder coffeeLoversReceivedMessageHolder;
    CoffeeLoversSentMessageHolder coffeeLoversSentMessageHolder;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    public CoffeeLoversAdapter(List<Chat> chatList, MainHostListener mainHostListener) {
        this.chatList = chatList;
        this.mainHostListener = mainHostListener;
    }

    @Override
    public int getItemViewType(int position) {
        Chat message = chatList.get(position);
        // if (message.getMessageUser().equals(firebaseDatabase.getReference().getDatabase().ge())) {

        if (message.getMessageUser().equals(currentUser.getDisplayName())) {

            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
// If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        if (i == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.message_sent, viewGroup, false);
            return new CoffeeLoversSentMessageHolder(view);
        } else if (i == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.message_received, viewGroup, false);
            return new CoffeeLoversReceivedMessageHolder(view);
        }
        return null;
        //return new CoffeeLoversViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                coffeeLoversSentMessageHolder.onBind(chatList.get(i), mainHostListener);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                coffeeLoversReceivedMessageHolder.onBind(chatList.get(i), mainHostListener);
        }

    }
    //coffeeLoversViewHolder.onBind(chatList.get(i), mainHostListener);

    @Override
    public int getItemCount() {
        return chatList.size();
    }
}
