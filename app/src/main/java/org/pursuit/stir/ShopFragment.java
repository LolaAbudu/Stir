package org.pursuit.stir;


import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.pursuit.stir.models.FourSquareVenuePhoto;
import org.pursuit.stir.models.FoursquareJSON;
import org.pursuit.stir.network.FoursquareService;
import org.pursuit.stir.network.RetrofitSingleton;
import org.pursuit.stir.shoprv.ShopAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ShopAdapter adapter;

    private String foursquareClientID;
    private String foursquareClientSecret;
    private List<FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults> foursquareResultsList;

    public ShopFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.shop_recyclerview);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(linearLayoutManager);

        googleApiClient = new GoogleApiClient.Builder(view.getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        foursquareClientID = BuildConfig.FoursquareClientID;
        foursquareClientSecret = BuildConfig.FoursquareClientSecret;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @SuppressLint("CheckResult")
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                String userLastLocation = location.getLatitude() + "," + location.getLongitude();
                                double userLocationAccuracy = location.getAccuracy();



                                RetrofitSingleton.getInstance()
                                        .create(FoursquareService.class)
                                        .searchCoffee(
                                                foursquareClientID,
                                                foursquareClientSecret,
                                                userLastLocation,
                                                userLocationAccuracy)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(foursquareJSON -> {
                                            FoursquareJSON.FoursquareResponse fr = foursquareJSON.getResponse();
                                            FoursquareJSON.FoursquareResponse.FoursquareGroup fg = fr.getGroup();
                                            foursquareResultsList = fg.getResults();
                                            adapter = new ShopAdapter(foursquareResultsList);
                                            recyclerView.setAdapter(adapter);
                                        }, throwable ->
                                                Toast.makeText(getContext(), "Oops, Stir can't connect to Foursquare's servers", Toast.LENGTH_SHORT).show());
                                getActivity().finish();

                                for (int i = 0; i < foursquareResultsList.size(); i++) {
                                    FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults.FoursquareVenue venue = foursquareResultsList.get(i).getVenue();
                                    final String venueId = venue.getId();
                                }

                                Observable.fromIterable(foursquareResultsList);
//                                        .flatMap();

//
//                            } else {
//                                Toast.makeText(getContext(), "Oops, Stir can't determine your current location", Toast.LENGTH_SHORT).show();
//                                getActivity().finish();
//                            }

                            }
                        }
                    });
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        googleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.disconnect();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), "Oops, Stir can't connect to Google's servers", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    public Observable<FourSquareVenuePhoto> getStringsAfterVenueIDLookup(String venue_id) {
        FoursquareService foursquareService = RetrofitSingleton.getInstance().create(FoursquareService.class);
        return foursquareService.getCoffeeVenuePhoto(venue_id, foursquareClientID, foursquareClientSecret)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
