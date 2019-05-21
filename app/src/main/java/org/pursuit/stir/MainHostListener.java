package org.pursuit.stir;

import org.pursuit.stir.models.FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults.FoursquareVenue;

public interface MainHostListener {

    void replaceWithHomeFragment();

    void replaceWithProfileFragment();

    void replaceWithShopFragment();

    void replaceWithImageUploadFragment();

    void moveToMap(FoursquareVenue foursquareVenue);

    void moveToDetailFragment(String imageName, String imageUrl);
}
