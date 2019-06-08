package org.pursuit.stir.shoprv;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.pursuit.stir.MainHostListener;
import org.pursuit.stir.R;
import org.pursuit.stir.models.FourSquareVenuePhoto;
import org.pursuit.stir.models.FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults;
import org.pursuit.stir.models.FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults.FoursquareVenue;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_shop_textview)
    TextView shopName;
    @BindView(R.id.fragment_shop_itemview_image)
    ImageView shopImage;
    @BindView(R.id.fragment_shop_distance_textview)
    TextView distance;

    private FoursquareVenue venue;

    public ShopViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(final Pair<FoursquareResults, FourSquareVenuePhoto> results,
                       final MainHostListener listener) {
        shopName.setText(results.first.getVenue().getName());

        double milesAway = results.first.getVenue().getLocation().getDistance() * 0.000621371;
        distance.setText(String.format( "%.2f", milesAway ) + " miles away");


        venue = results.first.getVenue();
        FourSquareVenuePhoto photo = results.second;
        String photoUrl = photo.getResponse().getPhotos().getItems().get(0).getPrefix() + "200x200" +
                photo.getResponse().getPhotos().getItems().get(0).getSuffix();

        Glide.with(itemView.getContext())
                .load(photoUrl)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.mipmap.ic_logo_placeholder)
                .into(shopImage);

        itemView.setOnClickListener(v -> listener.moveToMap(venue));
    }

}
