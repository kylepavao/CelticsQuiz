package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
 * Class for cheat panel
 */
public class CheatActivity extends Activity {
	
	public static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";
	
	private static final String TAG = "CheatActivity";
	private static final String KEY_CHEATER = "cheater";
	
	private TextView mAnswerTextView;
	private Button mShowAnswer;
	
	private boolean mAnswerIsTrue;
	private boolean mIsButtonClicked;
	
	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
	}
	
	/*
	 * params: saved instance state 
	 * Creates components of CheatActivity Panel creates action on click
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		mIsButtonClicked = false;
		setAnswerShownResult(false);
	
		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
		
		mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
		
		//Creates show answer button
		mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
		mShowAnswer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mAnswerIsTrue) {
					mAnswerTextView.setText(R.string.true_button);
				} else {
					mAnswerTextView.setText(R.string.false_button);
				}
				mIsButtonClicked = true;
				setAnswerShownResult(true);
			}
		});	
		//if no previous instance exists, load the default instance
        if (savedInstanceState != null) {
        	mIsButtonClicked = savedInstanceState.getBoolean(KEY_CHEATER, false);
        	setAnswerShownResult(mIsButtonClicked);
        	if(mAnswerIsTrue) {
    			mAnswerTextView.setText(R.string.true_button);
    		} else {
    			mAnswerTextView.setText(R.string.false_button);
    		}
        }
	}
	
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	super.onSaveInstanceState(savedInstanceState);
    	Log.i(TAG, "onSaveInstanceState");
    	savedInstanceState.putBoolean(KEY_CHEATER, mIsButtonClicked);
    }
}
