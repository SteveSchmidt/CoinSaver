/***************************************************************************************
 *   COINSAVER
 * 
 * Created by Steve Schmidt 2012
 * 
 * This application will provide three activities:
 * BudgetBuster - calculate how much money is spent over the course of a year or more.
 * Compound Savings - calculate how much money can be saved when compound interest is applied.
 * Target Savings - see how long it takes to save up to a goal with interest.
 **************************************************************************************/
package com.schmidt.coinsaver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//for testing: import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainMenu extends Activity {

	private Intent budgetIntent;
	private Intent compIntent;
	private Intent targIntent;

	// for testing: private final static String TAG = "Main Menu";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main_menu);
		initialize();

		// full screen
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
		// for testing: Log.i(TAG, "On Create .....");
	}

	/************************************************
	 * Initialize
	 ************************************************/
	 public void initialize() {
		
		try {
			budgetIntent = new Intent(MainMenu.this, BudgetBuster.class);
			compIntent = new Intent(MainMenu.this, CompoundSavings.class);
			targIntent = new Intent(MainMenu.this, TargetSavings.class);
		} catch (Exception e) {
			// for testing: Log.i(TAG, "initialize Intents .....");
		}

	}

	/************************************************
	 * Getters and Setters
	 ************************************************/
	public Intent getBudgetIntent() {
		return budgetIntent;
	}

	public void setBudgetIntent(Intent budgetIntent) {
		this.budgetIntent = budgetIntent;
	}

	public Intent getCompIntent() {
		return compIntent;
	}

	public void setCompIntent(Intent compIntent) {
		this.compIntent = compIntent;
	}

	public Intent getTargIntent() {
		return targIntent;
	}

	public void setTargIntent(Intent targIntent) {
		this.targIntent = targIntent;
	}

	/************************************************
	 * startBudget
	 * begins BudgetBuster Activity
	 ************************************************/
	public void startBudget(View view) {
		try {
			startActivity(budgetIntent);
		} catch (Exception e) {
			// for testing: Log.i(TAG, "start BudgetBuster .....");
		}
	}

	/************************************************
	 * startCompoundSavings
	 * begins Compound Savings Activity
	 ************************************************/
	public void startCompoundSavings(View view) {
		try {
			startActivity(compIntent);
		} catch (Exception e) {
			// for testing: Log.i(TAG, "start CompoundSavings .....");
		}
	}

	/************************************************
	 * startTargetSavings
	 * begins Target Savings Activity
	 ************************************************/
	public void startTargetSavings(View view) {
		try {
			startActivity(targIntent);
		} catch (Exception e) {
			// for testing: Log.i(TAG, "start TargetSavings .....");
		}
	}

	/************************************************
	 * targetToast
	 * Pop up info for Target Savings Activity
	 ************************************************/
	public void targetToast(View view) {
		Context context = getApplicationContext();
		CharSequence message = "Set your target goal and see how long it takes to save up to it.";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, message, duration);
		toast.show();

	}

	/************************************************
	 * compoundToast
	 * Pop up info for Compound Saving Activity
	 ************************************************/
	public void compoundToast(View view) {
		Context context = getApplicationContext();
		CharSequence message = "See how your money grows with compound interest applied.";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}

	/************************************************
	 * budgetToast
	 * Pop up info for BudgedBuster Activity
	 ************************************************/
	public void budgetToast(View view) {
		Context context = getApplicationContext();
		CharSequence message = "See how much money is spent over time.  Little by little, it adds up.";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// for testing: Log.i(TAG, "On Start .....");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// for testing: Log.i(TAG, "On Restart .....");
	}

	@Override
	protected void onResume() {
		super.onResume();
		// for testing: Log.i(TAG, "On Resume .....");
	}

	@Override
	protected void onPause() {
		super.onPause();
		// for testing: Log.i(TAG, "On Pause .....");
	}

	@Override
	protected void onStop() {
		super.onStop();
		// for testing: Log.i(TAG, "On Stop .....");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// for testing: Log.i(TAG, "On Destroy .....");
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		// for testing: Log.i(TAG, "On Back Pressed .....");
	}

}
