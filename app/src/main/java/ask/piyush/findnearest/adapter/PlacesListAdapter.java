package ask.piyush.findnearest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ask.piyush.findnearest.R;
import ask.piyush.findnearest.model.places.Result;

import java.util.ArrayList;

/**
 * Created by piyush on 20/12/15.
 */
public class PlacesListAdapter extends BaseAdapter {
  private final Context context;
  private final ArrayList<Result> list;

  public PlacesListAdapter(Context context, ArrayList<Result> placesList) {
    this.context = context;
    this.list = placesList;
  }

  @Override
  public int getCount() {
    return list.size();
  }

  @Override
  public Object getItem(int i) {
    return i;
  }

  @Override
  public long getItemId(int i) {
    return 0;
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    if (view == null) {
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      view = inflater.inflate(R.layout.list_place_item, null);
    }
    TextView title = (TextView) view.findViewById(R.id.place_title);
    TextView address = (TextView) view.findViewById(R.id.place_addrs);
    title.setText(list.get(i).getName());
    address.setText(list.get(i).getVicinity());
    return view;
  }
}
