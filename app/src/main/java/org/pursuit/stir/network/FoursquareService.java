package org.pursuit.stir.network;

import org.pursuit.stir.models.FourSquareVenuePhoto;
import org.pursuit.stir.models.FoursquareJSON;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FoursquareService {

    // A request to search for nearby coffee shop recommendations via the Foursquare API.
    @GET("search/recommendations?v=20190515&intent=coffee&limit=10")
    Observable<FoursquareJSON> searchCoffee(@Query("client_id") String clientID,
                                                         @Query("client_secret") String clientSecret,
                                                         @Query("ll") String ll,
                                                         @Query("llAcc") double llAcc);

    // A request for an image of a FourSquareVenue by its ID
    @GET("venues/{venue_id}/photos?&v=20190515&group=venue")
    Observable<FourSquareVenuePhoto> getCoffeeVenuePhoto(@Path("venue_id") String venue_id,
                                                     @Query("client_id") String clientID,
                                                     @Query("client_secret") String clientSecret);
}
