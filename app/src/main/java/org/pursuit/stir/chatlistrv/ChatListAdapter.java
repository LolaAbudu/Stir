package org.pursuit.stir.chatlistrv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pursuit.stir.MainHostListener;
import org.pursuit.stir.R;
import org.pursuit.stir.models.User;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListViewHolder> {

    private List<String> chatKeyList;
    private MainHostListener mainHostListener;

    public ChatListAdapter(List<String> chatKeyList, MainHostListener mainHostListener) {
        this.chatKeyList = chatKeyList;
        this.mainHostListener = mainHostListener;
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatlist_itemview, viewGroup, false);
        return new ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder chatListViewHolder, int i) {
        chatListViewHolder.onBind(chatKeyList.get(i), mainHostListener);
    }

    @Override
    public int getItemCount() {
        return chatKeyList.size();
    }
}
