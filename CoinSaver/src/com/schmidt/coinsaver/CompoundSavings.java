package com.schmidt.coinsaver;

import android.app.Activity;
import android.os.Bundle;
//for testing: 
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.ArrayList;

public class CompoundSavings extends Activity implements OnItemSelectedListener {

	// for testing: 
	private static final String TAG = "Compound Savings";
	private Button bCalc;
	private EditText etPrincipal;
	private EditText etInterest;
	private EditText etYears;
	private EditText etTotal;
	private EditText etAddedAmount;
	private Spinner spinner;
	private int compound;
	private double principal;
	private double rate;
	private double years;
	private double answerDub;
	private double tempD;
	private double addition;
	private boolean numbersVerified;
	private boolean moneyAdded;
	private String principalStr;
	private String interestStr;
	private String yearsStr;
	private String answerStr;
	private String additionStr;
	private ArrayList<Double> yearlyTotals;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.compound_savings);
		initialize();

		// full screen
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		bCalc.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// for testing: 
				Log.i(TAG, "Start Calculation onClick");
				calculate();
			}
		});
	}

	/*************************************************
	 * Initialize() 
	 * create a starting point for all variables
	 ************************************************/
	private void initialize() {
		// for testing: 
		Log.i(TAG, "initialize()");
		try {
			bCalc = (Button) findViewById(R.id.bCalculate);
			etPrincipal = (EditText) findViewById(R.id.etPrinc);
			etInterest = (EditText) findViewById(R.id.etRate);
			etYears = (EditText) findViewById(R.id.etYears);
			etTotal = (EditText) findViewById(R.id.etTotal);
			etAddedAmount = (EditText) findViewById(R.id.etAddedAmount);
			yearlyTotals = new ArrayList<Double>();			
		} catch (Exception e1) {
			// for testing: 
			Log.i(TAG, "findViewById...");
		}
		// Default setting for variables
		// Months and years are preset
		principal = 0.0;
		rate = 0.0;
		years = 0.0;
		compound = 0;
		answerDub = 0.0;
		tempD = 0.0;
		addition = 0.0;
		
		principalStr = "null";
		interestStr = "null";
		yearsStr = "null";
		answerStr = "null";
		
		numbersVerified = false;
		moneyAdded = false;
		
		spinner = (Spinner) findViewById(R.id.sCompound);
		spinner.setOnItemSelectedListener(CompoundSavings.this);
		
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
	public void calculate() {
		// for testing: 
		Log.i(TAG, "calculate()");
		numbersVerified = false;
		principalStr = etPrincipal.getText().toString();
		interestStr = etInterest.getText().toString();
		yearsStr = etYears.getText().toString();
		additionStr = etAddedAmount.getText().toString();
		
		// Ensure that the numbers as text equate to real numbers
		principal = parseText(principalStr);
		rate = parseText(interestStr);
		years = parseText(yearsStr);
		addition = parseText(additionStr);
		
		// Ensure that the numbers are appropriate to this activity
		verifyNumbers();
		
		// check for additional monthly contributions and select equation
		if (addition > 0.0)
			moneyAdded = true;
		else
			moneyAdded = false;
		
		// set initial principal as beginning of totals array
		yearlyTotals.clear();
		yearlyTotals.add(principal);
		
		// calculate without monthly contribution
		// A = P(1+r/n)^nt
		if (numbersVerified && !moneyAdded) {
			Log.i(TAG, "calculate w/o money added");
			
			// calculate accurate interest rate
			rate = rate/100;
			
			for(int i = 0; i < years; i++){
				Log.i(TAG, "iteration: " + i + ", yearlyTotals size: " + yearlyTotals.size());
				yearlyTotals.add(calculateWithOutAdditions(yearlyTotals.get(yearlyTotals.size() - 1)));
			}
		}
		
		if (numbersVerified && moneyAdded) {
			Log.i(TAG, "calculate w/ money added");
			
			// calculate accurate interest rate
			rate = rate/100/compound;
			
			for(int i = 0; i < years; i++){
				Log.i(TAG, "iteration: " + i + ", yearlyTotals size: " + yearlyTotals.size());
				yearlyTotals.add(calculateWithAdditions(yearlyTotals.get(yearlyTotals.size() - 1)));
			}
		}
		
		try {
			answerDub = yearlyTotals.get(yearlyTotals.size() - 1);
		} catch (Exception e) {
			etTotal.setText("ERROR! OUT of BOUNDS");
		}
		// format answer string and display
		try {
			answerStr = String.format("$ %.2f", answerDub);
		} catch (Exception e) {
			etTotal.setText("ERROR! No answer format");
		}
		etTotal.setText(answerStr);
	}

	/*************************************************
	 * calculateWithAdditions()
	 * calculates the interest with monthly additional contributions
	 * Formula-
	 * Total = [ ((1 + i)^n)*PV ] + [ PMT*(((1 + i)^n - 1)/i) ]
	 * where n = number of periods / PV = principle / PMT = periodic payment amount
	 ************************************************/
	private Double calculateWithAdditions(double principal) {
		// for testing: 
		Log.i(TAG, "calculateWithAdditions()");
		
		//answerDub = addition * ((Math.pow( (1+rate/compound), (compound * years -1)) / (rate / compound)) + principal * Math.pow( (1 + (rate / compound)),
		//(compound * years)));
		Log.i(TAG, "((Math.pow(1 +" + rate + ","  + compound + " ))*" + principal + " ) + ( " + addition + "*((Math.pow((1 + " +rate +")," +compound + ") - 1)/" +rate +" ))");
		answerDub =((Math.pow(1 + rate, compound))*principal ) + ( addition*((Math.pow((1 + rate),compound) - 1)/rate) );
		return answerDub;
		
	}
	
	/*************************************************
	 * calculateWithOutAdditions() 
	 * calculates the interest without the additional
	 * contributions
	 * Formula-
	 * A = P(1+r/n)^nt
	 ************************************************/
	private Double calculateWithOutAdditions(double initial) {
		// for testing: 
		Log.i(TAG, "calculateWithOutAdditions()");
		
		answerDub = principal * Math.pow( (1 + (rate / compound)),
				(compound * years));
		return answerDub;
		
	}
	
	/*************************************************
	 * parseText() 
	 * performs required check to transfer string 
	 * data into number form
	 ************************************************/
	private Double parseText(String str) {
		// for testing: 
		Log.i(TAG, "parseText()");
		try {
			tempD = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			etTotal.setText("ERROR! " + str + " not a number");
		}
		return tempD;
	}

	/*************************************************
	 * verifyNumbers() 
	 * performs required check to ensure numbers are appropriate
	 ************************************************/
	private void verifyNumbers() {
		// for testing: 
		Log.i(TAG, "verifyNumbers()");
		Log.i(TAG, "principal"+principal+"rate"+ rate+ "compound"+ compound+ "addition"+ addition+ "years"+ years);
		if (principal <= 0 || rate <= 0 || compound <= 0 || addition < 0 || years <= 0) {
			etTotal.setText("No numbers <= 0");
		} else {
			numbersVerified = true;
		}
		
		if (principalStr == "" || interestStr == ""  || yearsStr == ""  || additionStr == "" )
			etTotal.setText("Please fill out all boxes");
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
		// for testing: 
		Log.i(TAG, "On Start .....");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// for testing: 
		Log.i(TAG, "On Restart .....");
	}

	@Override
	protected void onResume() {
		super.onResume();
		// for testing: 
		Log.i(TAG, "On Resume .....");
	}

	@Override
	protected void onPause() {
		super.onPause();
		// for testing: 
		Log.i(TAG, "On Pause .....");
	}

	@Override
	protected void onStop() {
		super.onStop();
		// for testing: 
		Log.i(TAG, "On Stop .....");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// for testing: 
		Log.i(TAG, "On Destroy .....");
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		// for testing: 
		Log.i(TAG, "On Back Pressed .....");
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

	public EditText getEtPrincipal() {
		return etPrincipal;
	}

	public void setEtPrincipal(EditText etPrincipal) {
		this.etPrincipal = etPrincipal;
	}

	public EditText getEtInterest() {
		return etInterest;
	}

	public void setEtInterest(EditText etInterest) {
		this.etInterest = etInterest;
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

	public double getPrincipal() {
		return principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
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

	public boolean isNumbersVerified() {
		return numbersVerified;
	}

	public void setNumbersVerified(boolean numbersVerified) {
		this.numbersVerified = numbersVerified;
	}

	public String getPrincipalStr() {
		return principalStr;
	}

	public void setPrincipalStr(String principalStr) {
		this.principalStr = principalStr;
	}

	public String getInterestStr() {
		return interestStr;
	}

	public void setInterestStr(String interestStr) {
		this.interestStr = interestStr;
	}

	public String getYearsStr() {
		return yearsStr;
	}

	public void setYearsStr(String yearsStr) {
		this.yearsStr = yearsStr;
	}

	public String getAnswerStr() {
		return answerStr;
	}

	public void setAnswerStr(String answerStr) {
		this.answerStr = answerStr;
	}

	public EditText getEtAddedAmount() {
		return etAddedAmount;
	}
	
	
}