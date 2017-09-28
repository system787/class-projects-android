package vhoang52.cs273.orangecoastcollege.edu.paintestimator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HelpActivity extends AppCompatActivity {

    Button mReturnButton;
    TextView mGallonsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        mReturnButton = (Button) findViewById(R.id.returnButton);

        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        mGallonsTextView = (TextView) findViewById(R.id.estimatedPaint);

        String display = "Estimated Paint Required: ";
        try {
            display += getIntent().getStringExtra("gallons") + "gallons";
            mGallonsTextView.setText(display);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
