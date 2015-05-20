package com.bignerdranch.android.geoquiz;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build.VERSION;

/*
 * QuizActivity Class
 * Controls the functions of each item on the app's main panel
 */
public class QuizActivity extends ActionBarActivity {
	
	private static final String TAG = "QuizActivity";
	private static final String KEY_INDEX = "index";
	private static final String KEY_CHEATER = "cheater";
	private static final String KEY_QUESTION_CHEATER = "question cheater";
	private static final String TEXT_BUILD = "API Level " + android.os.Build.VERSION.SDK;
	
	private Button mTrueButton;
	private Button mFalseButton;
	private ImageButton mNextButton;
	private ImageButton mPreviousButton;
	private Button mCheatButton;
	private TextView mQuestionTextView;
	private TextView mBuildTextView;
	
	//Creates ArrayList of TrueFalse objects
	private TrueFalse[] mQuestionBank = new TrueFalse[] {
			new TrueFalse(R.string.question_championships, false, false),
			new TrueFalse(R.string.question_no6, true, false),
			new TrueFalse(R.string.question_bird_coaching, true, false),
			new TrueFalse(R.string.question_rondo_draft_year, false, false),
			new TrueFalse(R.string.question_bob_cousy_roy, false, false),
	};
	
	private int mCurrentIndex = 0;
	
	private boolean mIsCheater;
	
	/*
	 * Updates the question shown within mQuestionTextView
	 */
	private void updateQuestion() {
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mIsCheater = mQuestionBank[mCurrentIndex].getCheated();
		
		mQuestionTextView.setText(question);
	}
	
	/*
	 * Params: true if user pressed the true button
	 * checks to see if user guessed the correct answer
	 */
	private void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		
		int messageResId = 0;
		
		if (mIsCheater) { 
			messageResId = R.string.judgement_toast;
		} else {
			if(userPressedTrue == answerIsTrue) {
				messageResId = R.string.correct_toast;
			} else { 
				messageResId = R.string.incorrect_toast;
			}
		}
		
			Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
	}
	
	/*
	 * Params: saved state of panel
	 * Creates each component found on the main panel and sets 
	 * what each component does on click
	 */
    @TargetApi(11)
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	android.app.ActionBar actionBar = getActionBar();
        	actionBar.setSubtitle("Ultimate Fan Quiz");
        }
        //Creates question text
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);	
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				updateQuestion();	
			}
        });
        //Creates true button
        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
        });
        //Creates false button
        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(false);	
			}
        });
        //Creates next button
        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				mIsCheater = false;
				updateQuestion();	
			}
        });
        //Creates previous button
        mPreviousButton = (ImageButton)findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mCurrentIndex >= 1) {
					mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
					updateQuestion();		
				} else {
					mCurrentIndex = mQuestionBank.length - 1;
					updateQuestion();
				}
			}
        });
        //Creates cheat button
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Intent i = new Intent(QuizActivity.this, CheatActivity.class);
        		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        		i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        		startActivityForResult(i, 0);     		
        	}
        });
        //Creates android build text
        mBuildTextView = (TextView)findViewById(R.id.build_text_view);
        mBuildTextView.setText(TEXT_BUILD);
        
        //if no saved instance use first question in ArrayList
        if (savedInstanceState != null) {
        	mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        	mIsCheater = savedInstanceState.getBoolean(KEY_CHEATER, true);
        	mQuestionBank[mCurrentIndex].setCheated(savedInstanceState.getBoolean(KEY_QUESTION_CHEATER, true));
        }      
        updateQuestion();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (data == null) {
    		return;
    	}
    	mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    	mQuestionBank[mCurrentIndex].setCheated(mIsCheater);
    }
    
    /*
     * Params: previous saved instance
     * Updates saved instance if view of application is interrupted
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	super.onSaveInstanceState(savedInstanceState);
    	Log.i(TAG, "onSaveInstanceState");
    	savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    	savedInstanceState.putBoolean(KEY_CHEATER, mIsCheater);
    	savedInstanceState.putBoolean(KEY_QUESTION_CHEATER, mQuestionBank[mCurrentIndex].getCheated());
    }
    
    /*
     * Starts application
     */
    @Override
    public void onStart() {
    	super.onStart();
    	Log.d(TAG, "onStart() called");
    }
    
    /*
     * Suspends state of app if paused
     */
    @Override
    public void onPause() {
    	super.onPause();
    	Log.d(TAG, "onPause() called");
    }
    
    /*
     * Resumes app on paused state
     */
    @Override
    public void onResume() {
    	super.onResume();
    	Log.d(TAG, "onResume() called");
    }
    
    /*
     * Stops app 
     */
    @Override
    public void onStop() {
    	super.onStop();
    	Log.d(TAG, "onStop() called");
    }
    
    /*
     * Destroys app state
     */
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	Log.d(TAG, "onDestroy() called");
    }
    
    /*
     * Creates options menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
