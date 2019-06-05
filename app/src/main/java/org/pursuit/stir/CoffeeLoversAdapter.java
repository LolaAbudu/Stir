package org.pursuit.stir;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.pursuit.stir.models.Chat;

import java.util.List;

public class CoffeeLoversAdapter extends RecyclerView.Adapter<CoffeeLoversViewHolder> {

    private List<Chat> chatList;
    private MainHostListener mainHostListener;

    public CoffeeLoversAdapter(List<Chat> chatList, MainHostListener mainHostListener) {
        this.chatList = chatList;
        this.mainHostListener = mainHostListener;
    }

    @NonNull
    @Override
    public CoffeeLoversViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CoffeeLoversViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CoffeeLoversViewHolder coffeeLoversViewHolder, int i) {
        coffeeLoversViewHolder.onBind(chatList.get(i), mainHostListener);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
}
