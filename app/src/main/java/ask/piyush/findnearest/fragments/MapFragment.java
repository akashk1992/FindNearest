package ask.piyush.findnearest.fragments;

import android.app.Fragment;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ask.piyush.findnearest.R;
import ask.piyush.findnearest.activity.MainActivity;

/**
 * Created by PIYUSH on 5/16/2015.
 */
public class MapFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener {
    private View view;
    private double latitude;
    private double longitude;
    private GoogleMap mMap;
    private LocationManager locationManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        view = (RelativeLayout) inflater.inflate(R.layout.map_fragment, container, false);
        // Passing harcoded values for latitude & longitude. Please change as per your need. This is just used to drop a Marker on the Map
        latitude = 26.78;
        longitude = 72.56;
        setUpMapIfNeeded(); // For setting up the MapFragment
        return view;
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) MainActivity.fragmentManager.findFragmentById(R.id.mapFragment)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                // For showing a move to my loction button
                mMap.setMyLocationEnabled(true);
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        // For dropping a marker at a point on the Map
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("My Home").snippet("Home Address"));
        // For zooming automatically to the Dropped PIN Location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12.0f));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (mMap != null)
            setUpMap();

        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) MainActivity.fragmentManager
                    .findFragmentById(R.id.mapFragment)).getMap(); // getMap is deprecated
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                // For showing a move to my loction button
                mMap.setMyLocationEnabled(true);
                setUpMap();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
/*
        if (mMap != null) {
            MainActivity.fragmentManager.beginTransaction()
                    .remove(MainActivity.fragmentManager.findFragmentById(R.id.mapFragment)).commit();
            mMap = null;
        }
*/
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
}
