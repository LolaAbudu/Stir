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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.pursuit.stir.models.Bean;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;

public class DetailFragment extends Fragment {

    private MainHostListener mainHostListener;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReferenceBean;
    private boolean processBean = false;

    private static final String IMAGE_NAME_KEY = "name";
    private static final String IMAGE_URL_KEY = "url";

    private String imageName;
    private String imageUrl;

    private Bean beanTest = new Bean("me", "you");

    @BindView(R.id.detail_drink_name_textView)
    TextView imageNameTextView;
    @BindView(R.id.detail_user_photo)
    ImageView imageURLImageView;
    @BindView(R.id.detail_coffee_bean_image1)
    ImageView beanLike;
    @BindView(R.id.detail_coffee_count1)
    TextView beanCount;


    public DetailFragment() {
    }

    public static DetailFragment newInstance(String imageName, String imageUrl) {
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
        if (getArguments() != null) {
            imageName = getArguments().getString(IMAGE_NAME_KEY);
            imageUrl = getArguments().getString(IMAGE_URL_KEY);
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
        firebaseAuth = FirebaseAuth.getInstance();
//        databaseReferenceBean = FirebaseDatabase.getInstance().getReference().child("bean");
        databaseReferenceBean = FirebaseDatabase.getInstance().getReference("beans");
        databaseReferenceBean.keepSynced(true);

        imageNameTextView.setText(imageName);
        Picasso.get().load(imageUrl).into(imageURLImageView);

        onBeanClick();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainHostListener = null;
    }

    public void onBeanClick() {
        beanLike.setOnClickListener(v -> {
            Log.d(TAG, "onBeanClick: click is working");
//        mainHostListener.setBeanLike();

            databaseReferenceBean = FirebaseDatabase.getInstance().getReference("bean");
            databaseReferenceBean.setValue("heello, world");

            databaseReferenceBean.child("bean").child("likes").setValue(beanTest);

//                processBean = true;
////                if (processBean) {
//            databaseReferenceBean.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Log.d(TAG, "onDataChange: data changing works");
//
//                    Bean bean = new Bean();
//                    String uploadId = databaseReferenceBean.push().getKey();
//                    databaseReferenceBean.child(uploadId).setValue(bean);


////
//                    if (dataSnapshot.child("likes").hasChild(firebaseAuth.getCurrentUser().getUid())) {
//                    } else {
////                                Bean bean = new Bean(imageName, username);
//                        databaseReferenceBean.child("likes").child(firebaseAuth.getCurrentUser().getUid()).setValue("username");
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//
//            });
        });
    }

   

}
