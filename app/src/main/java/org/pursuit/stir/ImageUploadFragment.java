package org.pursuit.stir;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.pursuit.stir.models.FoursquareJSON;
import org.pursuit.stir.models.ImageUpload;
import org.pursuit.stir.shoprv.ShopSearchAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.disposables.CompositeDisposable;

public class ImageUploadFragment extends Fragment implements SearchView.OnQueryTextListener {

    private static final String ARG_PARAM1 = "param1";
    private static final int SELECT_IMAGE_REQUEST = 1;
    private static final int PERMISSION_ACCESS_FINE_LOCATION = 1;

    private String mParam1;
    private Button selectImageButton;
    private Button uploadImageButton;
    private EditText imageNameEditText;
    private ImageView userImageImageView;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ShopSearchAdapter adapter;
    private List<FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults> foursquareResponseList = new ArrayList<>();
    private SearchView searchView;


    private Uri imageUri;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private CompositeDisposable disposable = new CompositeDisposable();
    private FourSquareRepository fourSquareRepository = new FourSquareRepository();

    private StorageTask uploadStorageTask;
    private MainHostListener mainHostListener;

    public ImageUploadFragment() {
        // Required empty public constructor
    }

    public static ImageUploadFragment newInstance() {
        ImageUploadFragment fragment = new ImageUploadFragment();
        Bundle args = new Bundle();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (ContextCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION);
        }
        selectImageButton = view.findViewById(R.id.select_image_button);
        uploadImageButton = view.findViewById(R.id.upload_button);
        imageNameEditText = view.findViewById(R.id.image_name_editText);
        userImageImageView = view.findViewById(R.id.user_image_imageView);
//        progressBar = view.findViewById(R.id.progress_bar);

        storageReference = FirebaseStorage.getInstance().getReference("imageUploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("imageUploads");

        onClickListener();

        recyclerView = view.findViewById(R.id.search_recyclerView);
        recyclerView.setHasFixedSize(true);
        getSearchCoffeeCall(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_upload, container, false);
    }

    private void onClickListener() {
        selectImageButton.setOnClickListener(v -> openImageSelector());

        uploadImageButton.setOnClickListener(v -> {
            if (uploadStorageTask != null && uploadStorageTask.isInProgress()) {
                Toast.makeText(getContext(), "Image upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE_REQUEST && resultCode == -1 && data != null && data.getData() != null) {
            imageUri = data.getData();

            Picasso.get().load(imageUri).into(userImageImageView);
        }
    }

    private void openImageSelector() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_IMAGE_REQUEST);
    }

    private String getImageFIleExtension(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        if (imageUri != null) {
            //below creates unique names for the file, so we dont override them; below is using time in milliseconds to name each image (creates it a a big #.jpg) and saves it in the FireBase Storage
            StorageReference imageFieReference = storageReference.child(System.currentTimeMillis() + "." + getImageFIleExtension(imageUri));

            uploadStorageTask = imageFieReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        //below delays the progress bar for about 5 seconds so that the user can see the progress bar or else it could download so fast and the user doesn't see anything
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    progressBar.setProgress(0);
//                                }
//                            }, 500);

                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful()) ;
                        Uri downloadUrl = urlTask.getResult();

                        Toast.makeText(getContext(), "Image Upload Successful", Toast.LENGTH_SHORT).show();

                            String imageName = imageNameEditText.getText().toString().trim();
                            ImageUpload imageUpload = new ImageUpload(imageName, downloadUrl.toString(), 0);
                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(imageUpload);
                            openHomeFragment();
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT);
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //   double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            //     progressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openHomeFragment() {
        mainHostListener.replaceWithHomeFragment();
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
                                                Log.d("ImageUploadFragment", "getRetrofitCall: " + foursquareResponseList.size());
                                                for (int i = 0; i < foursquareResponseList.size(); i++) {
                                                    Log.d("ImageUploadFragment", " Shop : " + foursquareResponseList.get(i).getVenue().getName());
                                                }
                                                adapter = new ShopSearchAdapter(foursquareResponseList);
                                                recyclerView.setAdapter(adapter);
                                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                            }, throwable -> {
                                                Log.d("ImageUploadFragment", "failed: " + throwable.getLocalizedMessage());
                                            }
                                    ));
                        } else {
                            Toast.makeText(getContext(), "There was an error with this request", Toast.LENGTH_SHORT).show();
                            Log.d("evelyn", "onConnected: error with request");
                            //error state call
//                            mainHostListener.startErrorActivity();
                        }
                    });
        }
        searchView = view.findViewById(R.id.image_upload_coffee_shop_searchView);
        searchView.setOnQueryTextListener(this);
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        setSearchViewSetQuery(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        List<FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults> newList = new ArrayList<>();
        for (FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults results : foursquareResponseList) {
            if (results.getVenue().getName().toLowerCase(Locale.getDefault()).contains(s.toLowerCase(Locale.getDefault())))
                newList.add(results);
        }
        adapter.setData(newList);
        return false;
    }

    public void setSearchViewSetQuery(String s){
//        for (int i = 0; i < foursquareResponseList.size(); i++) {
        searchView.setQuery(s, false);
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainHostListener = null;
    }


    public void searchViewMethod() {
    }

  
}

