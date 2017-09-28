package vhoang52.cs273.orangecoastcollege.edu.paintestimator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mLengthEditText;
    EditText mWidthEditText;
    EditText mHeightEditText;
    EditText mDoorsEditText;
    EditText mWindowsEditText;

    Button mCalculateButton;
    Button mHelpButton;

    TextView mSurfaceAreaTextView;
    TextView mGallonsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
    }

    private void initializeViews() {
        mLengthEditText = (EditText) findViewById(R.id.lengthEditText);
        mWidthEditText = (EditText) findViewById(R.id.widthEditText);
        mHeightEditText = (EditText) findViewById(R.id.heightEditText);
        mDoorsEditText = (EditText) findViewById(R.id.doorsEditText);
        mWindowsEditText = (EditText) findViewById(R.id.windowsEditText);

        mCalculateButton = (Button) findViewById(R.id.calculateButton);
        mCalculateButton.setOnClickListener(this);
        mHelpButton = (Button) findViewById(R.id.helpButton);
        mHelpButton.setOnClickListener(this);

        mSurfaceAreaTextView = (TextView) findViewById(R.id.surfaceArea);
        mGallonsTextView = (TextView) findViewById(R.id.gallonsNeeded);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calculateButton:
                calculateHandler();
                break;
            case R.id.helpButton:
                calculateHandler();
                if (mGallonsTextView.getText().toString().length() > 0) {
                    Intent intent = new Intent(this, HelpActivity.class);
                    intent.putExtra("gallons", mGallonsTextView.getText().toString());
                } else {
                    Toast.makeText(this, "Enter input first", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    void calculateHandler() {
        try {
            DecimalFormat df = new DecimalFormat("#.0");
            PaintModel pm = new PaintModel(Integer.parseInt(mDoorsEditText.getText().toString()),
                                            Integer.parseInt(mWindowsEditText.getText().toString()),
                                            mHeightEditText.getText().toString(),
                                            mLengthEditText.getText().toString(),
                                            mWidthEditText.getText().toString());

            String surfaceArea = "Interior surface area: " + df.format(pm.totalSurfaceArea()) + " feet";
            mSurfaceAreaTextView.setText(surfaceArea);

            String gallonsNeeded = "Gallons needed: " + df.format(pm.gallonsOfPaintRequired());
            mGallonsTextView.setText(gallonsNeeded);
        } catch (Exception e) {
            Toast.makeText(this, "Enter valid input for all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
