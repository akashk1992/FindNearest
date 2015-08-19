package ask.piyush.findnearest.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import ask.piyush.findnearest.R;


public class PagesActivity extends Activity {

    private TextView pageContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pages_layout);
        getWidgets();
        Bundle extras = getIntent().getExtras();
        Log.d("test", "" + extras.getString("pageName"));
        String pageName = extras.getString("pageName");
        switch (pageName) {
            case "about_us":
                renderAboutUsPage();
                break;
            case "terms_of_uses":
                renderTermsOfUsesPage();
                break;
        }
    }

    private void renderTermsOfUsesPage() {
        pageContent.setText(getResources().getString(R.string.about_us_string));
    }

    private void renderAboutUsPage() {
        pageContent.setText(getResources().getString(R.string.terms_of_uses_string));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public void getWidgets() {
        pageContent = (TextView) findViewById(R.id.page_content);
        pageContent.setTypeface(Typeface.createFromAsset(getAssets(), "font/Roboto-Medium.ttf"));
    }
}
