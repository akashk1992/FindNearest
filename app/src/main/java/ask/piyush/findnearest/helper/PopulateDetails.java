package ask.piyush.findnearest.helper;

import android.content.Context;
import android.util.Log;
import ask.piyush.findnearest.utils.VolleySingleton;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

/**
 * Created by piyush on 15/10/15.
 */
public class PopulateDetails {
  private final Context context;

  public PopulateDetails(Context context) {
    this.context = context;
    String placeDetailsUrl = "https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJN1t_tDeuEmsRUsoyG83frY4&key=AIzaSyC6OSRSBd2DXm6o7YTCQ1zoFK_3H3VgfPk";
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
        Request.Method.GET,
        placeDetailsUrl,
        "",
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            populate(response);
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            Log.d("test", "Volley error: " + error);
          }
        });

    VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
  }

  public void populate(JSONObject response) {
    Log.d("test", "place details response: " + response.toString());
  }
}
