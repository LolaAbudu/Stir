package org.pursuit.stir;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CoffeeLoversFragment extends Fragment {

    private MainHostListener mainHostListener;

    public CoffeeLoversFragment() {
        // Required empty public constructor
    }

    public static CoffeeLoversFragment newInstance() {
        CoffeeLoversFragment fragment = new CoffeeLoversFragment();
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
        return inflater.inflate(R.layout.fragment_coffee_lovers, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainHostListener = null;
    }
}
