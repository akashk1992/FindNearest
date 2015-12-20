package ask.piyush.findnearest;

import android.app.Application;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by piyush on 3/12/15.
 */
public class FindNearestApp extends Application {
  private Tracker mTracker;

  @Override
  public void onCreate() {
    super.onCreate();
    GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
    // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
    mTracker = analytics.newTracker(R.xml.global_tracker);
    mTracker.enableAutoActivityTracking(true);
    mTracker.enableExceptionReporting(true);
  }
}
