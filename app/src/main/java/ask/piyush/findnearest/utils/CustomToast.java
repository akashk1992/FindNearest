package ask.piyush.findnearest.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ask.piyush.findnearest.R;

/**
 * Created by piyush on 10/8/15.
 */
public class CustomToast {
    private final Context context;

    public CustomToast(Context context) {
        this.context = context;
    }

    public void makeText(String message) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_toast, null);
        TextView toastText = (TextView) view.findViewById(R.id.toast_msg);
        Toast toast = new Toast(context);
        toastText.setText(message);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setView(view);
        toast.show();
    }
}
