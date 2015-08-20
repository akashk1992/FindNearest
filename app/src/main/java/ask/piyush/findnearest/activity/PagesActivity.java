package ask.piyush.findnearest.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import ask.piyush.findnearest.R;


public class PagesActivity extends AppCompatActivity {

    private TextView pageContent;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pages_layout);
        setUpActionBar();
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

    private void setUpActionBar() {
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    private void renderTermsOfUsesPage() {
        pageContent.setText(getResources().getString(R.string.terms_of_uses_string));
    }

    private void renderAboutUsPage() {
        pageContent.setText(getResources().getString(R.string.about_us_string));
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
