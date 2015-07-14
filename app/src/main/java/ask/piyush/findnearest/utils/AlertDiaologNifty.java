package ask.piyush.findnearest.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import ask.piyush.findnearest.R;

/**
 * Created by piyush on 13/7/15.
 */
public class AlertDiaologNifty {
    public void dialogShow(final Context context, String message, final String action) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder.withTitle("Alert!").withMessage(message)
                .withTitleColor("#FFFFFF")
                .withDividerColor("#11000000")
                .withMessageColor("#FFFFFFFF")
                .withDialogColor("#009688")
//                .withIcon(FindNearestApp.getContext().getResources().getDrawable(R.drawable.icon))
                .isCancelableOnTouchOutside(false)
                .withDuration(700)
                .withEffect(Effectstype.Slidetop)
                .withButton1Text("OK")
                .withButton2Text("Cancel")
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (action != null)
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

        dialogBuilder.show();

    }

    public void dialogShow(final Context context, String message) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder.withTitle("Alert!").withMessage(message)
                .withTitleColor("#FFFFFF")
                .withDividerColor("#11000000")
                .withMessageColor("#FFFFFFFF")
                .withDialogColor("#009688")
//                .withIcon(FindNearestApp.getContext().getResources().getDrawable(R.drawable.icon))
                .isCancelableOnTouchOutside(false)
                .withDuration(700)
                .withEffect(Effectstype.Slidetop)
                .withButton1Text("OK")
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                });
        dialogBuilder.show();

    }

    public void dialogShow(final Context context, String message, int layout) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder.withTitle("Alert!").withMessage(message)
                .withTitleColor("#FFFFFF")
                .withDividerColor("#11000000")
                .withMessageColor("#FFFFFFFF")
                .withDialogColor("#009688")
//                .withIcon(FindNearestApp.getContext().getResources().getDrawable(R.drawable.icon))
                .isCancelableOnTouchOutside(false)
                .withDuration(700)
                .withEffect(Effectstype.Slidetop)
                .withButton1Text("OK")
                .withButton2Text("Cancel")
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.cancel();
                    }
                });
        dialogBuilder.setCustomView(layout, context);
        dialogBuilder.show();

    }
}