package ask.piyush.findnearest.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ask.piyush.findnearest.R;
import ask.piyush.findnearest.helper.PojoMapping;
import ask.piyush.findnearest.model.placeDetails.Photo;
import ask.piyush.findnearest.model.placeDetails.PlaceDetailsResponse;
import ask.piyush.findnearest.model.placeDetails.Result;
import ask.piyush.findnearest.model.placeDetails.Review;
import ask.piyush.findnearest.utils.VolleySingleton;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.klinker.android.sliding.SlidingActivity;
import com.squareup.picasso.Picasso;
import io.techery.properratingbar.ProperRatingBar;
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
  private ProperRatingBar userRatingsBar;
  private ProperRatingBar criticsRatingBar;
  private TextView placeRatingTV;
  private TextView criticsRatingTV;
  private TextView phoneNumTV;
  private TextView phoneText;
  private LinearLayout imagesContainer;
  private TextView yesButton;
  public static PlaceDetailsPage slidingActivity;

  @Override
  public void init(Bundle bundle) {
    this.context = this;
    this.slidingActivity = this;
    setPrimaryColors(
        getResources().getColor(R.color.app_color_light),
        getResources().getColor(R.color.app_color_dark)
    );
    setContent(R.layout.place_details_page_activity);
    disableHeader();
    Intent intent = getIntent();
    expandFromPoints(
        intent.getIntExtra(ARG_EXPANSION_LEFT_OFFSET, 0),
        intent.getIntExtra(ARG_EXPANSION_TOP_OFFSET, 0),
        intent.getIntExtra(ARG_EXPANSION_VIEW_WIDTH, 0),
        intent.getIntExtra(ARG_EXPANSION_VIEW_HEIGHT, 0)
    );
    placeId = intent.getStringExtra("placeId");
    placeName = intent.getStringExtra("placeTitle");
    getWidgets();
    webServiceCall();
  }

  private void getWidgets() {
    imagesContainer = (LinearLayout) findViewById(R.id.images_container);
    placeTitle = (TextView) findViewById(R.id.place_heading_tv);
    vicinityLandmark = (TextView) findViewById(R.id.landmark_tv);
    userRatingsBar = (ProperRatingBar) findViewById(R.id.user_rating_bar);
    placeRatingTV = (TextView) findViewById(R.id.place_ratings_tv);
    phoneNumTV = (TextView) findViewById(R.id.phone_number_tv);
    phoneText = (TextView) findViewById(R.id.phone_text);
    criticsRatingTV = (TextView) findViewById(R.id.critics_ratings_tv);
    criticsRatingBar = (ProperRatingBar) findViewById(R.id.rating_bar);
    yesButton = (TextView) findViewById(R.id.yes_button);
    yesButton.setTag("yes");
    yesButton.setOnClickListener(MainActivity.mainActivity);
  }

  private void webServiceCall() {
    String placeDetailsUrl = getString(R.string.place_details_webservice) +
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
              populate(placeDetailsResponse.getResult());
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

  public void dialNumber(View view) {
    Uri number = Uri.parse("tel:" + placeDetailsResponse.getResult().getFormattedPhoneNumber());
    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
    startActivity(callIntent);
  }

  private void setTextStyle() {
    SpannableString spannableString1 = new SpannableString(phoneText.getText());
    spannableString1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.purplle)), 5, phoneText.getText().length(), 0);
    spannableString1.setSpan(new RelativeSizeSpan(0.7f), 5, phoneText.getText().length(), 0);
    phoneText.setText(spannableString1);

    SpannableString spannableString2 = new SpannableString(phoneNumTV.getText());
    spannableString2.setSpan(new UnderlineSpan(), 0, phoneNumTV.getText().length(), 0);
    phoneNumTV.setText(spannableString2);

    SpannableString spannableString3 = new SpannableString(vicinityLandmark.getText());
    spannableString3.setSpan(new RelativeSizeSpan(0.8f), 0, 6, 0);
    vicinityLandmark.setText(spannableString3);
  }

  public void populate(Result result) {
    for (Photo photo : result.getPhotos()) {
      createImageView(photo.getPhotoReference());
    }
    placeTitle.setText(placeName);
    Integer critcsReview = 0;
    if (result.getReviews() != null) {
      for (Review review : result.getReviews()) {
        critcsReview += review.getRating();
      }
      try {
        critcsReview = critcsReview / result.getReviews().size();
        criticsRatingBar.setRating(critcsReview);
      } catch (ArithmeticException e) {
        criticsRatingBar.setRating(0);
      }
    } else {
      criticsRatingBar.setVisibility(View.GONE);
    }
    vicinityLandmark.setText("Near By\n" + result.getVicinity());
    if (result.getUserRatingsTotal() != null)
      userRatingsBar.setRating(result.getUserRatingsTotal());
    if (result.getFormattedPhoneNumber() != null && !result.getFormattedPhoneNumber().isEmpty()) {
      phoneNumTV.setText(result.getFormattedPhoneNumber());
    } else {
      phoneNumTV.setVisibility(View.GONE);
      phoneText.setVisibility(View.GONE);
    }
    setTextStyle();
  }

  private void createImageView(String photoReference) {
    ImageView imageView = new ImageView(this);
//    imageView.setImageResource(R.drawable.play);
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT);
    layoutParams.weight = 1;
    layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
    imageView.setAdjustViewBounds(true);
    imageView.setPadding(2, 0, 2, 0);
    imageView.setLayoutParams(layoutParams);
    Picasso.with(context).
        load(getResources().getString(R.string.webservice_photo_url) + "maxwidth=800&photoreference=" + photoReference + "&key=" + getResources().getString(R.string.maps_web_service_api)).
        resize(600, 400).
        into(imageView);
    imagesContainer.addView(imageView);
  }
}
