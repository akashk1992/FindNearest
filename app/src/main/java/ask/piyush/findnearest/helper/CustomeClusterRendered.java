package ask.piyush.findnearest.helper;

import android.content.Context;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import ask.piyush.findnearest.R;

/**
 * Created by piyush on 27/6/15.
 */
public class CustomeClusterRendered extends DefaultClusterRenderer<MyItem> {

    private ImageView mImageView;
    private ImageView mClusterImageView;
    private int mDimension;

    public CustomeClusterRendered(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
        /*final IconGenerator mIconGenerator = new IconGenerator(context);
        final IconGenerator mClusterIconGenerator = new IconGenerator(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View multiProfile = inflater.inflate(R.layout.custome_cluster, null);
        mClusterIconGenerator.setContentView(multiProfile);
        mClusterImageView = (ImageView) multiProfile.findViewById(R.id.image);
        mImageView = new ImageView(context);
        int padding = 2;
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
        mImageView.setPadding(padding, padding, padding, padding);
        mIconGenerator.setContentView(mImageView);*/
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);
//        mImageView.setImageResource(item.clusterIcon);
//        Bitmap icon = mIconGenerator.makeIcon();
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(person.name);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.reddot));
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<MyItem> cluster, MarkerOptions markerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions);
    }
}
