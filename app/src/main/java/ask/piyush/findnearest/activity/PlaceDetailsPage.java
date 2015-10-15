package ask.piyush.findnearest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import ask.piyush.findnearest.R;
import com.klinker.android.sliding.SlidingActivity;

public class PlaceDetailsPage extends SlidingActivity {
  final String ARG_USE_EXPANSION = "arg_use_expansion";
  final String ARG_EXPANSION_LEFT_OFFSET = "arg_left_offset";
  final String ARG_EXPANSION_TOP_OFFSET = "arg_top_offset";
  final String ARG_EXPANSION_VIEW_WIDTH = "arg_view_width";
  final String ARG_EXPANSION_VIEW_HEIGHT = "arg_view_height";
  private double destinationLat;
  private double destinationLng;

  @Override
  public void init(Bundle bundle) {
    setTitle("Places Details");
    setPrimaryColors(
        getResources().getColor(R.color.app_color_light),
        getResources().getColor(R.color.app_color_dark)
    );
    setContent(R.layout.place_details_page_activity);
    Intent intent = getIntent();
    expandFromPoints(
        intent.getIntExtra(ARG_EXPANSION_LEFT_OFFSET, 0),
        intent.getIntExtra(ARG_EXPANSION_TOP_OFFSET, 0),
        intent.getIntExtra(ARG_EXPANSION_VIEW_WIDTH, 0),
        intent.getIntExtra(ARG_EXPANSION_VIEW_HEIGHT, 0)
    );

   /* LatLng latLng = marker.getPosition();
    if (polylineList != null) {
      for (Polyline polyline : polylineList)
        polyline.remove();
    }
    destinationLat = latLng.latitude;
    destinationLng = latLng.longitude;*/
//    webServiceCallForActualPath(destinationLat, destinationLng);
//    dialog.dismiss();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    return super.onOptionsItemSelected(item);
  }
}
