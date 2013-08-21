/*************************************************
 *   BudgetBuster
 * 
 * Created by Steve Schmidt 2012
 * 
 * This activity will calculate the total money
 * spent over the course of one or more years
 * given the amount per day, how many days per month
 * and how many months per year.
 ************************************************/
package com.schmidt.coinsaver;

import android.app.Activity;
import android.os.Bundle;
//for testing: import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class BudgetBuster extends Activity {

	// for testing: private static final String TAG = "Budget Buster";
	private Button bCalc;
	private EditText etAmount;
	private EditText etDays;
	private EditText etMonths;
	private EditText etYears;
	private EditText etTotal;
	private double amount;
	private double days;
	private double months;
	private double years;
	private double answerDub;
	private double tempD;
	private String answerStr;
	private String amountStr;
	private String daysStr;
	private String monthsStr;
	private String yearsStr;
	private boolean numbersVerified;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.budget_buster);
		initialize();

		// full screen
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		// Calculate button is linked to the calculate method 
		// and calls it once clicked
		bCalc.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// for testing: Log.i(TAG, "Start Calculation onClick");
				calculate();
			}
		});
	}
	/*************************************************
	 *   Initialize()
	 *   create a starting point for all variables
	 ************************************************/
	private void initialize() {
		// for testing: Log.i(TAG, "initialize()");
		etAmount = (EditText) findViewById(R.id.etAmount);
		etDays = (EditText) findViewById(R.id.etDays);
		etMonths = (EditText) findViewById(R.id.etMonths);
		etYears = (EditText) findViewById(R.id.etYears);
		bCalc = (Button) findViewById(R.id.bCalculate);
		etTotal = (EditText) findViewById(R.id.etTotal);
		
		// Default setting for variables
		// Months and years are preset 
		amount = 0.0;
		days = 0.0;
		months = 12.0;
		years = 1.0;
		answerDub = 0.0;
		tempD = 0.0;
		
		answerStr = "null";
		amountStr = "null";
		daysStr = "null";
		monthsStr = "null";
		yearsStr = "null";
		numbersVerified = false;

	}
	
	/*************************************************
	 *   calculate()
	 *   performs required calculation after calling 
	 *   check methods
	 ************************************************/
	public void calculate() {
		// for testing: Log.i(TAG, "calculate()");
		numbersVerified = false;
		amountStr = getEtAmount().getText().toString();
		daysStr = getEtDays().getText().toString();
		monthsStr = getEtMonths().getText().toString();
		yearsStr = getEtYears().getText().toString();

		// Ensure that the numbers as text equate to real numbers
		amount = parseText(amountStr);
		days = parseText(daysStr);
		months = parseText(monthsStr);
		years = parseText(yearsStr);

		// Ensure that the numbers are appropriate to this activity
		verifyNumbers();

		// calculate and format final string
		if (numbersVerified) {
			answerDub = amount * days * months * years;
			try {
				answerStr = String.format("$ %.2f", answerDub);
			} catch (Exception e) {
				etTotal.setText("ERROR! No answer format");
			}
			etTotal.setText(answerStr);
		}
	}
	/*************************************************
	 *   parseText()
	 *   performs required check to transfer
	 *   string data into number form
	 ************************************************/
	private Double parseText(String str) {
		// for testing: Log.i(TAG, "parseText()");
		try {
			tempD = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			etTotal.setText("ERROR! " + str + " not a number");
		}
		return tempD;
	}
	/*************************************************
	 *   verifyNumbers()
	 *   performs required check to ensure
	 *   numbers are appropriate
	 ************************************************/
	private void verifyNumbers() {
		// for testing: Log.i(TAG, "verifyNumbers()");
		numbersVerified = true;
		if (amount <= 0 || days <= 0 || months <= 0 || years <= 0) {
			etTotal.setText("No numbers <= 0");
			numbersVerified = false;
		}
		if (days > 30) {
			etTotal.setText("Days must be < 31");
			numbersVerified = false;
		}
		if (months > 12) {
			etTotal.setText("Months must be <= 12");
			numbersVerified = false;
		} 
	}
	
	/*************************************************
	 * Overridden life cycle methods
	 ************************************************/
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
	
	/*************************************************
	 * Getters and Setters
	 ************************************************/
	public EditText getEtAmount() {
		return etAmount;
	}
	public void setEtAmount(EditText etAmount) {
		this.etAmount = etAmount;
	}
	public Button getbCalc() {
		return bCalc;
	}
	public void setbCalc(Button bCalc) {
		this.bCalc = bCalc;
	}
	public EditText getEtDays() {
		return etDays;
	}
	public void setEtDays(EditText etDays) {
		this.etDays = etDays;
	}
	public EditText getEtMonths() {
		return etMonths;
	}
	public void setEtMonths(EditText etMonths) {
		this.etMonths = etMonths;
	}
	public EditText getEtYears() {
		return etYears;
	}
	public void setEtYears(EditText etYears) {
		this.etYears = etYears;
	}
	public EditText getEtTotal() {
		return etTotal;
	}
	public void setEtTotal(EditText etTotal) {
		this.etTotal = etTotal;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getDays() {
		return days;
	}
	public void setDays(double days) {
		this.days = days;
	}
	public double getMonths() {
		return months;
	}
	public void setMonths(double months) {
		this.months = months;
	}
	public double getYears() {
		return years;
	}
	public void setYears(double years) {
		this.years = years;
	}
	public double getAnswerDub() {
		return answerDub;
	}
	public void setAnswerDub(double answerDub) {
		this.answerDub = answerDub;
	}
	public double getTempD() {
		return tempD;
	}
	public void setTempD(double tempD) {
		this.tempD = tempD;
	}
	public String getAnswerStr() {
		return answerStr;
	}
	public void setAnswerStr(String answerStr) {
		this.answerStr = answerStr;
	}
	public String getAmountStr() {
		return amountStr;
	}
	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}
	public String getDaysStr() {
		return daysStr;
	}
	public void setDaysStr(String daysStr) {
		this.daysStr = daysStr;
	}
	public String getMonthsStr() {
		return monthsStr;
	}
	public void setMonthsStr(String monthsStr) {
		this.monthsStr = monthsStr;
	}
	public String getYearsStr() {
		return yearsStr;
	}
	public void setYearsStr(String yearsStr) {
		this.yearsStr = yearsStr;
	}
	public boolean isNumbersVerified() {
		return numbersVerified;
	}
	public void setNumbersVerified(boolean numbersVerified) {
		this.numbersVerified = numbersVerified;
	}
	
}
