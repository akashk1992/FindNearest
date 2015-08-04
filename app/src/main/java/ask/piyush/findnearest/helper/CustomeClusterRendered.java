package ask.piyush.findnearest.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
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
public class CustomeClusterRendered extends DefaultClusterRenderer<MyItem> implements ClusterManager.OnClusterItemClickListener<MyItem>, ClusterManager.OnClusterClickListener<MyItem>, ClusterManager.OnClusterInfoWindowClickListener<MyItem>, ClusterManager.OnClusterItemInfoWindowClickListener<MyItem> {

    private final ClusterManager<MyItem> clusterManager;
    private final ImageView mClusterItemIcon;
    private ImageView mImageView;
    private int mNavIcon;
    private TextView mClusterItemText;
    private int mDimension;
    final IconGenerator mIconGenerator = new IconGenerator(FindNearestApp.getContext());

    public CustomeClusterRendered(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
        this.mNavIcon = mNavIcon;
        this.clusterManager = clusterManager;
//        final IconGenerator mClusterIconGenerator = new IconGenerator(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custome_cluster_item, null);
//        mClusterIconGenerator.setContentView(view);
        mClusterItemText = (TextView) view.findViewById(R.id.info_window);
        mClusterItemIcon = (ImageView) view.findViewById(R.id.cluster_icon);
//        mImageView = new ImageView(context);
//        int padding = 2;
//        mImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        mClusterItemText.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
//        mClusterItemText.setPadding(padding, padding, padding, padding);
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
            clusterManager.setOnClusterClickListener(this);
            clusterManager.setOnClusterInfoWindowClickListener(this);
            clusterManager.setOnClusterItemInfoWindowClickListener(this);
        }
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<MyItem> cluster, MarkerOptions markerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions);
    }

    @Override
    public boolean onClusterItemClick(MyItem myItem) {
        Log.d("test", "cluster item clicked" + myItem.placeDetails);
        return false;
    }

    @Override
    public boolean onClusterClick(Cluster<MyItem> myItemCluster) {
        Log.d("test", "onClusterClick" + myItemCluster);
        Log.d("test", "onClusterClick" + myItemCluster.getItems());
        return false;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<MyItem> myItemCluster) {
        Log.d("test", "onClusterInfoWindowClick" + myItemCluster);
    }

    @Override
    public void onClusterItemInfoWindowClick(MyItem myItem) {
        Log.d("test", "onClusterItemInfoWindowClick" + myItem.placeDetails);
//        mClusterItemText.setText(myItem.placeDetails);
    }
}
