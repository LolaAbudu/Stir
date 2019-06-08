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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.pursuit.stir.models.Chat;
import org.pursuit.stir.models.User;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;

public class DetailFragment extends Fragment {

    private MainHostListener mainHostListener;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference userDBReference;
    private DatabaseReference databaseReference;

    private static final String IMAGE_NAME_KEY = "name";
    private static final String IMAGE_URL_KEY = "url";
    private static final String IMAGE_USER_ID_KEY = "userID";
    private static final String IMAGE_BEAN_COUNT_KEY = "bean";

    private String imageName;
    private String imageUrl;
    private String userID;
    private String chatKey;
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
    Button chatButton;


    public DetailFragment() {
    }

    public static DetailFragment newInstance(String imageName, String imageUrl, String userID) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_NAME_KEY, imageName);
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
                        break;
                    }
                    String mychatID = firebaseAuth.getCurrentUser().getUid().substring(22);
                    String otherChatID = user.getUsrID().substring(22);
                    obtainChatKey(postSnapShot, mychatID, otherChatID);


                    chatButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    databaseReference.child("chat").child("chatkey");

                    for (DataSnapshot postDataChat : dataSnapshot.getChildren()) {
                        Log.d(DetailFragment.class.getName(), "onDataChange " + postSnapShot.getValue());

                        Chat chat = postDataChat.getValue(Chat.class);

                        Log.d(TAG, "onDataChange: " + mychatID);
                        Log.d(TAG, "onDataChange: " + otherChatID);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d(TAG, "onDataChange: data changing works");

                    databaseReference.child("beanCount").setValue(beanCount);
//                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
//                        Bean bean = postSnapShot.getValue(Bean.class);
//
//                        if (bean != null) {
//                            beanCountTextView.setText(bean.getBeanCount());
//                        }
//                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        });
        return beanCount;
    }

    public String checkChatKey(String myChatId, String otherChatId){
        if (myChatId.charAt(0) > otherChatId.charAt(0)) {
            chatKey = myChatId + "_" + otherChatId;
        } else {
            chatKey = otherChatId + "_" + myChatId;
        }
        return chatKey;
    }

    private void obtainChatKey(DataSnapshot dataSnapshot, String myChatId, String otherChatId) {
        chatKey = "";
       DatabaseReference fbChatKey =  databaseReference.child("chat").child(chatKey);

//        for (DataSnapshot postDataChat : dataSnapshot.getChildren()) {
//            Chat chat = postDataChat.getValue(Chat.class);
            if (checkChatKey(myChatId, otherChatId).equals(fbChatKey)){
                //PASS IT ALONG to chat
            } else {
                //CREATE IT, THEn go to chat
            }
//
//
//            if (myChatId.charAt(0) > otherChatId.charAt(0)) {
//                chatKey = myChatId + "_" + otherChatId;
//            } else {
//                chatKey = otherChatId + "_" + myChatId;
//            }

    }



}



