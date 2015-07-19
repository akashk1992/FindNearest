package ask.piyush.findnearest.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import ask.piyush.findnearest.R;

/**
 * Created by piyush on 13/7/15.
 */
public class AlertDiaologNifty {
    private int radius;

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

    public int dialogShow(final Context context, String message, int layout) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, null);
        final EditText radius_input = (EditText) view.findViewById(R.id.radius_input);
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder.withTitle("Alert!").withMessage(message)
                .withTitleColor("#FFFFFF")
                .withDividerColor("#11000000")
                .withMessageColor("#FFFFFFFF")
                .withDialogColor("#009688")
//                .withIcon(FindNearestApp.getContext().getResources().getDrawable(R.drawable.icon))
                .isCancelableOnTouchOutside(false)
                .withDuration(700)
                .withEffect(Effectstype.Slidetop).setCustomView(view, context)
                .withButton1Text("OK")
                .withButton2Text("Cancel")
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String input = radius_input.getText().toString();
                        if (!input.equalsIgnoreCase("")) {
                            double aDouble = Double.parseDouble(input);
                            radius = (int) (1000 * aDouble);
                            Log.d("test", "radius1: " + radius);
                            dialogBuilder.dismiss();
                        } else
                            Toast.makeText(context, "Please Enter Radius", Toast.LENGTH_LONG).show();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.cancel();
                    }
                });
        dialogBuilder.show();
        return radius;
    }
}