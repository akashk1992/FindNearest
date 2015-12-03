package ask.piyush.findnearest.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomeTextView extends TextView {
  private Context mContext;

  public CustomeTextView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    mContext = context;
    createFont();
  }

  public CustomeTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mContext = context;
    createFont();
  }

  public CustomeTextView(Context context) {
    super(context);
    mContext = context;
    createFont();
  }

  public void createFont() {
    Typeface font = Typeface.createFromAsset(mContext.getAssets(), "font/Campton-light.otf");
    setTypeface(font);
  }

  @Override
  public void setTypeface(Typeface tf) {
    super.setTypeface(tf);
  }
}
