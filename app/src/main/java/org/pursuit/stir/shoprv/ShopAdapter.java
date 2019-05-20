package org.pursuit.stir.shoprv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pursuit.stir.MainHostListener;
import org.pursuit.stir.R;
import org.pursuit.stir.models.FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopViewHolder> {

    private List<FoursquareResults> resultsList;
    private MainHostListener listener;

    public ShopAdapter(final List<FoursquareResults> resultsList, final MainHostListener listener) {
        this.resultsList = resultsList;
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

}
