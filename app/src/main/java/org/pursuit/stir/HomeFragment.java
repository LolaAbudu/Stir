package org.pursuit.stir;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.pursuit.stir.homerv.HomeAdapter;
import org.pursuit.stir.models.ImageUpload;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment{

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;

    private ProgressBar progressCircle;

    private DatabaseReference databaseReference;
    private List<ImageUpload> imageList;
    private MainHostListener mainHostListener;

    public HomeFragment() { }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainHostListener){
            mainHostListener = (MainHostListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.home_itemview_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        progressCircle = view.findViewById(R.id.home_itemview_progress_circle);

        imageList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("imageUploads");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imageList.clear();

                for(DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                    Log.d(HomeFragment.class.getName(), "onDataChange " + postSnapShot.getValue());

                    ImageUpload imageUpload = postSnapShot.getValue(ImageUpload.class);
                    imageList.add(imageUpload);
                }

                homeAdapter = new HomeAdapter(imageList, mainHostListener);
                recyclerView.setAdapter(homeAdapter);

                homeAdapter.notifyDataSetChanged();

                progressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                progressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainHostListener = null;
    }
}
