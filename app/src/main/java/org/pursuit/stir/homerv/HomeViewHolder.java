package org.pursuit.stir.homerv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.pursuit.stir.HomeListener;
import org.pursuit.stir.R;
import org.pursuit.stir.models.ImageUpload;

public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
        View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

    private ImageView imageview;

    private HomeListener homeListener;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);

        imageview = itemView.findViewById(R.id.home_itemview_image_view);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void onBind(ImageUpload imageUpload) {

        Picasso.get()
                .load(imageUpload.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imageview);
    }

    public void setOnItemClickListener(HomeListener listener){
        this.homeListener = listener;
    }

    @Override
    public void onClick(View v) {
        if(homeListener != null){
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                homeListener.onItemClick(position);
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem delete = menu.add(Menu.NONE, 1, 1, "Delete");

        delete.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(homeListener != null){
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                //use incase we want to add more options on long press besides just the delete option
                switch (item.getItemId()){
                    case 1:
                        homeListener.onDeleteClick(position);
                        return true;
                }
            }
        }
        return false;
    }
}
