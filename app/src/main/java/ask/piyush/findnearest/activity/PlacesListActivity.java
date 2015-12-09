package ask.piyush.findnearest.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import ask.piyush.findnearest.R;
import com.klinker.android.sliding.SlidingActivity;

/**
 * Created by piyush on 9/12/15.
 */
public class PlacesListActivity extends SlidingActivity {
  final String ARG_EXPANSION_LEFT_OFFSET = "arg_left_offset";
  final String ARG_EXPANSION_TOP_OFFSET = "arg_top_offset";
  final String ARG_EXPANSION_VIEW_WIDTH = "arg_view_width";
  final String ARG_EXPANSION_VIEW_HEIGHT = "arg_view_height";
  private Context context;
  private PlacesListActivity placesListActivity;
  private TextView placeTitle;
  private TextView placeAddrs;
  private ImageView placeIcon;

  @Override
  public void init(Bundle bundle) {
    this.context = this;
    this.placesListActivity = this;
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
    getWidgets();
  }

  private void getWidgets() {
    placeTitle = (TextView) findViewById(R.id.place_title);
    placeAddrs = (TextView) findViewById(R.id.place_addrs);
    placeIcon = (ImageView) findViewById(R.id.place_icon);
  }
}
