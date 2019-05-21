package org.pursuit.stir;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {

    private MainHostListener mainHostListener;

    private static final String IMAGE_NAME_KEY = "name";
    private static final String IMAGE_URL_KEY = "url";

    private String imageName;
    private String imageUrl;

    private TextView imageNameTextView;
    private ImageView imageURLImageView;


    public DetailFragment() {
    }

    public static DetailFragment newInstance(String imageName, String imageUrl){
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_NAME_KEY, imageName);
        args.putString(IMAGE_URL_KEY, imageUrl);
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
        if(getArguments() != null){
            imageName = getArguments().getString(IMAGE_NAME_KEY);
            imageUrl = getArguments().getString(IMAGE_URL_KEY);

            Log.d("detailName", imageName + " lola2");
            Log.d("detailName", imageUrl + " lola2");
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

        Log.d("detailName", "lola");

        imageNameTextView = view.findViewById(R.id.detail_drink_name_textView);
        imageURLImageView = view.findViewById(R.id.detail_user_photo);

        imageNameTextView.setText(imageName);
        Picasso.get().load(imageUrl).into(imageURLImageView);

        Log.d("detailName", imageName + " lola");
        Log.d("detailName", imageUrl + " lola");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainHostListener = null;
    }
}
