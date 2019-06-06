package org.pursuit.stir.shoprv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pursuit.stir.MainHostListener;
import org.pursuit.stir.R;
import org.pursuit.stir.models.FourSquareVenuePhoto;
import org.pursuit.stir.models.FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopViewHolder> {

    private List<Pair<FoursquareResults, FourSquareVenuePhoto>> resultsList;
    private MainHostListener listener;

    public ShopAdapter(final List<Pair<FoursquareResults, FourSquareVenuePhoto>> pairResults, final MainHostListener listener) {
        this.resultsList = pairResults;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_shop_item_view, viewGroup, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder shopViewHolder, int i) {
        shopViewHolder.onBind(resultsList.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public void setData(List<Pair<FoursquareResults, FourSquareVenuePhoto>> newPairList) {
        this.resultsList = newPairList;
        notifyDataSetChanged();
    }

}
