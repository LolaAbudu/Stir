package org.pursuit.stir.shoprv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.pursuit.stir.ImageUploadFragment;
import org.pursuit.stir.R;
import org.pursuit.stir.models.FoursquareJSON;

public class ShopSearchViewHolder extends RecyclerView.ViewHolder {

    private ImageUploadFragment imageUploadFragment = new ImageUploadFragment();
    private TextView searchShopView;

    public ShopSearchViewHolder(@NonNull View itemView) {
        super(itemView);
        searchShopView = itemView.findViewById(R.id.search_shop_itemView);
    }

    public void onBind(FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults foursquareResults) {
        searchShopView.setText(foursquareResults.getVenue().getName());
        searchShopView.setOnClickListener(v -> {
            imageUploadFragment.setSearchViewSetQuery(foursquareResults.getVenue().getName());
        });
    }
}
