package ask.piyush.findnearest.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import ask.piyush.findnearest.R;
import ask.piyush.findnearest.model.places.OpeningHours;
import ask.piyush.findnearest.utils.FindNearestApp;

/**
 * Created by piyush on 27/6/15.
 */
public class CustomeClusterRendered extends DefaultClusterRenderer<MyItem> implements ClusterManager.OnClusterItemClickListener<MyItem> {

    private final ClusterManager<MyItem> clusterManager;
    private final ImageView mClusterItemIcon;
    private TextView mClusterItemText;
    final IconGenerator mIconGenerator = new IconGenerator(FindNearestApp.getContext());

    public CustomeClusterRendered(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
        this.clusterManager = clusterManager;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custome_cluster_item, null);
        mClusterItemText = (TextView) view.findViewById(R.id.info_window);
        mClusterItemIcon = (ImageView) view.findViewById(R.id.cluster_icon);
        mIconGenerator.setContentView(view);
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);
        if (item != null) {
            mClusterItemIcon.setImageResource(item.clusterIcon);
            Bitmap icon = mIconGenerator.makeIcon();
            String placeStatus;
            OpeningHours openingHours = item.placeDetails.getOpeningHours();
            markerOptions.icon(BitmapDescriptorFactory.fromResource(item.clusterIcon)).title(item.placeDetails.getName());
            if (openingHours != null) {
                placeStatus = openingHours.getOpenNow() ? "Open" : "Close";
                markerOptions.snippet("Status: Now " + placeStatus);
            }
            clusterManager.setOnClusterItemClickListener(this);
        }
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<MyItem> cluster, MarkerOptions markerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions);
    }

    @Override
    public boolean onClusterItemClick(MyItem myItem) {
        return false;
    }
}
