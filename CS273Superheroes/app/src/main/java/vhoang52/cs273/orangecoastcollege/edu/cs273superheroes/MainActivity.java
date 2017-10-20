package vhoang52.cs273.orangecoastcollege.edu.cs273superheroes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Superhero Quiz ft. CS273 - Mobile Applications
 * <p>
 * Users can quiz themselves on their knowledge of their superhero classmates from a choice of 3 different
 * quiz types
 *
 * @author vhoang52
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    TextView mQuestionNumberTextView;
    TextView mGuessHintTextView;
    TextView mResultTextView;

    ImageView mImageView;

    Button[] mGuessButtons;

    SecureRandom rng;
    String mQuizType;

    ArrayList<Superhero> mSuperheroList;
    ArrayList<Superhero> mQuizList;
    ArrayList<String> mQuizTypeList;

    Superhero mSelectedSuperhero;
    String mCorrectAnswer;

    int[] progress;

    /**
     * Length of the quiz (# of questions)
     * Can be changed to any number between 1 and the number of students within the class
     */
    static final int QUIZ_LENGTH = 10;

    Handler runnable;

    /**
     * Listens for a change in the local preferences stored on device
     */
    SharedPreferences.OnSharedPreferenceChangeListener mSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            mQuizType = sharedPreferences.getString(getString(R.string.preferences_key), getString(R.string.preferences_default));
            resetQuiz();
        }
    };

    /**
     * onCreate operations:
     * - Load layout file
     * - Loads JSON formatted file containing all of the superhero objects
     * - initializes variables used within activity (random generator, progress trackers, Views, change listeners, etc)
     *
     * @param savedInstanceState null. savedInstanceState never written to
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "Attempting to load JSON file");
        mSuperheroList = JSONLoader.loadJSONFile(this);
        Log.i(TAG, "Heroes loaded: " + mSuperheroList.size());

        rng = new SecureRandom();
        progress = new int[2];
        initialize();

        SharedPreferences sprefs = PreferenceManager.getDefaultSharedPreferences(this);
        sprefs.registerOnSharedPreferenceChangeListener(mSharedPreferenceChangeListener);
        mQuizType = sprefs.getString(getString(R.string.preferences_key), getString(R.string.preferences_default));

        resetQuiz();
    }


    private void initialize() {
        mQuestionNumberTextView = (TextView) findViewById(R.id.questionNumber);
        mGuessHintTextView = (TextView) findViewById(R.id.guessHint);
        mResultTextView = (TextView) findViewById(R.id.guessResult);

        mImageView = (ImageView) findViewById(R.id.heroImage);

        mGuessButtons = new Button[4];
        mGuessButtons[0] = (Button) findViewById(R.id.guessOne);
        mGuessButtons[0].setOnClickListener(this);
        mGuessButtons[1] = (Button) findViewById(R.id.guessTwo);
        mGuessButtons[1].setOnClickListener(this);
        mGuessButtons[2] = (Button) findViewById(R.id.guessThree);
        mGuessButtons[2].setOnClickListener(this);
        mGuessButtons[3] = (Button) findViewById(R.id.guessFour);
        mGuessButtons[3].setOnClickListener(this);

        mQuizList = new ArrayList<>();
        runnable = new Handler();
    }

    /**
     * Loads the next quiz question
     * <p>
     * Re-enables buttons previously disabled, grabs the next hero to be quizzed, and advances the logic forward
     */
    private void loadNextHero() {
        for (int i = 0; i < mGuessButtons.length; i++) {
            mGuessButtons[i].setEnabled(true);
        }

        int selection = rng.nextInt(mQuizList.size());

        mSelectedSuperhero = mQuizList.get(selection);
        mQuizList.remove(selection);
        int currentQuestionNumber = QUIZ_LENGTH - mQuizList.size();
        mQuestionNumberTextView.setText(getString(R.string.question_tracker, currentQuestionNumber, QUIZ_LENGTH));

        try {
            String imageName = mSelectedSuperhero.getUsername() + ".png";
            InputStream in = getAssets().open(imageName);
            Drawable asset = Drawable.createFromStream(in, mSelectedSuperhero.getUsername());
            mImageView.setImageDrawable(asset);
        } catch (IOException e) {
            Log.d(TAG, "IOException when loading filename: " + mSelectedSuperhero.getUsername());
        }

        if (mQuizType.equals(getString(R.string.type_a))) {
            mQuizTypeList = new ArrayList<>();
            for (Superhero s : mSuperheroList) {
                mQuizTypeList.add(s.getName());
                mCorrectAnswer = mSelectedSuperhero.getName();
            }
        } else if (mQuizType.equals(getString(R.string.type_b))) {
            mQuizTypeList = new ArrayList<>();
            for (Superhero s : mSuperheroList) {
                mQuizTypeList.add(s.getSuperpower());
                mCorrectAnswer = mSelectedSuperhero.getSuperpower();
            }
        } else if (mQuizType.equals(getString(R.string.type_c))) {
            mQuizTypeList = new ArrayList<>();
            for (Superhero s : mSuperheroList) {
                mQuizTypeList.add(s.getOneThing());
                mCorrectAnswer = mSelectedSuperhero.getOneThing();
            }
        }

        do {
            Collections.shuffle(mQuizTypeList);
        } while (mQuizTypeList.subList(0, mGuessButtons.length).contains(mCorrectAnswer));

        for (int i = 0; i < mGuessButtons.length; i++) {
            mGuessButtons[i].setEnabled(true);
            mGuessButtons[i].setText(mQuizTypeList.get(i));
        }

        mGuessButtons[rng.nextInt(mGuessButtons.length)].setText(mCorrectAnswer);
    }

    /**
     * Advances the progress counters forward
     * (int[2] where [0] is current question # and [1] is the number of correct answers)
     * <p>
     * Listens to the button pressed and does the following:
     * - Increases progress counter, enables the visibility of the result text at the bottom
     * If the answer was correct: displays the correct answer in green until the next question is loaded
     * If the answer was incorrect: displays "Incorrect!" in red until the next question is loaded
     *
     * @param buttonPressed the button chosen by the user
     */
    public void chooseAnswer(Button buttonPressed) {
        progress[0]++;
        mResultTextView.setVisibility(View.VISIBLE);

        for (Button b : mGuessButtons) {
            b.setEnabled(false);
            mResultTextView.setText(mCorrectAnswer);
            mResultTextView.setTextColor(getResources().getColor(R.color.green));
        }

        if (buttonPressed.getText().toString().equals(mCorrectAnswer)) {
            progress[1]++;
        } else {
            mResultTextView.setText(R.string.prompt_incorrect);
            mResultTextView.setTextColor(getResources().getColor(R.color.red));
        }

        if (progress[0] < QUIZ_LENGTH) {

            for (int i = 0; i < mGuessButtons.length; i++) {
                mGuessButtons[i].setEnabled(false);
            }

            runnable.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadNextHero();
                    mResultTextView.setVisibility(View.GONE);
                }
            }, 2000);
        }

        if (progress[0] == QUIZ_LENGTH) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(getString(R.string.prompt_finish, progress[0], progress[1]));

            dialog.setPositiveButton(getString(R.string.prompt_reset), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    resetQuiz();
                }
            });
            dialog.setCancelable(false);
            dialog.create().show();
        }
    }

    /**
     * Clears out progress, hints, and rebuilds a list of superheroes based on the selected quiz type
     */
    private void resetQuiz() {
        mQuizList.clear();
        for (int i : progress) {
            i = 0;
        }
        mGuessHintTextView.setText(getString(R.string.type_description, mQuizType));

        while (mQuizList.size() < QUIZ_LENGTH) {
            Log.d(TAG, "mSuperHeroList size = " + mSuperheroList.size());
            Superhero hero = mSuperheroList.get(rng.nextInt(mSuperheroList.size()));
            if (!mQuizList.contains(hero)) {
                mQuizList.add(hero);
            }
        }

        Toast.makeText(MainActivity.this, getText(R.string.prompt_restarted), Toast.LENGTH_SHORT).show();
        mResultTextView.setText("");

        loadNextHero();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guessOne:
                chooseAnswer(mGuessButtons[0]);
                break;
            case R.id.guessTwo:
                chooseAnswer(mGuessButtons[1]);
                break;
            case R.id.guessThree:
                chooseAnswer(mGuessButtons[2]);
                break;
            case R.id.guessFour:
                chooseAnswer(mGuessButtons[3]);
                break;
        }
    }
}
