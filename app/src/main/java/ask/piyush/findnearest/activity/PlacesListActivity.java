package ask.piyush.findnearest.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import ask.piyush.findnearest.R;
import ask.piyush.findnearest.adapter.PlacesListAdapter;
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
  private PlacesListActivity placesListActivity;
  private TextView placeTitle;
  private TextView placeAddrs;
  private ImageView placeIcon;
  private ArrayList<Result> placesList;
  private ListView placesListView;

  @Override
  public void init(Bundle bundle) {
    this.context = this;
    this.placesListActivity = this;
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
    getWidgets();
    placesList = MainActivity.allPlaces;
    populateList();
  }

  private void populateList() {
  }

  private void getWidgets() {
    placesListView = (ListView) findViewById(R.id.all_places_list);
    placesListView.setAdapter(new PlacesListAdapter(context, placesList));
  }
}
