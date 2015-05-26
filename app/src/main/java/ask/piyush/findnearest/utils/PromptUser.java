package ask.piyush.findnearest.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import ask.piyush.findnearest.R;

/**
 * Created by piyush on 26/5/15.
 */
public class PromptUser {
    public static void displayPromptMessage(final Activity activity, String userPromptMessage) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
//        final String gpsPromptmessage = activity.getString(R.string.gps_prompt_msg);
//        final String internetPromptmessage = activity.getString(R.string.internet_prompt_msg);

        builder.setMessage(userPromptMessage)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();
    }
}
