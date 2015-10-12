package ask.piyush.findnearest.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ask.piyush.findnearest.R;
import codetail.graphics.drawables.LollipopDrawablesCompat;

/**
 * Created by piyush on 8/9/15.
 */
public class CustomeDrawerListAdapter extends BaseAdapter {

  private final String[] mNavTitle;
  private final int[] mIcons;
  private final Context context;

  public CustomeDrawerListAdapter(String[] mNavTitle, int[] mNavIcons, Context context) {
    this.mNavTitle = mNavTitle;
    this.mIcons = mNavIcons;
    this.context = context;
  }

  @Override
  public int getCount() {
    return mNavTitle.length;
  }

  @Override
  public Object getItem(int position) {
    return mNavTitle[position];
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @SuppressLint("NewApi")
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rootView = inflater.inflate(R.layout.drawer_list_item, parent, false);
    TextView listText = (TextView) rootView.findViewById(R.id.drawer_list_text);
    listText.setCompoundDrawablesWithIntrinsicBounds(mIcons[position], 0, 0, 0);
    listText.setBackground(getRippleDrawable(R.drawable.ripple_list_item));
    listText.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/candara.ttf"));
    listText.setText(mNavTitle[position]);
    return rootView;
  }

  public Drawable getRippleDrawable(int id) {
    return LollipopDrawablesCompat.getDrawable(context.getResources(), id, context.getTheme());
  }
}
