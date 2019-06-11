package org.pursuit.stir;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.pursuit.stir.models.Chat;

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
    private MainHostListener mainHostListener;

    public CoffeeLoversReceivedMessageHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(final Chat chat, MainHostListener mainHostListener) {
      //  Picasso.get().load(imageUrl).into(userImageView);
        userTextView.setText(chat.getMessageUser());
        textTextView.setText(chat.getMessageText());
        timeTextView.setText(DateFormat.format("MM-dd-yyyy (HH:mm:ss)",
                chat.getMessageTime()));
    }
}
