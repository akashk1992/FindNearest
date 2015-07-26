package ask.piyush.findnearest.helper;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by piyush on 19/6/15.
 */
public class MyItem implements ClusterItem {
    public final LatLng mPosition;
    //    public final int clusterIcon;
    public String placeName;

    public MyItem(double lat, double lng, String placesResponse) {
        this.placeName = placesResponse;
        mPosition = new LatLng(lat, lng);
//        this.clusterIcon = clusterIcon;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}
