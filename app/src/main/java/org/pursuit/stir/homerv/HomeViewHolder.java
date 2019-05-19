package org.pursuit.stir.homerv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.pursuit.stir.R;
import org.pursuit.stir.models.ImageUpload;

public class HomeViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageview;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);

        imageview = itemView.findViewById(R.id.home_itemview_image_view);
    }

    public void onBind(ImageUpload imageUpload) {
        Picasso.get()
                .load(imageUpload.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imageview);
    }
}
