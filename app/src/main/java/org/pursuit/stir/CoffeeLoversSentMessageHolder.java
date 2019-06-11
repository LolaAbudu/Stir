package org.pursuit.stir;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import org.pursuit.stir.models.Chat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoffeeLoversSentMessageHolder extends RecyclerView.ViewHolder {

//    @BindView(R.id.message_user_sent)
//    TextView userTextView;
    @BindView(R.id.message_text_sent)
    TextView textTextView;
    @BindView(R.id.message_time_sent)
    TextView timeTextView;
    private MainHostListener mainHostListener;

    public CoffeeLoversSentMessageHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(final Chat chat, MainHostListener mainHostListener) {
       // userTextView.setText(chat.getMessageUser());
        textTextView.setText(chat.getMessageText());
        timeTextView.setText(DateFormat.format("MM-dd-yyyy (HH:mm:ss)",
                chat.getMessageTime()));
    }
}
