package org.pursuit.stir.homerv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.pursuit.stir.R;
import org.pursuit.stir.models.ImageUpload;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private Context context;
    private List<ImageUpload> imageList;

    public HomeAdapter(Context context, List<ImageUpload> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder homeViewHolder, int position) {
        homeViewHolder.onBind(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
