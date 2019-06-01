package org.pursuit.stir.shoprv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
//    @BindView(R.id.fragment_shop_distance_textview)
//    TextView distance;

    private MainHostListener listener;
    private FoursquareVenue venue;

    String id;
    String photoUrl;
    double latitude;
    double longitude;

    public ShopViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(final Pair<FoursquareResults, FourSquareVenuePhoto> results,
                       final MainHostListener listener) {
//        FoursquareVenue venue = pair.first;
//        FourSquareVenuePhoto photo = pair.second;

//        String photoUrlString = photo.getResponse().getPhotos().getItems().get(0).getPrefix() + "100x100" +
//                photo.getResponse().getPhotos().getItems().get(0).getSuffix();
//        photoCall(foursquareResults.getVenue().getId());

        shopName.setText(results.first.getVenue().getName());
//        shopName.setText(foursquareResults.getVenue().getName());
//        distance.setText(foursquareResults.getVenue().getLocation().getDistance());
        venue = results.first.getVenue();
        FourSquareVenuePhoto photo = results.second;
        String photoUrl = photo.getResponse().getPhotos().getItems().get(0).getPrefix() + "200x200" +
                photo.getResponse().getPhotos().getItems().get(0).getSuffix();

        Glide.with(itemView.getContext())
                .load(photoUrl)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.mipmap.ic_logo_placeholder)
                .into(shopImage);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.moveToMap(venue);
            }
        });
    }

//    public void photoCall(String photoId) {
//        RetrofitSingleton.getInstance()
//                .create(FoursquareService.class)
//                .getCoffeeVenuePhoto(photoId,
//                        BuildConfig.FoursquareClientID,
//                        BuildConfig.FoursquareClientSecret)
//                .enqueue(new Callback<FourSquareVenuePhoto>() {
//                    @Override
//                    public void onResponse(Call<FourSquareVenuePhoto> call, Response<FourSquareVenuePhoto> response) {
//                        Log.d("evelyn", "onResponse: " + response.body().getResponse().getPhotos().getItems().get(0).getPrefix());
//                        photoUrl = response.body().getResponse().getPhotos().getItems().get(0).getPrefix() +
//                                "200x200" +
//                                response.body().getResponse().getPhotos().getItems().get(0).getSuffix();
//                        Picasso.get()
//                                .load(photoUrl)
//                                .placeholder(R.mipmap.ic_launcher)
//                                .into(shopImage);
//                    }
//
//                    @Override
//                    public void onFailure(Call<FourSquareVenuePhoto> call, Throwable t) {
//
//                    }
//                });
//        Log.d("evelynphoto", "photoCall: " + photoUrl);
//    }

}
