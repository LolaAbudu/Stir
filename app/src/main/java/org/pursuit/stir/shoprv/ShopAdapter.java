package org.pursuit.stir.shoprv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pursuit.stir.BuildConfig;
import org.pursuit.stir.MainHostListener;
import org.pursuit.stir.MapListener;
import org.pursuit.stir.R;
import org.pursuit.stir.models.FourSquareVenuePhoto;
import org.pursuit.stir.models.FoursquareJSON;
import org.pursuit.stir.models.FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults;
import org.pursuit.stir.models.FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults.FoursquareVenue;
import org.pursuit.stir.network.FoursquareService;
import org.pursuit.stir.network.RetrofitSingleton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopAdapter extends RecyclerView.Adapter<ShopViewHolder> {

    private List<FoursquareResults> resultsList;
    private MainHostListener listener;

    public ShopAdapter(List<FoursquareResults> resultsList, MainHostListener listener) {
        this.resultsList = resultsList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_shop_item_view, viewGroup, false);
        return new ShopViewHolder(view, listener);
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
