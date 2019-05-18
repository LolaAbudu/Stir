package org.pursuit.stir.shoprv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pursuit.stir.R;
import org.pursuit.stir.models.FoursquareJSON;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopViewHolder> {

    private List<FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults> resultsList;

    public ShopAdapter(List<FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults> resultsList) {
        this.resultsList = resultsList;
    }
    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_shop_item_view, viewGroup, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder shopViewHolder, int i) {
        shopViewHolder.onBind(resultsList.get(i));
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }
}
