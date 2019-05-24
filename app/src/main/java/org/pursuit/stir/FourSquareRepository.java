package org.pursuit.stir;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.pursuit.stir.models.FourSquareVenuePhoto;
import org.pursuit.stir.models.FoursquareJSON;
import org.pursuit.stir.models.FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults;
import org.pursuit.stir.network.FoursquareService;
import org.pursuit.stir.network.RetrofitSingleton;
import org.pursuit.stir.shoprv.ShopAdapter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FourSquareRepository {


    private String foursquareClientID = BuildConfig.FoursquareClientID;
    private String foursquareClientSecret = BuildConfig.FoursquareClientSecret;
    private List<FoursquareResults> foursquareResultsList;

    private static final String TAG = "evelyn";

    public Single<List<Pair<FoursquareResults, FourSquareVenuePhoto>>> fourSquareResult(Double latitude,
                                                                                        Double longitude,
                                                                                        Float userLocationAccuracy) {
        String latLong = latitude + "," + longitude;

        FoursquareService foursquareService = RetrofitSingleton.getInstance()
                .create(FoursquareService.class);
        return
                foursquareService
                        .searchCoffee(
                                foursquareClientID,
                                foursquareClientSecret,
                                latLong,
                                userLocationAccuracy)
                        .flatMapIterable(foursquareJSON -> foursquareJSON.getResponse().getGroup().getResults())
                        .flatMap(result -> foursquareService.getCoffeeVenuePhoto(result.getVenue().getId(),
                                foursquareClientID, foursquareClientSecret),
                                (results, photo) -> {
                                    Log.d(TAG, "onSuccess: " + results.getVenue().getName() +
                                            photo.getResponse().getPhotos().getItems().get(0).getSuffix());
                                    return new Pair<>(results, photo);
                                }
                        )
                        .toList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }
}

//a class that uses another class is a caller
//TODO check if we have the call in memory, give it to me, else, make the call
