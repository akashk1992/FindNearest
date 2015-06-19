package ask.piyush.findnearest.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ask.piyush.findnearest.R;
import ask.piyush.findnearest.activity.MainActivity;
import ask.piyush.findnearest.helper.PlacesResponseHandler;

/**
 * Created by PIYUSH on 5/16/2015.
 */
public class MapFragment extends Fragment implements PlacesResponseHandler {
    private View view;
    private double latitude;
    private double longitude;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        view = inflater.inflate(R.layout.map_fragment, null);
        return view;
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) MainActivity.fragmentManager.findFragmentById(R.id.mapFragment)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                // For displaying a move to my location button
                mMap.setMyLocationEnabled(true);
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        //you can call this method whenever you have new lat long
        // For dropping a marker at a point on the Map
        Log.d("test", "set up map called" + latitude + " -- " + longitude);
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(getActivity().getString(R.string.me)).icon(BitmapDescriptorFactory.fromResource(R.drawable.me)));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Me").snippet("Home Address"));
        // For zooming automatically to the Dropped PIN Location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12.0f));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void getPlaces() {

    }
}
