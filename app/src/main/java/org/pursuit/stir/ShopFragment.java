package org.pursuit.stir;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.pursuit.stir.models.FoursquareJSON;
import org.pursuit.stir.models.FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults;
import org.pursuit.stir.models.FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults.FoursquareVenue;
import org.pursuit.stir.network.FoursquareService;
import org.pursuit.stir.network.RetrofitSingleton;
import org.pursuit.stir.shoprv.ShopAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ShopAdapter adapter;
    private static final int PERMISSION_ACCESS_FINE_LOCATION = 1;


    private String foursquareClientID;
    private String foursquareClientSecret;
    private List<FoursquareResults> foursquareResultsList;
    private static final String TAG = "evelyn";

    private MainHostListener mainHostListener;

    public ShopFragment() {
        // Required empty public constructor
    }

    public static ShopFragment newInstance() {
        return new ShopFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainHostListener) {
            mainHostListener = (MainHostListener) context;
        }
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
        if (ContextCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION);
        }
        recyclerView = view.findViewById(R.id.shop_recyclerview);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        googleApiClient = new GoogleApiClient.Builder(view.getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        Log.d(TAG, "onViewCreated: " + googleApiClient.isConnected());

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

                                FoursquareService foursquareService = RetrofitSingleton.getInstance()
                                        .create(FoursquareService.class);

                                Call<FoursquareJSON> coffeeCall = foursquareService.searchCoffee(
                                        foursquareClientID,
                                        foursquareClientSecret,
                                        userLastLocation,
                                        userLocationAccuracy);
                                coffeeCall.enqueue(new Callback<FoursquareJSON>() {
                                    @Override
                                    public void onResponse(Call<FoursquareJSON> call, Response<FoursquareJSON> response) {

                                        // Gets the venue object from the JSON response
                                        FoursquareJSON fjson = response.body();
                                        FoursquareJSON.FoursquareResponse fr = fjson.getResponse();
                                        FoursquareJSON.FoursquareResponse.FoursquareGroup fg = fr.getGroup();
                                        List<FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults> frs = fg.getResults();

                                        // Displays the results in the RecyclerView
                                        adapter = new ShopAdapter(frs, mainHostListener);
                                        recyclerView.setAdapter(adapter);
                                    }

                                    @Override
                                    public void onFailure(Call<FoursquareJSON> call, Throwable t) {
                                        Log.d(TAG, "onFailure: " + t.toString());
                                    }
                                });


//                                foursquareService
//                                        .searchCoffee(
//                                                foursquareClientID,
//                                                foursquareClientSecret,
//                                                userLastLocation,
//                                                userLocationAccuracy)
//                                        .flatMapIterable(foursquareJSON -> foursquareJSON.getResponse().getGroup().getResults())
//                                        .flatMap(result -> foursquareService.getCoffeeVenuePhoto(result.getVenue().getId(), foursquareClientID, foursquareClientSecret),
//                                                (results, photo) -> {
//                                                    Log.d(TAG, "onSuccess: " + results.getVenue().getName() + photo.getResponse().getPhotos().getItems().get(0).getSuffix());
//                                                    return new Pair(results, photo);
//                                                }
//                                        )
//                                        .toList()
//                                        .subscribeOn(Schedulers.io())
//                                        .observeOn(AndroidSchedulers.mainThread())
//                                        .subscribe(new Consumer<List<Pair>>() {
//                                            @Override
//                                            public void accept(List<Pair> pairList) throws Exception {
//                                                adapter = new ShopAdapter(pairList);
//                                                recyclerView.setAdapter(adapter);
//                                            }
//                                        }, new Consumer<Throwable>() {
//                                            @Override
//                                            public void accept(Throwable throwable) throws Exception {
//                                                Toast.makeText(getContext(), "Oops, Stir can't connect to Foursquare's servers", Toast.LENGTH_SHORT).show();
//                                                Log.d(TAG, "accept: " + throwable.toString());
//                                            }
//                                        });
////                                getActivity().finish();
//
//                            } else {
//                                Toast.makeText(getContext(), "Oops, Stir can't determine your current location", Toast.LENGTH_SHORT).show();
////                                getActivity().finish();
//                            }
//                        }
//                    });
                            } else {
                                Toast.makeText(getContext(), "There was an error with this request", Toast.LENGTH_SHORT).show();
                                Log.d("evelyn", "onConnected: error with request");
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

    @Override
    public void onDetach() {
        super.onDetach();
        mainHostListener = null;
    }
}
