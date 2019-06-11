package org.pursuit.stir.profilerv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.pursuit.stir.R;
import org.pursuit.stir.homerv.HomeViewHolder;
import org.pursuit.stir.models.ImageUpload;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileViewHolder> {
    private List<ImageUpload> imageList;

    public ProfileAdapter(List<ImageUpload> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProfileViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_profile_item_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder profileViewHolder, int i) {
        profileViewHolder.onBind(imageList.get(i));

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
