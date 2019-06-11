package org.pursuit.stir.profilerv;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.pursuit.stir.MainHostListener;
import org.pursuit.stir.R;
import org.pursuit.stir.models.ImageUpload;
import org.pursuit.stir.models.User;

import java.util.List;

public class ProfileViewHolder extends RecyclerView.ViewHolder {
    private CardView coffeeImageCardView;
    private ImageView coffeeImageView;
    private ImageView coffeeBeanImageView;
    private TextView coffeeBeanCount;
    private TextView coffeeImageDate;

    public ProfileViewHolder(@NonNull View itemView) {
        super(itemView);
        coffeeImageCardView = itemView.findViewById(R.id.profile_cardView_itemView);
        coffeeImageView = itemView.findViewById(R.id.profile_imageView_itemView);
        coffeeBeanImageView = itemView.findViewById(R.id.profile_coffee_bean_image_itemView);
        coffeeBeanCount = itemView.findViewById(R.id.profile_coffee_bean_count_itemView);
        coffeeImageDate = itemView.findViewById(R.id.profile_image_date_itemView);
    }

    public void onBind(ImageUpload imageList) {
       // imageNameTextView.setText(imageName);

        Picasso.get()
                .load(imageList.getImageUrl())
                .placeholder(R.drawable.stir_logo)
                .fit()
                .centerCrop()
                .into(coffeeImageView);
        Log.d("lookhere", "onBind: " + imageList.getImageUrl());
    }
}

