package ask.piyush.findnearest.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import ask.piyush.findnearest.R;

/**
 * Created by piyush on 3/8/15.
 */
public class PlacesInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final LayoutInflater layoutInflater;
    private final Context context;

    public PlacesInfoWindowAdapter(Context context) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(final Marker marker) {
        View view = layoutInflater.inflate(R.layout.info_window, null);
        TextView placeName = (TextView) view.findViewById(R.id.place_name);
        TextView placeStatus = (TextView) view.findViewById(R.id.place_status);
        placeName.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/Roboto-Medium.ttf"));
        placeStatus.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/Roboto-MediumItalic.ttf"));
        placeName.setText(marker.getTitle());
        placeStatus.setText(marker.getSnippet());
        return view;
    }
}
