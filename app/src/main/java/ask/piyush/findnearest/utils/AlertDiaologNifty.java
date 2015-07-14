package ask.piyush.findnearest.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import ask.piyush.findnearest.R;

/**
 * Created by piyush on 13/7/15.
 */
public class AlertDiaologNifty {
    public void dialogShow(final Context context, String message, boolean isCustome) {
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder.withTitle("Alert!")                                  //.withTitle(null)  no title
                .withTitleColor("#FFFFFF")                                  //def
                .withDividerColor("#11000000")                              //def
                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                .withDialogColor("#009688")                               //def  | withDialogColor(int resid)                               //def
//                .withIcon(FindNearestApp.getContext().getResources().getDrawable(R.drawable.icon))
                .isCancelableOnTouchOutside(false)                           //def    | isCancelable(true)
                .withDuration(700)                                          //def
                .withEffect(Effectstype.Slidetop)                                         //def Effectstype.Slidetop
                .withButton1Text("OK")                                      //def gone
                .withButton2Text("Cancel")                                  //def gone
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(action));
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.cancel();
                    }
                });
        if (isCustome) {
            dialogBuilder.setCustomView(R.layout.custom_alert_view, context);
            dialogBuilder.withMessage("Enter Radius");
        } else dialogBuilder.withMessage(message);

        dialogBuilder.show();

    }

}
