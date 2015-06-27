package ask.piyush.findnearest.activity;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ask.piyush.findnearest.R;
import ask.piyush.findnearest.fragments.MapFragment;
import ask.piyush.findnearest.helper.CustomeClusterRendered;
import ask.piyush.findnearest.helper.MyItem;
import ask.piyush.findnearest.helper.PojoMapping;
import ask.piyush.findnearest.utils.PromptUser;
import ask.piyush.findnearest.utils.VolleySingleton;

import static ask.piyush.findnearest.utils.FindNearestApp.getContext;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    public static GoogleApiClient mGoogleApiClient;
    private String mActivityTitle;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private android.support.v7.app.ActionBar actionBar;
    private ListView mDrawerList;
    public static android.support.v4.app.FragmentManager fragmentManager;
    private String[] mNavTitle;
    public Context context;
    private int[] mNavIcons;
    private LocationManager locationManager;
    private GoogleMap mMap;
    private double latitude;
    private double longitude;
    // Declare a variable for the cluster manager.
    ClusterManager<MyItem> mClusterManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        setUpNavigationDrawer();
        /********check GPS Status*************/
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        if (checkPlayServices()) {
            checkStatusAndCallMaps();
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, 1000).show();
            } else {
                Toast.makeText(getContext(), "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
            }
            return false;
        }
        return true;
    }

    private void checkStatusAndCallMaps() {
        if (isNetworkAvailable()) {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                PromptUser.displayPromptMessage(this, context.getString(R.string.gps_prompt_msg));
            } else {
                callMapFragment();
            }
        } else {
            //if Network not available prompt user
            PromptUser.displayPromptMessage(this, context.getString(R.string.internet_prompt_msg));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkStatusAndCallMaps();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();

        }
        super.onStop();
    }

    private void callMapFragment() {
        fragmentManager = getSupportFragmentManager();
        //call initial map fragment
        MapFragment newFragment = new MapFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.commit();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void setUpNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        actionBar = getSupportActionBar();
        mDrawerList = (ListView) findViewById(R.id.nav_drawer_list);
        mNavTitle = getResources().getStringArray(R.array.places_array);
//        mNavIcons = getResources().getIntArray(R.array.nav_drawer_icons);
        mNavIcons = new int[]{
                R.drawable.atm55,
                R.drawable.petrol55,
                R.drawable.hosital55,
                R.drawable.ic_launcher};
        //custome drawer list
        CustomeDrawerListAdapter customeDrawerListAdapter = new CustomeDrawerListAdapter(mNavTitle, mNavIcons);
        mDrawerList.setAdapter(customeDrawerListAdapter);
        mDrawerList.setOnItemClickListener(this);
        setUpDrawerToggle();
    }

    private void setUpDrawerToggle() {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);

        }
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                actionBar.setTitle("Places");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                actionBar.setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = drawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.cluster).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (id == R.id.cluster) {
            setUpClusterer();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, 0, this)
                .build();
        mGoogleApiClient.connect();
        setUpMapIfNeeded();
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
// 1) select item
// 2) update title(call @overriden setTitle() method)
// 3) close Drawer
        TextView drawerListText = (TextView) view.findViewById(R.id.drawer_list_text);
        String selectedDrawerItem = drawerListText.getText().toString();
        String placesWebServiceUrl = buildGooglePlaceUrl(selectedDrawerItem.trim().toLowerCase());
        webServiceapiCall(placesWebServiceUrl);
        mDrawerList.setItemChecked(position, true);
        setTitle(mNavTitle[position]);
        drawerLayout.closeDrawer(mDrawerList);
    }

    private String buildGooglePlaceUrl(String selectedDrawerItem) {
        return getString(R.string.web_service_url) + "location=17.4353663,78.3920193&radius=1000&types=" + selectedDrawerItem + "&key=AIzaSyC6OSRSBd2DXm6o7YTCQ1zoFK_3H3VgfPk&sensor=true";
    }

    private void setUpClusterer() {
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(17.1234576, 78.1234570), 12.0f));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(17.1234576, 78.1234570), 12.0f));
        // Add cluster items (markers) to the cluster manager.
        mClusterManager = new ClusterManager<MyItem>(this, mMap);
        mClusterManager.setRenderer(new CustomeClusterRendered(getContext(),mMap,mClusterManager));
        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        addItems();
    }

    private void addItems() {
//        double lat = 51.5145160;
//        double lng = -0.1270060;
        List list = new ArrayList();
        list.add(new MyItem(16.1234576, 78.1234570, R.drawable.reddot));
        list.add(new MyItem(15.1234575, 78.1234572, R.drawable.reddot));
        list.add(new MyItem(14.1234572, 78.1234575, R.drawable.reddot));
        list.add(new MyItem(13.1234571, 78.1234577, R.drawable.reddot));
        mClusterManager.addItems(list);
        mClusterManager.cluster();
    }

    private void webServiceapiCall(String placesWebServiceUrl) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                placesWebServiceUrl, "",
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("test", "places: " + response + "");
                        PojoMapping mapping = new PojoMapping();
                        ask.piyush.findnearest.model.Response jsonResponse = mapping.getPlacesResponse(response.toString());
                        Log.d("response", "" + jsonResponse.getResults().get(0).getName());
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // hide the progress dialog
                        Toast.makeText(getContext(), "Something Went Wrong..!", Toast.LENGTH_LONG).show();
                    }
                });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void setTitle(CharSequence title) {
        mActivityTitle = (String) title;
        actionBar.setTitle(mActivityTitle);
    }

    @Override
    public void onConnected(Bundle bundle) {
        displayLocation();
        startLocationUpdates();
    }

    private void displayLocation() {
        Location mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
        } else Toast.makeText(getContext(), "Couldn't get location..!", Toast.LENGTH_LONG).show();
    }

    private void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, createLocationRequest(), this);
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        setUpMap();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) MainActivity.fragmentManager.findFragmentById(R.id.mapFragment)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                // For displaying a move to my location button
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        //you can call this method whenever you have new lat long
        // For dropping a marker at a point on the Map
        MarkerOptions options = new MarkerOptions();
//        mMap.addMarker(options.position(new LatLng(17.654160, 78.322067)).title(getContext().getString(R.string.me)).icon(BitmapDescriptorFactory.fromResource(R.drawable.me)));
//        mMap.addMarker(options.position(new LatLng(latitude, longitude)).title(getContext().getString(R.string.me)).icon(BitmapDescriptorFactory.fromResource(R.drawable.me)));
//        mMap.addMarker(options.position(new LatLng(17.654789, 78.321654)).title(getContext().getString(R.string.me)).icon(BitmapDescriptorFactory.fromResource(R.drawable.me)));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Me").snippet("Home Address"));
        // For zooming automatically to the Dropped PIN Location
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12.0f));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("test", "" + connectionResult);
    }

    private class CustomeDrawerListAdapter extends BaseAdapter {

        private final String[] mTitles;
        private final int[] mIcons;

        public CustomeDrawerListAdapter(String[] mNavTitle, int[] mNavIcons) {
            this.mTitles = mNavTitle;
            this.mIcons = mNavIcons;
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View rootView = inflater.inflate(R.layout.drawer_list_item, parent, false);
            TextView listText = (TextView) rootView.findViewById(R.id.drawer_list_text);
            ImageView listIcon = (ImageView) rootView.findViewById(R.id.drawer_list_icon);
            listIcon.setBackground(getResources().getDrawable(mIcons[position]));
            listText.setTypeface(Typeface.createFromAsset(getAssets(), "font/RobotoCondensed-Regular.ttf"));
            listText.setText(mTitles[position]);
            return rootView;
        }
    }
}
