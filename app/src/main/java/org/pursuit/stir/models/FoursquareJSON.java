package org.pursuit.stir.models;

import java.util.ArrayList;
import java.util.List;

public class FoursquareJSON {

    // A response object within the JSON.
    FoursquareResponse response;

    public class FoursquareResponse {

        // A group object within the response.
        FoursquareGroup group;

        public FoursquareGroup getGroup() {
            return group;
        }

        public class FoursquareGroup {

            // A results list within the group.
            List<FoursquareResults> results = new ArrayList<>();

            public List<FoursquareResults> getResults() {
                return results;
            }

            public class FoursquareResults {

                // A venue object within the results.
                FoursquareVenue venue;

                public FoursquareVenue getVenue() {
                    return venue;
                }

                public class FoursquareVenue {

                    // The ID of the venue.
                    String id;

                    // The name of the venue.
                    String name;

                    FourSquarePhoto photos;

                    // A location object within the venue.
                    FoursquareLocation location;

                    public String getId() {
                        return id;
                    }

                    public class FoursquareLocation {

                        // The address of the location.
                        String address;

                        // The latitude of the location.
                        double lat;

                        // The longitude of the location.
                        double lng;

                        // The distance of the location, calculated from the specified location.
                        int distance;

                    }

                    private class FourSquarePhoto {
                    }
                }

            }

        }

    }

}
