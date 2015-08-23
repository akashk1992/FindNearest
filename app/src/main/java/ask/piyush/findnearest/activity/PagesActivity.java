package ask.piyush.findnearest.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
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
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d("test", "home called");
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void renderTermsOfUsesPage() {
        actionBar.setTitle("Terms Of Uses");
//        String htmlAsString = getResources().getString(R.string.terms_of_uses_string);
//        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString);
//        pageContent.setMovementMethod(LinkMovementMethod.getInstance());
        pageContent.setText(getResources().getString(R.string.terms_of_uses_string));
    }

    private void renderAboutUsPage() {
        actionBar.setTitle("About Us");
//        String htmlAsString = getResources().getString(R.string.about_us_string);
//        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString); // used by TextView
//        pageContent.setMovementMethod(LinkMovementMethod.getInstance());
        pageContent.setText(getResources().getString(R.string.about_us_string));
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    public void getWidgets() {
        pageContent = (TextView) findViewById(R.id.page_content);
        pageContent.setTypeface(Typeface.createFromAsset(getAssets(), "font/Roboto-Medium.ttf"));
    }
}
