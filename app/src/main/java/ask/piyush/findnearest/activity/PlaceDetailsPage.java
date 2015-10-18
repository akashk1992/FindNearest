package ask.piyush.findnearest.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import ask.piyush.findnearest.R;
import ask.piyush.findnearest.helper.PojoMapping;
import ask.piyush.findnearest.model.placeDetails.PlaceDetailsResponse;
import ask.piyush.findnearest.utils.VolleySingleton;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.klinker.android.sliding.SlidingActivity;
import org.json.JSONObject;

public class PlaceDetailsPage extends SlidingActivity {
  final String ARG_USE_EXPANSION = "arg_use_expansion";
  final String ARG_EXPANSION_LEFT_OFFSET = "arg_left_offset";
  final String ARG_EXPANSION_TOP_OFFSET = "arg_top_offset";
  final String ARG_EXPANSION_VIEW_WIDTH = "arg_view_width";
  final String ARG_EXPANSION_VIEW_HEIGHT = "arg_view_height";
  private Context context;
  TextView placeTitle;
  PlaceDetailsResponse placeDetailsResponse;
  private String placeId;
  private String placeName;
  private TextView vicinityLandmark;
  private TextView userRatings;

  @Override
  public void init(Bundle bundle) {
    this.context = this;
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
    placeId = intent.getStringExtra("placeId");
    placeName = intent.getStringExtra("placeTitle");
    Log.d("test", "placeId " + placeId);
    placeTitle = (TextView) findViewById(R.id.place_heading_tv);
    vicinityLandmark = (TextView) findViewById(R.id.landmark_tv);
    userRatings = (TextView) findViewById(R.id.place_ratings_tv);
    webServiceCall();
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

  private void webServiceCall() {
    String placeDetailsUrl = "https://maps.googleapis.com/maps/api/place/details/json?" +
        "placeid=" + placeId +
        "&key=" + getResources().getString(R.string.maps_web_service_api);
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
        Request.Method.GET,
        placeDetailsUrl,
        "",
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            Log.d("test", "place details response: " + response.toString());
            placeDetailsResponse = new PojoMapping().getDetailsResponse(response.toString());
            if (placeDetailsResponse.getStatus().equalsIgnoreCase("ok"))
              populate();
            else Toast.makeText(PlaceDetailsPage.this, "Something went wrong..!", Toast.LENGTH_SHORT).show();
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            Log.d("test", "Volley error: " + error);
            Toast.makeText(PlaceDetailsPage.this, "Internal Error Occured", Toast.LENGTH_SHORT).show();
          }
        });

    VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    return super.onOptionsItemSelected(item);
  }

  public void populate() {

    placeTitle.setText(placeName);
    vicinityLandmark.setText("Near By: " + placeDetailsResponse.getResult().getVicinity());
    userRatings.setText("User Rating: " + placeDetailsResponse.getResult().getUserRatingsTotal());
  }
}
