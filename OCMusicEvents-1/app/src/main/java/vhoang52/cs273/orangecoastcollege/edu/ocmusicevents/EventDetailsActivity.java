package vhoang52.cs273.orangecoastcollege.edu.ocmusicevents;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

/**
 * Gets details passed in from intent and displays the corresponding information to the user
 */
public class EventDetailsActivity extends AppCompatActivity {

    TextView mTitleText;
    TextView mDetailsText;
    ImageView mArtistImage;

    /**
     * Assigns ids to views and applies the correct references from the intent passed in
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        mTitleText = (TextView) findViewById(R.id.titleTextView);
        mDetailsText = (TextView) findViewById(R.id.detailsTextView);
        mArtistImage = (ImageView) findViewById(R.id.detailsImageView);

        try {
            String title = getIntent().getStringExtra("title");
            mTitleText.setText(title);
            String details = getIntent().getStringExtra("details");
            mDetailsText.setText(details);

            String imageName = title.trim().replace(" ", "") + ".jpeg";
            AssetManager am = getAssets();
            InputStream stream = am.open(imageName);
            Drawable image = Drawable.createFromStream(stream, title);
            mArtistImage.setImageDrawable(image);
        } catch (Exception e) {
            Toast.makeText(this, "Error occurred while retrieving details", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}
