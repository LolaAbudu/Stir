package org.pursuit.stir;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoffeePrefFragment extends Fragment {

    @BindView(R.id.coff_pref_continue_button)
    Button continueButton;
    @BindView(R.id.radio_group_one)
    RadioGroup groupOne;
    @BindView(R.id.radio_group_two)
    RadioGroup groupTwo;
    @BindView(R.id.radio_group_three)
    RadioGroup groupThree;
    @BindView(R.id.radio_group_four)
    RadioGroup groupFour;

    public CoffeePrefFragment() {
    }

    public static CoffeePrefFragment newInstance() {
        CoffeePrefFragment fragment = new CoffeePrefFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coffee_pref, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainHostActivity.class);
                startActivity(intent);
            }
        });
    }
}
