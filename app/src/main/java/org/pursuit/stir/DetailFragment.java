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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;

public class DetailFragment extends Fragment {

    private MainHostListener mainHostListener;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReferenceBean;

    private static final String IMAGE_NAME_KEY = "name";
    private static final String IMAGE_URL_KEY = "url";
    private static final String IMAGE_BEAN_COUNT_KEY = "bean";

    private String imageName;
    private String imageUrl;
    private int beanCount = 0;

    @BindView(R.id.detail_drink_name_textView)
    TextView imageNameTextView;
    @BindView(R.id.detail_user_photo)
    ImageView imageURLImageView;
    @BindView(R.id.detail_coffee_bean_image1)
    ImageView coffeeBean;
    @BindView(R.id.detail_coffee_count1)
    TextView beanCountTextView;


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
        imageNameTextView.setText(imageName);
        Picasso.get().load(imageUrl).into(imageURLImageView);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReferenceBean = FirebaseDatabase.getInstance().getReference("likes").child(imageName);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("imageURL", imageUrl);
        hashMap.put("beanCount", String.valueOf(onBeanClick()));
        databaseReferenceBean.setValue(hashMap);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainHostListener = null;
    }

    public int onBeanClick() {
        coffeeBean.setOnClickListener(v -> {
            Log.d(TAG, "onBeanClick: click is working");
            beanCount = beanCount + 1;
            beanCountTextView.setText(String.valueOf(beanCount));

            databaseReferenceBean.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d(TAG, "onDataChange: data changing works");
                    databaseReferenceBean.child("beanCount").setValue(beanCount);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        });
        return beanCount;
    }

//    private void onBeanClicked(DatabaseReference postRef) {
//        postRef.runTransaction(new Transaction.Handler() {
//            @Override
//            public Transaction.Result doTransaction(MutableData mutableData) {
//                ImageUpload bean = mutableData.getValue(ImageUpload.class);
//                if (bean == null) {
//                    return Transaction.success(mutableData);
//                }
//                beanCount = bean.starCount;
//                if (bean.stars.containsKey(firebaseAuth.getUid())) {
//                    // Unstar the post and remove self from stars
//                    beanCount = bean.starCount - 1;
//                    bean.stars.remove(firebaseAuth.getUid());
//                } else {
//                    // Star the post and add self to stars
//                    beanCount = bean.starCount + 1;
//                    bean.stars.put(firebaseAuth.getUid(), true);
//                }
//
//                // Set value and report transaction success
//                beanCountTextView.setText(String.valueOf(beanCount));
//                mutableData.setValue(bean);
//                return Transaction.success(mutableData);
//            }
//
//            @Override
//            public void onComplete(DatabaseError databaseError, boolean b,
//                                   DataSnapshot dataSnapshot) {
//                // Transaction completed
//                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
//            }
//        });

}



