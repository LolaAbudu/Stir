package org.pursuit.stir.shoprv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.pursuit.stir.R;
import org.pursuit.stir.models.FoursquareJSON;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_shop_textview)
    TextView shopName;
    @BindView(R.id.fragment_shop_itemview_image)
    ImageView shopImage;
    @BindView(R.id.fragment_shop_distance_textview)
    TextView distance;

    String id;
    double latitude;
    double longitude;

    public ShopViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(final FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults results) {
        shopName.setText(results.getVenue().getName());
        distance.setText(results.getVenue().getLocation().getDistance());
        //TODO: Implement photo call here or in shop fragment?
        Picasso.get()
                .load(R.mipmap.ic_launcher)
                .into(shopImage);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: pass model to maps activity through interface
            }
        });
    }

}
