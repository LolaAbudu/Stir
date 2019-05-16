package org.pursuit.stir.models;

import java.util.List;

public class FourSquareVenuePhoto {

    FourSquareResponse response;

    public FourSquareResponse getResponse() {
        return response;
    }

    public class FourSquareResponse {

        FourSquarePhotoResponse photos;

        public FourSquarePhotoResponse getPhotos() {
            return photos;
        }

        public class FourSquarePhotoResponse {

            List<PhotoUserAccount> items;

            public class PhotoUserAccount {

                String prefix;
                String suffix;
            }
        }
    }
}
