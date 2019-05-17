package org.pursuit.stir;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

            }
        });

        homeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void openImageSelector() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SELECT_IMAGE_REQUEST && resultCode == -1 && data != null && data.getData() != null){
            imageUri = data.getData();

            Picasso.get().load(imageUri).into(userImageImageView);
        }
    }
}
