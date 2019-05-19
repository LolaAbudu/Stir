package org.pursuit.stir.models;

import java.util.List;

public class FourSquareVenuePhoto {

    private FourSquareResponse response;

    public FourSquareResponse getResponse() {
        return response;
    }

    public class FourSquareResponse {

        private FourSquarePhotoResponse photos;

        public FourSquarePhotoResponse getPhotos() {
            return photos;
        }

        public class FourSquarePhotoResponse {

            private List<PhotoUserAccount> items;

            public List<PhotoUserAccount> getItems() {
                return items;
            }

            public class PhotoUserAccount {

                private String prefix;
                private String suffix;

                public String getPrefix() {
                    return prefix;
                }

                public String getSuffix() {
                    return suffix;
                }
            }
        }
    }
}
