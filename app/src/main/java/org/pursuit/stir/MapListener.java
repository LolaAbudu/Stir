package org.pursuit.stir;

import org.pursuit.stir.models.FoursquareJSON;
import org.pursuit.stir.models.FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults.FoursquareVenue;

public interface MapListener {

    void moveToMap(FoursquareVenue foursquareVenue);
}
