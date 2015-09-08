package ask.piyush.findnearest.utils;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import ask.piyush.findnearest.R;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by piyush on 4/7/15.
 */
public class LoadingBar {
  private final Context context;

  public LoadingBar(Context context) {
    this.context = context;
  }

  public void showProgressWheel(boolean b, final ProgressWheel progressWheel, LinearLayout progressBarLayout) {
    if (b) {
      progressWheel.setVisibility(View.VISIBLE);
      progressBarLayout.setAlpha(0.6f);
      progressBarLayout.setEnabled(false);
      progressWheel.setBarColor(context.getResources().getColor(R.color.app_color));
      progressWheel.spin();
    } else {
      progressWheel.setVisibility(View.GONE);
      progressBarLayout.setAlpha(0);
      progressBarLayout.setEnabled(true);
      progressWheel.stopSpinning();
    }
  }
}
