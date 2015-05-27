package ask.piyush.findnearest;

import android.app.Application;
import android.content.Context;

/**
 * Created by piyush on 26/5/15.
 */
public class FindNearestApp extends Application {

    static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
