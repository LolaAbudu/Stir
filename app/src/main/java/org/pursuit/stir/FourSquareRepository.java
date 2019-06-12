package org.pursuit.stir;

import android.util.Log;
import android.util.Pair;

import org.pursuit.stir.models.FourSquareVenuePhoto;
import org.pursuit.stir.models.FoursquareJSON;
import org.pursuit.stir.models.FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults;
import org.pursuit.stir.network.FoursquareService;
import org.pursuit.stir.network.RetrofitSingleton;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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


    public Observable<FoursquareJSON> getCoffeeShop(Double latitude,
                                                    Double longitude,
                                                    Float userLocationAccuracy) {
        String latLong = latitude + "," + longitude;
        FoursquareService foursquareService = RetrofitSingleton.getInstance()
                .create(FoursquareService.class);

       return foursquareService.searchCoffee(foursquareClientID, foursquareClientSecret, latLong, userLocationAccuracy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

