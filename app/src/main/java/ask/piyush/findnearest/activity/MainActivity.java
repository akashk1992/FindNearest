package ask.piyush.findnearest.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import ask.piyush.findnearest.R;
import ask.piyush.findnearest.adapter.CustomeDrawerListAdapter;
import ask.piyush.findnearest.adapter.TravelModeAdapter;
import ask.piyush.findnearest.helper.CustomeClusterRendered;
import ask.piyush.findnearest.helper.MyItem;
import ask.piyush.findnearest.helper.PojoMapping;
import ask.piyush.findnearest.model.direction.DirectionResponse;
import ask.piyush.findnearest.model.direction.Route;
import ask.piyush.findnearest.model.places.Result;
import ask.piyush.findnearest.utils.*;
import codetail.graphics.drawables.DrawableHotspotTouch;
import codetail.graphics.drawables.LollipopDrawable;
import codetail.graphics.drawables.LollipopDrawablesCompat;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.clustering.ClusterManager;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.orhanobut.dialogplus.*;
import com.pnikosis.materialishprogress.ProgressWheel;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener, GoogleMap.OnInfoWindowClickListener {
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
  private double currentLatitude;
  private double currentLongitude;
  //harsha plaza =17.4353663,78.3920193
  ClusterManager<MyItem> mClusterManager;
  List<Polyline> polylineList = new ArrayList<>();
  private ProgressWheel progressWheel;
  private LinearLayout progressWheelLayout;
  private String mode = "driving";
  private List<Result> placesResponse;
  private int nearestPlaceIndex;
  private float zoomLevel = 2.0f;
  private int radius;
  private AlertDiaologNifty alertDialogRadius;
  private final String TRAVE_MODE_TAG = "travelmode";
  private final String RADIUS_TAG = "radius";
  private final String MAP_TYPE_TAG = "maptype";
  private FloatingActionMenu floatingActionMenu;
  private PopupWindow mapTypeAlert;
  private DirectionResponse directionResponse;
  private SharedPreferences first_time = null;
  private SharedPreferences first_time_markers;
  private boolean localFirstTime;
  private FrameLayout tutorialScreenLayout;
  private Double destinationLat;
  private Double destinationLng;
  private HashMap<String, String> extraMarkerInfo = new HashMap<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    context = getApplicationContext();
    initialSetup();
    setUpFab();
    new LoadingBar(context).showProgressWheel(true, progressWheel, progressWheelLayout);
    setUpNavigationDrawer();
    /********check GPS Status*************/
    locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
    if (checkPlayServices()) {
      mGoogleApiClient = new GoogleApiClient.Builder(this)
          .addConnectionCallbacks(this)
          .addOnConnectionFailedListener(this)
          .addApi(Places.GEO_DATA_API)
          .addApi(LocationServices.API)
          .addApi(Places.PLACE_DETECTION_API)
          .enableAutoManage(this, 0, this)
          .build();
      mGoogleApiClient.connect();
      checkStatusAndCallMaps();
    }
  }

  private void initialSetup() {
    localFirstTime = true;
    first_time = getSharedPreferences("find_nearest_first_time", 0);
    first_time_markers = getSharedPreferences("first_time_markers_tutorial", 0);
    findViewById(R.id.tutorial_image).setOnClickListener(this);
    findViewById(R.id.tutorial_image).setTag("tutorial");
    tutorialScreenLayout = (FrameLayout) findViewById(R.id.tutorial_screen_layout);
    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
    progressWheelLayout = (LinearLayout) findViewById(R.id.progress_wheel_layout);
  }

  public Drawable getRippleDrawable(int id) {
    return LollipopDrawablesCompat.getDrawable(getResources(), id, getTheme());
  }

  private void setUpFab() {
    final Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
    final ImageView iconMainActionBtn = new ImageView(this); // Create an icon
    iconMainActionBtn.setImageDrawable(getResources().getDrawable(R.drawable.options_white_new));
    FloatingActionButton mainActionButton = new FloatingActionButton.Builder(this)
        .setContentView(iconMainActionBtn)
        .build();
    if (Build.VERSION.SDK_INT >= 16)
      mainActionButton.setBackground(getRippleDrawable(R.drawable.ripple));
    mainActionButton.setOnTouchListener(
        new DrawableHotspotTouch((LollipopDrawable) mainActionButton.getBackground()));
    ImageView iconRadius = new ImageView(this); // Create an icon
    iconRadius.setImageDrawable(getResources().getDrawable(R.drawable.radius55));
    ImageView iconTravelMode = new ImageView(this); // Create an icon
    iconTravelMode.setImageDrawable(getResources().getDrawable(R.drawable.travelmodes55));
    ImageView iconMapType = new ImageView(this); // Create an icon
    iconMapType.setImageDrawable(getResources().getDrawable(R.drawable.maplayers55));

    SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
    SubActionButton menuRadius = itemBuilder.setContentView(iconRadius).build();
    SubActionButton menuTravelMode = itemBuilder.setContentView(iconTravelMode).build();
    SubActionButton menuMapType = itemBuilder.setContentView(iconMapType).build();

    menuRadius.setTag(RADIUS_TAG);
    menuTravelMode.setTag(TRAVE_MODE_TAG);
    menuMapType.setTag(MAP_TYPE_TAG);

    menuRadius.setOnClickListener(this);
    menuTravelMode.setOnClickListener(this);
    menuMapType.setOnClickListener(this);

    floatingActionMenu = new FloatingActionMenu.Builder(this)
        .addSubActionView(menuRadius)
        .addSubActionView(menuTravelMode)
        .addSubActionView(menuMapType)
        .attachTo(mainActionButton)
        .build();
    floatingActionMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
      @Override
      public void onMenuOpened(FloatingActionMenu floatingActionMenu) {
        iconMainActionBtn.startAnimation(animation);
        if (drawerLayout.isDrawerOpen(mDrawerList)) drawerLayout.closeDrawer(mDrawerList);
      }

      @Override
      public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
        iconMainActionBtn.clearAnimation();
      }
    });
  }

  private boolean checkPlayServices() {
    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
    if (resultCode != ConnectionResult.SUCCESS) {
      if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
        GooglePlayServicesUtil.getErrorDialog(resultCode, this, 1000).show();
      } else {
        new CustomToast(context).makeText(getString(R.string.device_not_supported));
      }
      return false;
    }
    return true;
  }

  private void checkStatusAndCallMaps() {
    if (isNetworkAvailable()) {
      if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        new AlertDiaologNifty().materialDialogForGPS(this, context.getString(R.string.gps_prompt_msg), Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        new LoadingBar(context).showProgressWheel(false, progressWheel, progressWheelLayout);
      } else {
        new LoadingBar(context).showProgressWheel(false, progressWheel, progressWheelLayout);
        setUpMapIfNeeded();
      }
    } else {
      //if Network not available prompt user
      new AlertDiaologNifty().matrialDialog(this, context.getString(R.string.internet_prompt_msg));
      new LoadingBar(context).showProgressWheel(false, progressWheel, progressWheelLayout);
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

  private boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) return true;
    else return false;
  }

  private void setUpNavigationDrawer() {
    mActivityTitle = getTitle().toString();
    actionBar = getSupportActionBar();
    mDrawerList = (ListView) findViewById(R.id.nav_drawer_list);
    mNavTitle = getResources().getStringArray(R.array.places_array);
//        mNavIcons = getResources().getIntArray(R.array.nav_drawer_icons);
    mNavIcons = new int[]{
        R.drawable.atm32,
        R.drawable.petrol32,
        R.drawable.hosital32,
        R.drawable.restaurant32,
        R.drawable.movietheatre,
        R.drawable.citypoliceicon32,
        R.drawable.bakeryicon,
        R.drawable.laundary32,
        R.drawable.beer32,
        R.drawable.bar32,
        R.drawable.bank32,
        R.drawable.bus_station32,
        R.drawable.train32,
        R.drawable.cafe32,
        R.drawable.church32,
        R.drawable.gym32,
        R.drawable.health32
    };
    //custome drawer list
    CustomeDrawerListAdapter customeDrawerListAdapter = new CustomeDrawerListAdapter(mNavTitle, mNavIcons, context);
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
    mDrawerToggle = new ActionBarDrawerToggle(
        this,
        drawerLayout,
        R.string.drawer_open,
        R.string.drawer_close) {
      @Override
      public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        if (floatingActionMenu != null && floatingActionMenu.isOpen()) {
          floatingActionMenu.close(true);
        }
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
//        menu.findItem(R.id.setting_string).setVisible(!drawerOpen);
    return super.onPrepareOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    int id = item.getItemId();
    if (mDrawerToggle.onOptionsItemSelected(item)) {
      return true;
    }
    if (id == R.id.about_us) {
      Intent intent = new Intent(MainActivity.this, PagesActivity.class);
      intent.putExtra("pageName", "about_us");
      startActivity(intent);
      return true;
    }
    if (id == R.id.terms_of_uses) {
      Intent intent = new Intent(MainActivity.this, PagesActivity.class);
      intent.putExtra("pageName", "terms_of_uses");
      startActivity(intent);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    setUpMapIfNeeded();
//        FirstTimeUser firstTimeUser1 = FirstTimeUser.findById(FirstTimeUser.class, (long) 1);
//        Log.d("tut", "" + firstTimeUser1);
    if (first_time.getBoolean("first_run", true)) {
      tutorialScreenLayout.setVisibility(View.VISIBLE);
      findViewById(R.id.tutorial_image).setBackgroundResource(R.drawable.tutorial);
      first_time.edit().putBoolean("first_run", false).commit();
    }
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
    view.setSelected(true);
    new LoadingBar(context).showProgressWheel(true, progressWheel, progressWheelLayout);
    TextView drawerListText = (TextView) view.findViewById(R.id.drawer_list_text);
    String selectedDrawerItem = drawerListText.getText().toString();
    String placesWebServiceUrl = buildGooglePlaceUrl(selectedDrawerItem.trim().toLowerCase().replace(' ', '_'));
    Log.d("test", "websrvc: " + placesWebServiceUrl);
    webServiceapiCall(placesWebServiceUrl, position);
    mDrawerList.setItemChecked(position, true);
    setTitle(mNavTitle[position]);
    drawerLayout.closeDrawer(mDrawerList);
  }

  private String buildGooglePlaceUrl(String selectedDrawerItem) {
//        return getString(R.string.web_service_url) + "location=17.4353663,78.3920193&radius=1000&types=" + selectedDrawerItem + "&key=AIzaSyC6OSRSBd2DXm6o7YTCQ1zoFK_3H3VgfPk&sensor=true";
    if (selectedDrawerItem.contains("fuel")) selectedDrawerItem = "gas_station";
    if (alertDialogRadius != null)
      radius = alertDialogRadius.getRadius();
    if (radius == 0) radius = 1000;
    Log.d("test", "web rad: " + radius);
    return getString(R.string.web_service_url) +
        "location=" + currentLatitude + "," + currentLongitude +
        "&radius=" + radius + "&types=" + selectedDrawerItem +
        "&key=" + getString(R.string.maps_web_service_api) + "&sensor=true";
  }

  private void setUpClusterer() {
    mClusterManager = new ClusterManager<MyItem>(this, mMap);
    mMap.setOnCameraChangeListener(mClusterManager);
    mMap.setOnMarkerClickListener(mClusterManager);
  }

  private void webServiceapiCall(final String placesWebServiceUrl, final int position) {
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
        placesWebServiceUrl, "",
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            double lat;
            double lng;
            mMap.clear();
            mMap.addMarker(
                new MarkerOptions()
                    .position(new LatLng(currentLatitude, currentLongitude))
                    .title(context.getString(R.string.me))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.me))
            );
            Log.d("test", "places: " + response);
            PojoMapping mapping = new PojoMapping();
            ask.piyush.findnearest.model.places.Response jsonResponse = mapping.getPlacesResponse(response.toString());
            placesResponse = jsonResponse.getResults();
            List<MyItem> clusterItems = new ArrayList();
            ArrayList<Double> distances = new ArrayList();
            if (!(placesResponse.size() == 0)) {
              for (int index = 0; index < placesResponse.size(); index++) {
                ask.piyush.findnearest.model.places.Location location1 = placesResponse.get(index).getGeometry().getLocation();
                lat = location1.getLat();
                lng = location1.getLng();
                distances.add(SphericalUtil.computeDistanceBetween(new LatLng(lat, lng), new LatLng(currentLatitude, currentLongitude)));
                clusterItems.add(new MyItem(lat, lng, mNavIcons[position], placesResponse.get(index)));
                extraMarkerInfo.put(placesResponse.get(index).getName(), placesResponse.get(index).getPlaceId());
              }
              ArrayList<Double> distBeforeSort = new ArrayList(distances);
              Collections.sort(distances);
              nearestPlaceIndex = distBeforeSort.indexOf(distances.get(0));
              addPlacesToCluster(clusterItems);
              Log.d("test", "distBeforeSort: " + distBeforeSort.size());
              Log.d("test", "distances: " + distances.size());
              distBeforeSort = null;
              distances = null;
              createPolylineToNearest(nearestPlaceIndex, placesResponse);
            } else {
              placesResponse = null;
              new CustomToast(context).makeText(getString(R.string.no_result_found));
            }
            new LoadingBar(context).showProgressWheel(false, progressWheel, progressWheelLayout);
          }
        },
        new Response.ErrorListener() {

          @Override
          public void onErrorResponse(VolleyError error) {
            // hide the progress dialog
            new CustomToast(context).makeText(getString(R.string.something_went_wrong));
            new LoadingBar(context).showProgressWheel(false, progressWheel, progressWheelLayout);
          }
        });
    VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
  }

  private void createPolylineToNearest(int nearestPlaceIndex, List<Result> placesResponse) {
    //create polyline to nearest one
    if (polylineList != null) {
      for (Polyline polyline : polylineList)
        polyline.remove();
    }
    destinationLat = placesResponse.get(nearestPlaceIndex).getGeometry().getLocation().getLat();
    destinationLng = placesResponse.get(nearestPlaceIndex).getGeometry().getLocation().getLng();
    webServiceCallForActualPath(destinationLat, destinationLng);
  }

  private void webServiceCallForActualPath(double destinationLat, double destinationLng) {
    Log.d("mode", "mode: " + mode);
    String path = "https://maps.googleapis.com/maps/api/directions/json?" +
        "origin=" + currentLatitude + "," + currentLongitude +
        "&destination=" + destinationLat + "," + destinationLng +
        "&key=AIzaSyC6OSRSBd2DXm6o7YTCQ1zoFK_3H3VgfPk&mode=" + mode.toLowerCase();
    Log.d("test", "direction: " + path);
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
        path, "",
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            Log.d("test", "path: " + response);
            directionResponse = new PojoMapping().getResponse(response.toString());
            List<Route> routes = directionResponse.getRoutes();
            String status = directionResponse.getStatus();
            if (status.equalsIgnoreCase("OK")) {
              String overview_polyline = routes.get(0).getOverviewPolyline().getPoints();
              String distance = routes.get(0).getLegs().get(0).getDistance().getText();
              Log.d("test", "dist: " + distance);
              direction(overview_polyline);
            } else if (status.equalsIgnoreCase("ZERO_RESULTS")) {
              new CustomToast(context).makeText(getString(R.string.zero_result_found));
            } else {
              new CustomToast(context).makeText(getString(R.string.something_went_wrong));
            }
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            Log.d("test", "Something Went Wrong While getting Directions");
          }
        });
    VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
  }

  private void direction(String points) {
    List<LatLng> decodedPath = PolyUtil.decode(points);
    int polylineColor;
    if (mode.equalsIgnoreCase("Driving"))
      polylineColor = getResources().getColor(R.color.app_color);
    else polylineColor = getResources().getColor(R.color.orange);
    Polyline polyline = mMap.addPolyline(new PolylineOptions().color(polylineColor).geodesic(true).addAll(decodedPath));

    /*CameraPosition cameraPosition = new CameraPosition.Builder()
        .target(new LatLng(currentLatitude, currentLongitude))
        .bearing(45)
        .zoom(16.0f)
        .tilt(85).build();*/
//    LatLngBounds bounds = new LatLngBounds(
//        new LatLng(destinationLat, destinationLng), new LatLng(currentLatitude, currentLongitude));

    CameraPosition cameraPosition = new CameraPosition.Builder()
        .target(new LatLng(currentLatitude, currentLongitude))
        .zoom(14.0f)
        .build();
    mMap.addCircle(new CircleOptions()
        .center(new LatLng(currentLatitude, currentLongitude))
        .radius(radius + 300)
        .strokeColor(getResources().getColor(R.color.app_color_light))
        .strokeWidth(5)
        .fillColor(0x5066c0b7));
    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
    polylineList.add(polyline);
  }

  private void addPlacesToCluster(List<MyItem> list) {
    if (first_time_markers.getBoolean("first_markers", true)) {
      //display first time markers use
      tutorialScreenLayout.setVisibility(View.VISIBLE);
      findViewById(R.id.tutorial_image).setBackgroundResource(R.drawable.markers);
      first_time_markers.edit().putBoolean("first_markers", false).commit();
    }
    mClusterManager.clearItems();
    mClusterManager.addItems(list);
    mClusterManager.setRenderer(new CustomeClusterRendered(getApplicationContext(), mMap, mClusterManager));
    mMap.setInfoWindowAdapter(new PlacesInfoWindowAdapter(context));
    mMap.setOnInfoWindowClickListener(this);
    mClusterManager.cluster();
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
      currentLatitude = mLastLocation.getLatitude();
      currentLongitude = mLastLocation.getLongitude();
    } else
      new CustomToast(context).makeText(getString(R.string.couldnt_get_location));
  }

  private void startLocationUpdates() {
    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, createLocationRequest(), this);
  }

  protected LocationRequest createLocationRequest() {
    LocationRequest mLocationRequest = new LocationRequest();
    mLocationRequest.setInterval(5000);//request sent at given interval
//        mLocationRequest.setFastestInterval(5000);//auto reception of my location at given interval
    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    return mLocationRequest;
  }

  @Override
  public void onLocationChanged(Location location) {
    currentLatitude = location.getLatitude();
    currentLongitude = location.getLongitude();
    if (localFirstTime == true) {
      localFirstTime = false;
      setUpMap();
    }
  }

  private void setUpMapIfNeeded() {
    if (mMap == null) {
      // Try to obtain the map from the SupportMapFragment.
      fragmentManager = getSupportFragmentManager();
      mMap = ((SupportMapFragment) MainActivity.fragmentManager.findFragmentById(R.id.mapFragment)).getMap();
      // Check if we were successful in obtaining the map.
      if (mMap != null) {
        // For displaying a move to my location button
        mMap.setMyLocationEnabled(true);
//                mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setBuildingsEnabled(true);
        setUpClusterer();
        setUpMap();
      }
    }
  }

  private void setUpMap() {
    //you can call this method whenever you have new lat long
    // For dropping a marker at a point on the Map
    if (mMap != null) {
      Log.d("test", "set up mapp called");
      mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).flat(true).title(context.getString(R.string.me)).icon(BitmapDescriptorFactory.fromResource(R.drawable.me)));
      if (zoomLevel == 2.0f) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 16.0f));
        zoomLevel = mMap.getCameraPosition().zoom;
      } else
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), mMap.getCameraPosition().zoom));
    }
  }

  @Override
  public void onConnectionSuspended(int i) {
  }

  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {
    Log.d("test", "" + connectionResult);
  }

  @Override
  public void onClick(View v) {
    if (v.getTag().equals(RADIUS_TAG)) {
      floatingActionMenu.close(true);
      alertDialogRadius = new AlertDiaologNifty();
      alertDialogRadius.materialDialogForRadius(this, "");
    } else if (v.getTag().equals(TRAVE_MODE_TAG)) {
      floatingActionMenu.close(true);
      OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
          TextView textView = (TextView) view.findViewById(R.id.text_view);
          String selectedMode = textView.getText().toString();
          dialog.dismiss();
          mode = selectedMode;
          if (placesResponse != null) {
            if (polylineList != null) {
              for (Polyline polyline : polylineList)
                polyline.remove();
            }
            webServiceCallForActualPath(destinationLat, destinationLng);
          } else {
            AlertDiaologNifty alert = new AlertDiaologNifty();
            alert.matrialDialog(MainActivity.this, getString(R.string.select_place_alert));
          }
        }
      };
      TravelModeAdapter adapter = new TravelModeAdapter(context);
      showOnlyContentDialog(new ListHolder(), Gravity.TOP, adapter, itemClickListener);
    } else if (v.getTag().equals(MAP_TYPE_TAG)) {
      floatingActionMenu.close(true);
      progressWheelLayout.setAlpha(0.6f);
      if (mMap != null) {
        mapTypeAlert = new AlertDiaologNifty().materialDialogMapTypes(MainActivity.this);
      }
    } else if (v.getTag().equals("normalview")) {
      if (mMap != null) mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
      progressWheelLayout.setAlpha(0);
      mapTypeAlert.dismiss();
    } else if (v.getTag().equals("satelliteview")) {
      if (mMap != null) mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
      progressWheelLayout.setAlpha(0);
      mapTypeAlert.dismiss();
    } else if (v.getTag().equals("tutorial")) {
      tutorialScreenLayout.setVisibility(View.GONE);
    }
  }

  private void showOnlyContentDialog(Holder holder, int gravity, BaseAdapter adapter,
                                     OnItemClickListener itemClickListener) {
    final DialogPlus dialog = new DialogPlus.Builder(this)
        .setContentHolder(holder)
        .setGravity(gravity)
        .setAdapter(adapter)
        .setOnItemClickListener(itemClickListener)
        .setExpanded(false)
        .setCancelable(true)
        .create();
    dialog.show();
  }

  public Intent addExpansionArgs(Intent intent) {
    final String ARG_USE_EXPANSION = "arg_use_expansion";
    final String ARG_EXPANSION_LEFT_OFFSET = "arg_left_offset";
    final String ARG_EXPANSION_TOP_OFFSET = "arg_top_offset";
    final String ARG_EXPANSION_VIEW_WIDTH = "arg_view_width";
    final String ARG_EXPANSION_VIEW_HEIGHT = "arg_view_height";
//    intent.putExtra(ARG_USE_EXPANSION, true);
    View expansionView = findViewById(R.id.expansion_view);
    int location[] = new int[2];
    expansionView.getLocationInWindow(location);
    intent.putExtra(ARG_EXPANSION_LEFT_OFFSET, location[0]);
    intent.putExtra(ARG_EXPANSION_TOP_OFFSET, location[1]);
    intent.putExtra(ARG_EXPANSION_VIEW_WIDTH, expansionView.getWidth());
    intent.putExtra(ARG_EXPANSION_VIEW_HEIGHT, expansionView.getHeight());
    return intent;
  }

  @Override
  public void onInfoWindowClick(final Marker marker) {
    floatingActionMenu.close(true);
    String placeId = extraMarkerInfo.get(marker.getTitle());
    Intent intent = new Intent(this, PlaceDetailsPage.class);
    intent.putExtra("placeId", placeId);
    intent.putExtra("placeTitle", marker.getTitle());
    startActivity(addExpansionArgs(intent));
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    this.finish();
  }
}
