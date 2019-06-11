package org.pursuit.stir;

import org.pursuit.stir.models.FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults.FoursquareVenue;

public interface MainHostListener {

    void replaceWithHomeFragment();

    void replaceWithProfileFragment();

    void replaceWithShopFragment();

    void replaceWithImageUploadFragment();

    void replaceWithCoffeeLoversFragment(String chatKey);

    void moveToMap(FoursquareVenue foursquareVenue);

    void moveToDetailFragment(String image, String imageURl, String userID);

    void startErrorActivity();
  
    void replaceWithCoffeePrefFragment();

    void replaceWithChatListFragment();

    void replaceWithProfilePhotoFragment();

}
