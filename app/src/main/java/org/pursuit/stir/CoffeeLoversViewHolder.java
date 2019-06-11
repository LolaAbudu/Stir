package org.pursuit.stir;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import org.pursuit.stir.models.Chat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoffeeLoversViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.message_user)
    TextView userTextView;
    @BindView(R.id.message_text)
    TextView textTextView;
    @BindView(R.id.message_time)
    TextView timeTextView;
    private MainHostListener mainHostListener;

    public CoffeeLoversViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(final Chat chat, MainHostListener mainHostListener) {
        userTextView.setText(chat.getMessageUser());
        textTextView.setText(chat.getMessageText());
        timeTextView.setText(DateFormat.format("MMMM dd yyyy (hh:mm aa)",
                chat.getMessageTime()));
    }
}
