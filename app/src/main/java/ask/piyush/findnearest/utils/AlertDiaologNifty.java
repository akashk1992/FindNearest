package ask.piyush.findnearest.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import ask.piyush.findnearest.R;
import ask.piyush.findnearest.activity.MainActivity;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by piyush on 13/7/15.
 */
public class AlertDiaologNifty {
    private int radius;

    public int getRadius() {
        return radius;
    }

    public void materialDialogForRadius(final MainActivity context, String message) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_alert_view, null);
        final EditText radius_input = (EditText) view.findViewById(R.id.radius_input);
        final MaterialDialog mMaterialDialog = new MaterialDialog(context);
        mMaterialDialog.setTitle("ALERT")
                .setMessage(message)
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String input = radius_input.getText().toString();
                        if (!input.equalsIgnoreCase("")) {
                            double aDouble = Double.parseDouble(input);
                            radius = (int) (1000 * aDouble);
                            Log.d("test", "radius1: " + radius);
                            mMaterialDialog.dismiss();
                        } else
                            new CustomToast(context).makeText("Enter Radius Then Press OK");
                    }
                })
                .setNegativeButton("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                }).setContentView(view);
        mMaterialDialog.show();
    }

    public void matrialDialog(MainActivity context, String message) {
        //alert to select place first
        final MaterialDialog mMaterialDialog = new MaterialDialog(context);
        mMaterialDialog.setTitle("ALERT")
                .setMessage(message)
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }

    public void materialDialogForGPS(final MainActivity context, String message, final String action) {
        final MaterialDialog mMaterialDialog = new MaterialDialog(context);
        mMaterialDialog.setTitle("ALERT")
                .setMessage(message)
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (action != null)
                            context.startActivity(new Intent(action));
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }

    public PopupWindow materialDialogMapTypes(final MainActivity context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.map_types_alert, null);
        ImageView satelliteView = (ImageView) (view).findViewById(R.id.map_type_satellite);
        ImageView normalMapView = (ImageView) (view).findViewById(R.id.map_type_normal);
        satelliteView.setTag("satelliteview");
        normalMapView.setTag("normalview");
        satelliteView.setOnClickListener(context);
        normalMapView.setOnClickListener(context);
        PopupWindow popupWindow = new PopupWindow(view, 295, 440, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.setAnimationStyle(R.anim.abc_slide_in_top);
        return popupWindow;
    }
}