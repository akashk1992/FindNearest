package ask.piyush.findnearest.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
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
        String htmlAsString = "<b>Terms of uses:</b><br><br>" +
                "App will require following Permission from User:<br>" +
                "<i>1) Internet Connection<br>" +
                "2) User location<br>" +
                "3) GPS enabled<br></i><br><br>" +
                "[Note*: We assure you that we ain't sharing your location with anyone and " +
                "The Find Nearest Team respects user's privacy]";
        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString);
        pageContent.setMovementMethod(LinkMovementMethod.getInstance());
        pageContent.setText(htmlAsSpanned);
//        pageContent.setText(getResources().getString(R.string.terms_of_uses_string));
    }

    private void renderAboutUsPage() {
        actionBar.setTitle("About Us");
        String htmlAsString = "<b>Find Nearest</b><br><br>" +
                "developed By Akash Khandale<br>" +
                "developer id: askplay92<br>" +
                "contact: askplay92@gmail.com<br>" +
                "web-site: <a href='http://akashkhandale.wix.com/find-nearest'>Find Nearest</a><br><br>" +
                "<i><b>Find Nearest</b> is maps based application," +
                "that lets you find the nearest place first as per selected category.<br>" +
                "This app will be very useful for people who travels a lot.<br>" +
                "Also who are newer in locations." +
                "So with this app, you can find the nearest place to you with the most precise location" +
                "and you can explore more about place as well.<br><br>" +
                "User can also get the exact direction to the place with real time Navigation." +
                "Not Only one place but user can get direction to related place.";
        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString); // used by TextView
        pageContent.setMovementMethod(LinkMovementMethod.getInstance());
        pageContent.setText(htmlAsSpanned);
//        pageContent.setText(getResources().getString(R.string.about_us_string));
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    public void getWidgets() {
        pageContent = (TextView) findViewById(R.id.page_content);
        pageContent.setTextSize(16);
    }
}
