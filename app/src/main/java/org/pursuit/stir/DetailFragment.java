package org.pursuit.stir;


import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.pursuit.stir.models.FoursquareJSON;
import org.pursuit.stir.models.User;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class DetailFragment extends Fragment {

    private MainHostListener mainHostListener;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference userDBReference;
    private DatabaseReference databaseReference;

    private static final String IMAGE_NAME_KEY = "name";
    private static final String SHOP_NAME_KEY = "shop";
    private static final String IMAGE_URL_KEY = "url";
    private static final String IMAGE_USER_ID_KEY = "userID";
    private static final String IMAGE_BEAN_COUNT_KEY = "bean";


    private CompositeDisposable disposable = new CompositeDisposable();
    private FourSquareRepository fourSquareRepository = new FourSquareRepository();
    private List<FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults> foursquareResponseList;

    private String imageName;
    private String shopName;
    private String imageUrl;
    private String userID;
    private String chatKey;
    private String mychatID;
    private String otherChatID;
    private int beanCount = 0;
    private User user;

    @BindView(R.id.detail_drink_name_textView)
    TextView imageNameTextView;
    @BindView(R.id.detail_username_textView)
    TextView userNameTextView;
    @BindView(R.id.detail_user_photo)
    ImageView imageURLImageView;
    @BindView(R.id.detail_coffee_bean_image1)
    ImageView coffeeBean;
    @BindView(R.id.detail_coffee_count1)
    TextView beanCountTextView;
    @BindView(R.id.chat_with_me_button)
    TextView chatButton;
    @BindView(R.id.detail_coffee_shop_name)
    TextView shopNameView;
    @BindView(R.id.detail_coffee_shop_address)
    TextView shopAddressView;


    public DetailFragment() {
    }

    public static DetailFragment newInstance(String imageName, String shopName, String imageUrl, String userID) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_NAME_KEY, imageName);
        args.putString(SHOP_NAME_KEY, shopName);
        args.putString(IMAGE_URL_KEY, imageUrl);
        args.putString(IMAGE_USER_ID_KEY, userID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainHostListener) {
            mainHostListener = (MainHostListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageName = getArguments().getString(IMAGE_NAME_KEY);
            shopName = getArguments().getString(SHOP_NAME_KEY);
            imageUrl = getArguments().getString(IMAGE_URL_KEY);
            userID = getArguments().getString(IMAGE_USER_ID_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        imageNameTextView.setText(imageName);
        Picasso.get().load(imageUrl).into(imageURLImageView);
        shopNameView.setText(shopName);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("likes").child(imageName);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("imageURL", imageUrl);
        hashMap.put("beanCount", String.valueOf(onBeanClick()));
        databaseReference.setValue(hashMap);


        userDBReference = FirebaseDatabase.getInstance().getReference("users");
        userDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Log.d(DetailFragment.class.getName(), "onDataChange " + postSnapShot.getValue());

                    User user = postSnapShot.getValue(User.class);
                    if (userID.equals(user.getUsrID())) {
                        userNameTextView.setText(user.getUsername());
                        mychatID = firebaseAuth.getCurrentUser().getUid().substring(22);
                        otherChatID = user.getUsrID().substring(22);
                        break;
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mychatID.equals(userID)) {
                    Snackbar snackbar = Snackbar.make(v, "Oops, you can't chat with yourself!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    mainHostListener.replaceWithCoffeeLoversFragment(checkChatKey(mychatID, otherChatID));
                }
            }
        });
        getSearchCoffeeCall(view);
    }

    public void getSearchCoffeeCall(View view) {
        if (ContextCompat.checkSelfPermission(
                getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), location -> {
                        if (location != null) {
                            disposable.add(fourSquareRepository
                                    .getCoffeeShop(location.getLatitude(), location.getLongitude(), location.getAccuracy())
                                    .subscribe(foursquareJSON -> {
                                                foursquareResponseList = foursquareJSON.getResponse().getGroup().getResults();
                                                for (int i = 0; i < foursquareResponseList.size(); i++) {

                                                    if (shopName.equals(foursquareResponseList.get(i).getVenue().getName())) {
                                                        String address = foursquareResponseList.get(i).getVenue().getLocation().getAddress() + "\n"
                                                                + foursquareResponseList.get(i).getVenue().getLocation().getCity() + ", "
                                                                + foursquareResponseList.get(i).getVenue().getLocation().getState() + ", "
                                                                + foursquareResponseList.get(i).getVenue().getLocation().getPostalCode();
                                                        shopAddressView.setText(address);
                                                    }
                                                }
                                            }, throwable -> {
                                                Log.d("ImageUploadFragment", "failed: " + throwable.getLocalizedMessage());
                                            }
                                    ));
                        } else {
                            Toast.makeText(getContext(), "There was an error with this request", Toast.LENGTH_SHORT).show();
//                            mainHostListener.startErrorActivity();
                        }
                    });
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mainHostListener = null;
    }

    public int onBeanClick() {
        coffeeBean.setOnClickListener(v -> {
            beanCount = beanCount + 1;
            beanCountTextView.setText(String.valueOf(beanCount));

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    databaseReference.child("beanCount").setValue(beanCount);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        });
        return beanCount;
    }

    public String checkChatKey(String myChatId, String otherChatId) {
        if (myChatId.charAt(0) < otherChatId.charAt(0)) {
            chatKey = myChatId + "_" + otherChatId;
        } else {
            chatKey = otherChatId + "_" + myChatId;
        }
        return chatKey;
    }
}



