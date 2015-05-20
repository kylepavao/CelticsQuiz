package com.bignerdranch.android.geoquiz;
/*
 * Class for creating question
 * Keeps track of question, answer and whether the user asked for a hint
 */
public class TrueFalse {
	private int mQuestion;
	
	private boolean mTrueQuestion;
	private boolean mCheated;
	
	/* 
	 * Constructor: sets question, whether or not the answer is true, whether or not the user
	 * has cheated on this question
	 */
	public TrueFalse(int question, boolean trueQuestion, boolean cheated) {
		mQuestion = question;
		mTrueQuestion = trueQuestion;
		mCheated = cheated;
	}
	
	/* 
	 * returns question
	 */
	public int getQuestion() {
		return mQuestion;
	}
	
	/*
	 *  Params: integer of question element
	 *  Sets question for this object
	 */
	public void setQuestion(int question) {
		mQuestion = question;
	}
	
	/*
	 * Return: true if answer is true
	 */
	public boolean isTrueQuestion() {
		return mTrueQuestion;
	}
	
	/*
	 * Params: true if answer is true
	 */
	public void setTrueQuestion(boolean trueQuestion) {
		mTrueQuestion = trueQuestion;
	}
	
	/*
	 * Params: true if user cheated
	 */
	public void setCheated(boolean cheated) {
		mCheated = cheated;
	}
	
	/*
	 * Return: true if user cheated on this question
	 */
	public boolean getCheated() {
		return mCheated;
	}

}
