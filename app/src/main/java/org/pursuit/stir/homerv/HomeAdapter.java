package org.pursuit.stir.homerv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.pursuit.stir.MainHostListener;
import org.pursuit.stir.R;
import org.pursuit.stir.models.ImageUpload;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private List<ImageUpload> imageList;
    private MainHostListener mainHostListener;

    public HomeAdapter(List<ImageUpload> imageList, MainHostListener mainHostListener) {
        this.imageList = imageList;
        this.mainHostListener = mainHostListener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder homeViewHolder, int position) {
        homeViewHolder.onBind(imageList.get(position), mainHostListener);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
