package org.pursuit.stir;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import org.pursuit.stir.models.ImageUpload;
import org.pursuit.stir.models.ProfileImage;
import org.pursuit.stir.models.User;

import java.util.ArrayList;


public class ProfilePhotoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private String groupOneAnswer;
    private String groupTwoAnswer;
    private String imageName;
    private String imageUrl;
    private String photoImageUrl;

    private Button selectPhotoButton;
    private ImageView profilePhotoImage;
    private Button savePhotoButton;
    private String mParam1;
    private String mParam2;
    private StorageTask uploadStorageTask;
    private Uri imageUri;
    private FirebaseAuth mAuth;
    private MainHostListener mainHostListener;
    private static final int SELECT_IMAGE_REQUEST = 1;



    private OnFragmentInteractionListener mListener;

    public ProfilePhotoFragment() {
        // Required empty public constructor
    }


    public static ProfilePhotoFragment newInstance() {
        ProfilePhotoFragment fragment = new ProfilePhotoFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profilePhotoImage = view.findViewById(R.id.profile_edit_photo_fragment_profile_image);
        selectPhotoButton = view.findViewById(R.id.profile_edit_select_photo_button);
        savePhotoButton = view.findViewById(R.id.profile_edit_save_button);

        storageReference = FirebaseStorage.getInstance().getReference("profilePhoto");
        databaseReference = FirebaseDatabase.getInstance().getReference("profilePhoto");
        onClickListener();
    }

    private void onClickListener() {
        selectPhotoButton.setOnClickListener(v -> openImageSelector());

        savePhotoButton.setOnClickListener(v -> {
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
            Picasso.get().load(imageUri).into(profilePhotoImage);
        } else {
            Toast.makeText(getContext(), "NO PHOTO UPLOADED", Toast.LENGTH_SHORT).show();
        }
    }


    private void openImageSelector() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
          startActivityForResult(intent, SELECT_IMAGE_REQUEST);
    }


    private void uploadImage() {
        if (imageUri != null) {
            //below creates unique names for the file, so we dont override them; below is using time in milliseconds to name each image (creates it a a big #.jpg) and saves it in the FireBase Storage
            StorageReference imageFieReference = storageReference.child(System.currentTimeMillis() + "." + getImageFIleExtension(imageUri));

            uploadStorageTask = imageFieReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {

                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        taskSnapshot.getUploadSessionUri();

                        while (!urlTask.isSuccessful()) ;
                        Uri downloadUrl = urlTask.getResult();

                        ProfileImage profileImage = new ProfileImage(downloadUrl.toString(), mAuth.getUid());
//                        String uploadId = databaseReference.push().getKey();
                        databaseReference.child(mAuth.getUid()).setValue(downloadUrl.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                openProfileFragment();
                            }
                        });

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

    private void openProfileFragment() {
            mainHostListener.replaceWithProfileFragment();
    }
    private String getImageFIleExtension(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
