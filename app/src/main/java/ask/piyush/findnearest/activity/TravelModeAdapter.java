package ask.piyush.findnearest.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ask.piyush.findnearest.R;

/**
 * @author alessandro.balocco
 */
public class TravelModeAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;

    public TravelModeAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.simple_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        switch (position) {
            case 0:
                viewHolder.textView.setText("Driving");
                viewHolder.imageView.setImageResource(R.drawable.icon_steering1);
                break;
            case 1:
                viewHolder.textView.setText("Walking");
               viewHolder.imageView.setImageResource(R.drawable.walking1);
                break;
            case 2:
                viewHolder.textView.setText("Bicycling");
               viewHolder.imageView.setImageResource(R.drawable.bicycle);
                break;
        }

        return view;
    }

    static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
