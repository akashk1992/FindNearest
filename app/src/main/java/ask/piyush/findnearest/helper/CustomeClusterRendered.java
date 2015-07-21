package ask.piyush.findnearest.helper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.util.List;

import ask.piyush.findnearest.R;
import ask.piyush.findnearest.model.places.Result;

/**
 * Created by piyush on 27/6/15.
 */
public class CustomeClusterRendered extends DefaultClusterRenderer<MyItem> implements ClusterManager.OnClusterItemClickListener<MyItem>, ClusterManager.OnClusterClickListener<MyItem>, ClusterManager.OnClusterInfoWindowClickListener<MyItem>, ClusterManager.OnClusterItemInfoWindowClickListener<MyItem> {

    private final List<Result> placesResponse;
    private final ClusterManager<MyItem> clusterManager;
    private ImageView mImageView;
    private int mNavIcon;
    private TextView mClusterItem;
    private int mDimension;

    public CustomeClusterRendered(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager, int mNavIcon, List<Result> placesResponse) {
        super(context, map, clusterManager);
        this.mNavIcon = mNavIcon;
        this.placesResponse = placesResponse;
        this.clusterManager = clusterManager;
        /*final IconGenerator mIconGenerator = new IconGenerator(context);
        final IconGenerator mClusterIconGenerator = new IconGenerator(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custome_cluster, null);
        mClusterIconGenerator.setContentView(view);
        mClusterItem = (TextView) view.findViewById(R.id.info_window);
//        mImageView = new ImageView(context);
        int padding = 2;
//        mImageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
        mClusterItem.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
        mClusterItem.setPadding(padding, padding, padding, padding);
        mIconGenerator.setContentView(mClusterItem);*/
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);
//        mImageView.setImageResource(item.clusterIcon);
//        Bitmap icon = mIconGenerator.makeIcon();
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(person.name);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(mNavIcon));
        clusterManager.setOnClusterItemClickListener(this);
        clusterManager.setOnClusterClickListener(this);
        clusterManager.setOnClusterInfoWindowClickListener(this);
        clusterManager.setOnClusterItemInfoWindowClickListener(this);
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<MyItem> cluster, MarkerOptions markerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions);
    }

    @Override
    public boolean onClusterItemClick(MyItem myItem) {
        Log.d("test", "cluster item clicked" + myItem.placeName);
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
        Log.d("test", "onClusterItemInfoWindowClick" + myItem.placeName);
//        mClusterItem.setText(myItem.placeName);
    }
}
