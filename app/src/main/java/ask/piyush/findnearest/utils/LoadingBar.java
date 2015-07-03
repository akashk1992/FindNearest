package ask.piyush.findnearest.utils;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;

import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by piyush on 4/7/15.
 */
public class LoadingBar {
    public static void showProgressWheel(boolean b, ProgressWheel progressWheel, DrawerLayout drawerLayout) {
        if (b) {
            progressWheel.setVisibility(View.VISIBLE);
            drawerLayout.setAlpha(0.8f);
            progressWheel.setBarColor(Color.RED);
            progressWheel.setRimColor(Color.WHITE);
            progressWheel.spin();
        } else {
            progressWheel.setVisibility(View.GONE);
            drawerLayout.setAlpha(1f);
            progressWheel.stopSpinning();
        }
    }
}
