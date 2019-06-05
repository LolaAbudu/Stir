package org.pursuit.stir;


import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.pursuit.stir.models.FourSquareVenuePhoto;
import org.pursuit.stir.models.FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults;
import org.pursuit.stir.shoprv.ShopAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ShopAdapter adapter;
    private static final int PERMISSION_ACCESS_FINE_LOCATION = 1;

    private static final String TAG = "evelyn";

    private MainHostListener mainHostListener;

    private ProgressBar progressCircle;

    //used to keep track of Rx call
    private CompositeDisposable disposable = new CompositeDisposable();

    private FourSquareRepository fourSquareRepository = new FourSquareRepository();


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
        progressCircle = view.findViewById(R.id.shop_progress_circle);

        recyclerView = view.findViewById(R.id.shop_recyclerview);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        googleApiClient = new GoogleApiClient.Builder(view.getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        Log.d(TAG, "onViewCreated: " + googleApiClient.isConnected());


    }

    //TODO make it take the list of result and photos (PAIRS)
    public void updateUI(List<Pair<FoursquareResults, FourSquareVenuePhoto>> pairResults) {
        // Displays the results in the RecyclerView
        adapter = new ShopAdapter(pairResults, mainHostListener);
        recyclerView.setAdapter(adapter);

        progressCircle.setVisibility(View.INVISIBLE);
    }

    public void updateUIOnFailure(List<FoursquareResults> fourSquareResult) {
        Log.d("failed", "Call failed");

        progressCircle.setVisibility(View.INVISIBLE);
    }

    public List<FoursquareResults> transformPairToResult(List<Pair<FoursquareResults, FourSquareVenuePhoto>> fourSquareResult) {
        List<FoursquareResults> results = new ArrayList<>();
        for (int i = 0; i < fourSquareResult.size(); i++) {
            results.add(fourSquareResult.get(i).first);
        }
        return results;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), location -> {
                        if (location != null) {
                            disposable.add(fourSquareRepository.fourSquareResult(location.getLatitude(),
                                    location.getLongitude(), location.getAccuracy())
                                    .subscribe(this::updateUI
                                            , this::showError));
                        } else {
                            Toast.makeText(getContext(), "There was an error with this request", Toast.LENGTH_SHORT).show();
                            Log.d("evelyn", "onConnected: error with request");
                            //error state call
                        }
                    });
        }
    }

    private void showError(Throwable throwable) {
        progressCircle.setVisibility(View.INVISIBLE);
        Log.d(TAG, "onConnected: " + throwable.getMessage());
        Toast.makeText(getContext(), "Oops, network error. Please try again!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDestroy() {
        super.onDestroy();

        //cleas up the call result
        disposable.clear();
    }
}
//TODO implement onError for Rx Call

