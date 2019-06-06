package org.pursuit.stir.shoprv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pursuit.stir.R;
import org.pursuit.stir.models.FoursquareJSON;

import java.util.List;

public class ShopSearchAdapter extends RecyclerView.Adapter<ShopSearchViewHolder> {

    private List<FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults> foursquareResponseList;

    public ShopSearchAdapter(List<FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults> foursquareResponseList) {
        this.foursquareResponseList = foursquareResponseList;
    }

    @NonNull
    @Override
    public ShopSearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.shop_search_item_view, viewGroup, false);
        return new ShopSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopSearchViewHolder shopSearchViewHolder, int i) {
        shopSearchViewHolder.onBind(foursquareResponseList.get(i));
    }

    @Override
    public int getItemCount() {
        return foursquareResponseList.size();
    }

    public void setData(List<FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults> newFourList) {
        this.foursquareResponseList = newFourList;
        notifyDataSetChanged();
    }
}
