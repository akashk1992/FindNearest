package ask.piyush.findnearest.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import ask.piyush.findnearest.R;
import ask.piyush.findnearest.model.places.Result;
import com.klinker.android.sliding.SlidingActivity;

import java.util.ArrayList;

/**
 * Created by piyush on 9/12/15.
 */
public class PlacesListActivity extends SlidingActivity {
  final String ARG_EXPANSION_LEFT_OFFSET = "arg_left_offset";
  final String ARG_EXPANSION_TOP_OFFSET = "arg_top_offset";
  final String ARG_EXPANSION_VIEW_WIDTH = "arg_view_width";
  final String ARG_EXPANSION_VIEW_HEIGHT = "arg_view_height";
  private Context context;
  private ArrayList<Result> placesList;
  private LinearLayout container;

  @Override
  public void init(Bundle bundle) {
    this.context = this;
    setPrimaryColors(
        getResources().getColor(R.color.app_color_light),
        getResources().getColor(R.color.app_color_dark)
    );
    setContent(R.layout.all_places_layout);
    disableHeader();

    Intent intent = getIntent();
    expandFromPoints(
        intent.getIntExtra(ARG_EXPANSION_LEFT_OFFSET, 0),
        intent.getIntExtra(ARG_EXPANSION_TOP_OFFSET, 0),
        intent.getIntExtra(ARG_EXPANSION_VIEW_WIDTH, 0),
        intent.getIntExtra(ARG_EXPANSION_VIEW_HEIGHT, 0)
    );
    placesList = MainActivity.allPlaces;
    addView();
  }

  private void addView() {
    container = (LinearLayout) findViewById(R.id.place_item_container);
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    for (Result result : placesList) {
      View view = inflater.inflate(R.layout.list_place_item, null);
      TextView title = (TextView) view.findViewById(R.id.place_title);
      TextView address = (TextView) view.findViewById(R.id.place_addrs);
      title.setText(result.getName());
      address.setText(result.getVicinity());
      container.addView(view);
    }
  }
}
