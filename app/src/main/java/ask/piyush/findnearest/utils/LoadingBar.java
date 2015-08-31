package ask.piyush.findnearest.utils;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by piyush on 4/7/15.
 */
public class LoadingBar {
    public static void showProgressWheel(boolean b, final ProgressWheel progressWheel, LinearLayout progressBarLayout) {
        if (b) {
            progressWheel.setVisibility(View.VISIBLE);
            progressBarLayout.setAlpha(0.6f);
            progressBarLayout.setEnabled(false);
            progressWheel.setBarColor(Color.RED);
            progressWheel.spin();
        } else {
            progressWheel.setVisibility(View.GONE);
            progressBarLayout.setAlpha(0);
            progressBarLayout.setEnabled(true);
            progressWheel.stopSpinning();
        }
    }
}
