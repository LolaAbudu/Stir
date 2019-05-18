package org.pursuit.stir;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.pursuit.stir.models.ImageUpload;

public class ImageUploadFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private static final int SELECT_IMAGE_REQUEST = 1;

    private Button selectImageButton;
    private Button uploadImageButton;
    private TextView homeTextView;
    private EditText imageNameEditText;
    private ImageView userImageImageView;

    private ProgressBar progressBar;
    private Uri imageUri;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private StorageTask uploadStorageTask;

    private HomeListener homeListener;


    public ImageUploadFragment() {
        // Required empty public constructor
    }

    public static ImageUploadFragment newInstance() {
        ImageUploadFragment fragment = new ImageUploadFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignUpListener) {
            homeListener = (HomeListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectImageButton = view.findViewById(R.id.select_image_button);
        uploadImageButton = view.findViewById(R.id.upload_button);
        homeTextView = view.findViewById(R.id.home_textView);
        imageNameEditText = view.findViewById(R.id.image_name_editText);
        userImageImageView = view.findViewById(R.id.user_image_imageView);
        progressBar = view.findViewById(R.id.progress_bar);

        storageReference = FirebaseStorage.getInstance().getReference("imageUploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("imageUploads");

        onClickListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_upload, container, false);
    }

    private void onClickListener() {
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageSelector();
            }
        });

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadStorageTask != null && uploadStorageTask.isInProgress()){
                    Toast.makeText(getContext(), "Image upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadImage();
                }
            }
        });

        homeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeFragment();
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
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //below delays the progress bar for about 5 seconds so that the user can see the progress bar or else it could download so fast and the user doesn't see anything
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(getContext(), "Image Upload Successful", Toast.LENGTH_SHORT).show();
                            ImageUpload imageUpload = new ImageUpload(imageNameEditText.getText().toString().trim(), taskSnapshot.getUploadSessionUri().toString());
                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(imageUpload);
                        }
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
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openHomeFragment() {
        homeListener.replaceWithHomeFragment();
    }
}
