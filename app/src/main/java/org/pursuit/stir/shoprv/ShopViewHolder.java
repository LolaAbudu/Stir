package org.pursuit.stir.shoprv;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.pursuit.stir.BuildConfig;
import org.pursuit.stir.MainHostListener;
import org.pursuit.stir.MapListener;
import org.pursuit.stir.R;
import org.pursuit.stir.models.FourSquareVenuePhoto;
import org.pursuit.stir.models.FoursquareJSON;
import org.pursuit.stir.models.FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults.FoursquareVenue;
import org.pursuit.stir.network.FoursquareService;
import org.pursuit.stir.network.RetrofitSingleton;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_shop_textview)
    TextView shopName;
    @BindView(R.id.fragment_shop_itemview_image)
    ImageView shopImage;
    @BindView(R.id.fragment_shop_distance_textview)
    TextView distance;

    private MainHostListener listener;
    private FoursquareVenue venue;

    String id;
    String photoUrl;
    double latitude;
    double longitude;

    public ShopViewHolder(@NonNull View itemView, MainHostListener listener) {
        super(itemView);
        this.listener = listener;
        ButterKnife.bind(this, itemView);
    }

    public void onBind(final FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults foursquareResults) {
//        FoursquareVenue venue = pair.first;
//        FourSquareVenuePhoto photo = pair.second;

//        String photoUrlString = photo.getResponse().getPhotos().getItems().get(0).getPrefix() + "100x100" +
//                photo.getResponse().getPhotos().getItems().get(0).getSuffix();
//        photoCall(foursquareResults.getVenue().getId());

        shopName.setText(foursquareResults.getVenue().getName());
        Log.d("evelyn", "onBind: " + foursquareResults.getVenue().getName());
//        distance.setText(foursquareResults.getVenue().getLocation().getDistance());

        Picasso.get()
                .load(photoUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(shopImage);

        venue = foursquareResults.getVenue();
        Log.d("evelyn", "onBind: " + venue.getLocation().getAddress());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: pass model to maps fragment through interface
//                listener.moveToMap(venue);
            }
        });
    }

    public void photoCall(String photoId) {
        RetrofitSingleton.getInstance()
                .create(FoursquareService.class)
                .getCoffeeVenuePhoto(photoId,
                        BuildConfig.FoursquareClientID,
                        BuildConfig.FoursquareClientSecret)
                .enqueue(new Callback<FourSquareVenuePhoto>() {
                    @Override
                    public void onResponse(Call<FourSquareVenuePhoto> call, Response<FourSquareVenuePhoto> response) {
                        Log.d("evelyn", "onResponse: " + response.body().getResponse().getPhotos().getItems().get(0).getPrefix());
                        photoUrl = response.body().getResponse().getPhotos().getItems().get(0).getPrefix() +
                                "100x100" +
                                response.body().getResponse().getPhotos().getItems().get(0).getSuffix();
                    }

                    @Override
                    public void onFailure(Call<FourSquareVenuePhoto> call, Throwable t) {

                    }
                });
    }

}
