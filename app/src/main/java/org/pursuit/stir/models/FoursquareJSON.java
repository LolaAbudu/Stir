package org.pursuit.stir.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FoursquareJSON implements Serializable {

    // A response object within the JSON.
    FoursquareResponse response;

    public FoursquareResponse getResponse() {
        return response;
    }

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

                public class FoursquareVenue implements Serializable {

                    // The ID of the venue.
                    String id;

                    // The name of the venue.
                    String name;

                    // A location object within the venue.
                    FoursquareLocation location;

                    public String getId() {
                        return id;
                    }

                    public String getName() {
                        return name;
                    }

                    public FoursquareLocation getLocation() {
                        return location;
                    }

                    public class FoursquareLocation {

                        // The address of the location.
                        private String address;

                        // The latitude of the location.
                        private double lat;

                        // The longitude of the location.
                        private double lng;

                        // The distance of the location, calculated from the specified location.
                        private int distance;

                        public String getAddress() {
                            return address;
                        }

                        public double getLat() {
                            return lat;
                        }

                        public double getLng() {
                            return lng;
                        }

                        public int getDistance() {
                            return distance;
                        }
                    }
                }

            }

        }

    }

}
