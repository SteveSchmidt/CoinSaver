package com.schmidt.coinsaver;

import android.app.Activity;
import android.os.Bundle;
//for testing: import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class TargetSavings extends Activity implements OnItemSelectedListener {
	
	// for testing: private static final String TAG = "Target Savings";
	private Button bCalc;
	private EditText etGoal;
	private EditText etRate;
	private EditText etStart;
	private EditText etTime;
	private Spinner spinner;
	private int compound;
	private double goal;
	private double rate;
	private double start;
	private double answerDub;
	private double tempAmount;
	private double tempRate;
	private double tempD;
	private double logAmount;
	private double logRate;
	private boolean numbersVerified;
	private String goalStr;
	private String rateStr;
	private String startStr;
	private String answerStr;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.target_savings);
		initialize();
		
		// full screen
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		bCalc.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// for testing: Log.i(TAG, "Start Calculation onClick");
				calculate();
			}		
		});
	}

	/*************************************************
	 * Initialize() 
	 * create a starting point for all variables
	 ************************************************/
	private void initialize() {
		// for testing: Log.i(TAG, "initialize()");
		try {
			bCalc = (Button) findViewById(R.id.bCalculate);
			etGoal = (EditText) findViewById(R.id.etGoal);
			etRate = (EditText) findViewById(R.id.etRate);
			etStart = (EditText) findViewById(R.id.etStart);
			etTime = (EditText) findViewById(R.id.etTime);
			
		} catch (Exception e1) {
			// for testing: Log.i(TAG, "findViewById...");
		}
		
		// Default setting for variables
		// Months and years are preset
		goal = 0.0;
		rate = 0.0;
		start = 0.0;
		compound = 0;
		answerDub = 0.0;
		
		tempAmount = 0.0;
		tempRate = 0.0;
		logAmount = 0.0;
		logRate = 0.0;
		tempD = 0.0;
		
		goalStr = "null";
		rateStr = "null";
		startStr = "null";
		answerStr = "null";
		numbersVerified = false;
		
		spinner = (Spinner) findViewById(R.id.sCompound);
		spinner.setOnItemSelectedListener(TargetSavings.this);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.times_compounded, android.R.layout.simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setSelection(2);
	}

	/*************************************************
	 * calculate() 
	 * performs required calculation after calling
	 * check methods
	 ************************************************/
	private void calculate() {
		// for testing: Log.i(TAG, "calculate()");
		numbersVerified = false;
		goalStr = etGoal.getText().toString();
		rateStr = etRate.getText().toString();
		startStr = etStart.getText().toString();
		
		// Ensure that the numbers as text equate to real numbers
		goal = parseText(goalStr);
		rate = parseText(rateStr);
		start = parseText(startStr);
		
		rate = rate/100;
		
		verifyNumbers();
		// the compound interest formula is:
		// FV = PV(1+i/n)^(n*t)
		// To solve for T:
		// divide both side by PV
		// FV/PV = (1+i/n)^(n*t)
		// take the natural log of both sides
		// ln(FV/PV) = ln(1+i/n^t)
		// move t out of as a coefficient
		// ln(FV/PV) = t * ln(1+i/n)
		// t = ln(FV/PV)/ln(1+i/n)
		if(numbersVerified){
			// for testing: 
			// Log.i(TAG, "if() calculation");
			
			//answerDub = (1/compound) * (Math.log(goal/start)/Math.log(1+rate/compound));
			
			try {
				tempAmount = goal/start;
				tempRate = 1 + (rate/compound);
				logAmount = Math.log(tempAmount);
				logRate = Math.log(tempRate);
				
				answerDub = (logAmount / compound) / logRate;
			} catch (Exception e1) {
				etTime.setText("ERROR! No 0's");
			}
			
			try {
				answerStr = String.format(" %.2f years", answerDub);
			} catch (Exception e) {
				etTime.setText("ERROR! No answer format");
			}
			etTime.setText(answerStr);
		}
	}
	
	/*************************************************
	 * parseText() 
	 * performs required check to transfer string 
	 * data into number form
	 ************************************************/
	private Double parseText(String str) {
		// for testing: Log.i(TAG, "parseText()");
		try {
			tempD = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			etTime.setText("ERROR! " + str + " not a number");
		}
		return tempD;
	}
	
	/*************************************************
	 * verifyNumbers() 
	 * performs required check to ensure numbers are appropriate
	 ************************************************/
	private void verifyNumbers() {
		// for testing: Log.i(TAG, "verifyNumbers()");
		if (goal <= 0 || rate <= 0 || start <= 0) {
			etTime.setText("No numbers <= 0");
		} else if (goal < start) {
			etTime.setText("Goal must be < start");
		} else {
			numbersVerified = true;
		}
	}

	/*************************************************
	 * onItemSelected() 
	 * identifies selected item on spinner and sets 
	 * up the associated rate
	 ************************************************/
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		int selection = spinner.getSelectedItemPosition();
		switch (selection) {
		case 0:
			compound = 365;
			break;
		case 1:
			compound = 12;
			break;
		case 2:
			compound = 4;
			break;
		case 3:
			compound = 1;
			break;
		}
	}

	public void onNothingSelected(AdapterView<?> parent) {
		// empty
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
	public Button getbCalc() {
		return bCalc;
	}

	public void setbCalc(Button bCalc) {
		this.bCalc = bCalc;
	}

	public EditText getEtGoal() {
		return etGoal;
	}

	public void setEtGoal(EditText etGoal) {
		this.etGoal = etGoal;
	}

	public EditText getEtRate() {
		return etRate;
	}

	public void setEtRate(EditText etRate) {
		this.etRate = etRate;
	}

	public EditText getEtStart() {
		return etStart;
	}

	public void setEtStart(EditText etStart) {
		this.etStart = etStart;
	}

	public EditText getEtTime() {
		return etTime;
	}

	public void setEtTime(EditText etTime) {
		this.etTime = etTime;
	}

	public Spinner getSpinner() {
		return spinner;
	}

	public void setSpinner(Spinner spinner) {
		this.spinner = spinner;
	}

	public int getCompound() {
		return compound;
	}

	public void setCompound(int compound) {
		this.compound = compound;
	}

	public double getGoal() {
		return goal;
	}

	public void setGoal(double goal) {
		this.goal = goal;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getStart() {
		return start;
	}

	public void setStart(double start) {
		this.start = start;
	}

	public double getAnswerDub() {
		return answerDub;
	}

	public void setAnswerDub(double answerDub) {
		this.answerDub = answerDub;
	}

	public double getTempAmount() {
		return tempAmount;
	}

	public void setTempAmount(double tempAmount) {
		this.tempAmount = tempAmount;
	}

	public double getTempRate() {
		return tempRate;
	}

	public void setTempRate(double tempRate) {
		this.tempRate = tempRate;
	}

	public double getTempD() {
		return tempD;
	}

	public void setTempD(double tempD) {
		this.tempD = tempD;
	}

	public double getLogAmount() {
		return logAmount;
	}

	public void setLogAmount(double logAmount) {
		this.logAmount = logAmount;
	}

	public double getLogRate() {
		return logRate;
	}

	public void setLogRate(double logRate) {
		this.logRate = logRate;
	}

	public boolean isNumbersVerified() {
		return numbersVerified;
	}

	public void setNumbersVerified(boolean numbersVerified) {
		this.numbersVerified = numbersVerified;
	}

	public String getGoalStr() {
		return goalStr;
	}

	public void setGoalStr(String goalStr) {
		this.goalStr = goalStr;
	}

	public String getRateStr() {
		return rateStr;
	}

	public void setRateStr(String rateStr) {
		this.rateStr = rateStr;
	}

	public String getStartStr() {
		return startStr;
	}

	public void setStartStr(String startStr) {
		this.startStr = startStr;
	}

	public String getAnswerStr() {
		return answerStr;
	}

	public void setAnswerStr(String answerStr) {
		this.answerStr = answerStr;
	}
	
	
}