package com.paypal.android.simpledemo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;
import android.view.View.OnClickListener;

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalAdvancedPayment;
import com.paypal.android.MEP.PayPalInvoiceData;
import com.paypal.android.MEP.PayPalInvoiceItem;
import com.paypal.android.MEP.PayPalPayment;
import com.paypal.android.MEP.PayPalPreapproval;
import com.paypal.android.MEP.PayPalReceiverDetails;

public class SimpleDemo extends Activity implements OnClickListener {

	// The PayPal server to be used - can also be ENV_NONE and ENV_LIVE
	private static final int server = PayPal.ENV_SANDBOX;
	// The ID of your application that you received from PayPal
	private static final String appID = "APP-80W284485P519543T";
	// This is passed in for the startActivityForResult() android function, the
	// value used is up to you
	private static final int request = 1;

	public static final String build = "10.12.09.8053";

	protected static final int INITIALIZE_SUCCESS = 0;
	protected static final int INITIALIZE_FAILURE = 1;

	// Native android items for the application
	TextView labelSimplePayment;
	TextView labelKey;
	TextView appVersion;
	Button exitApp;
	TextView title;
	TextView info;
	TextView extra;
	LinearLayout layoutSimplePayment;
	private ScrollView scroller;

	// You will need at least one CheckoutButton, this application has four for
	// examples
	ImageButton launchSimplePayment;
	// Button launchPreapproval;
	// private boolean isPreapproval;

	private String TAG;

	// These are used to display the results of the transaction
	public static String resultTitle;
	public static String resultInfo;
	public static String resultExtra;

	// This handler will allow us to properly update the UI. You cannot touch
	// Views from a non-UI thread.

	View v;
	Handler hRefresh = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case INITIALIZE_SUCCESS:
				onClick(v);
				break;
			case INITIALIZE_FAILURE:
				showFailure();
				break;
			}
		}
	};
	private int requestCode;
	private int resultCode;
	private Intent data;
	private boolean mEditing = false;
	private static EditText text;

	public void showFailure() {
		/*
		 * title.setText("FAILURE");
		 * info.setText("Could not initialize the PayPal library.");
		 * title.setVisibility(View.VISIBLE); info.setVisibility(View.VISIBLE);
		 */
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Initialize the library. We'll do it in a separate thread because it
		// requires communication with the server
		// which may take some time depending on the connection strength/speed.
		Thread libraryInitializationThread = new Thread() {
			public void run() {
				initLibrary();

				// The library is initialized so let's create our CheckoutButton
				// and update the UI.
				if (PayPal.getInstance().isLibraryInitialized()) {
					hRefresh.sendEmptyMessage(INITIALIZE_SUCCESS);
				} else {
					hRefresh.sendEmptyMessage(INITIALIZE_FAILURE);
				}
			}
		};
		libraryInitializationThread.start();

		// LinearLayout layoutSimplePayment;

	

		launchSimplePayment = (ImageButton) findViewById(R.id.btnDon);
		exitApp = (Button) findViewById(R.id.btn_exit);
		// small=(CheckBox) findViewById(R.id.small);

		// launchSimplePayment.setOnClickListener((OnClickListener) this);
		 exitApp.setOnClickListener(this);
		launchSimplePayment.setOnClickListener(this);
		

	}

	public void onClick(View v) {
		labelSimplePayment = (TextView) findViewById(R.id.lbl_simplePayment);
		RadioButton medium = (RadioButton) findViewById(R.id.medium);
		RadioButton small = (RadioButton) findViewById(R.id.small);
		RadioButton large = (RadioButton) findViewById(R.id.large);
		//RadioButton other = (RadioButton) findViewById(R.id.other);
		text = (EditText) findViewById(R.id.editText);
		//TextView tv = (TextView) findViewById(R.id.other);
		PayPalPayment payment = exampleSimplePayment();
		 
		if (v == launchSimplePayment) {

			String enteredValue = text.getText().toString();
			
			if (small.isChecked()) {
				enteredValue = "1.00";
			}
			 if (medium.isChecked()) {
				enteredValue = "5.00";
			}
			 if (large.isChecked()) {
				enteredValue = "10.00";
			}
			
			Log.v("log", "new value = " + enteredValue);
			payment.setSubtotal(new BigDecimal(enteredValue));
			
			Intent checkoutIntent = PayPal.getInstance().checkout(payment,
					com.paypal.android.simpledemo.SimpleDemo.this, new ResultDelegate());
			startActivityForResult(checkoutIntent, request);
	
			 onActivityResult(requestCode, resultCode, data);		
		}
		else if(v==exitApp){
				//System.exit(0);
				finish();
			}
		
	}
	
	/**
	 * The initLibrary function takes care of all the basic Library
	 * initialization.
	 * 
	 * @return The return will be true if the initialization was successful and
	 *         false if
	 */
	private void initLibrary() {
		PayPal pp = PayPal.getInstance();
		// If the library is already initialized, then we don't need to
		// initialize it again.
		if (pp == null) {
			// This is the main initialization call that takes in your Context,
			// the Application ID, and the server you would like to connect to.
			pp = PayPal.initWithAppID(this, appID, server);

			// -- These are required settings.
			pp.setLanguage("en_US"); // Sets the language for the library.
			// --

			// -- These are a few of the optional settings.
			// Sets the fees payer. If there are fees for the transaction, this
			// person will pay for them. Possible values are FEEPAYER_SENDER,
			// FEEPAYER_PRIMARYRECEIVER, FEEPAYER_EACHRECEIVER, and
			// FEEPAYER_SECONDARYONLY.
			pp.setFeesPayer(PayPal.FEEPAYER_EACHRECEIVER);
			// Set to true if the transaction will require shipping.
			pp.setShippingEnabled(true);
			// Dynamic Amount Calculation allows you to set tax and shipping
			// amounts based on the user's shipping address. Shipping must be
			// enabled for Dynamic Amount Calculation. This also requires you to
			// create a class that implements PaymentAdjuster and Serializable.
			pp.setDynamicAmountCalculationEnabled(false);
			// --
			
		
		}
		//onActivityResult( requestCode,  resultCode, data);
		exampleSimplePayment() ;
	}

	/**
	 * Create a PayPalPayment which is used for simple payments.
	 * 
	 * @return Returns a PayPalPayment.
	 */
	private  PayPalPayment exampleSimplePayment() {

		// Create a basic PayPalPayment.
		PayPalPayment payment = new PayPalPayment();
		// Sets the currency type for this payment.
		payment.setCurrencyType("USD");
		// Sets the recipient for the payment. This can also be a phone number.
		//String phoneoremail="supurg_1350912876_biz@gmail.com";
		
Bundle bundle = getIntent().getExtras();

		
		String email = bundle.getString("email");
		
		payment.setRecipient(email);
		// Sets the amount of the payment, not including tax and shipping
		// amounts.

		// payment.setSubtotal(new BigDecimal("1.25"));

		// Sets the payment type. This can be PAYMENT_TYPE_GOODS,
		// PAYMENT_TYPE_SERVICE, PAYMENT_TYPE_PERSONAL, or PAYMENT_TYPE_NONE.
		payment.setPaymentType(PayPal.PAYMENT_TYPE_SERVICE);

		// PayPalInvoiceData can contain tax and shipping amounts. It also
		// contains an ArrayList of PayPalInvoiceItem which can
		// be filled out. These are not required for any transaction.
		PayPalInvoiceData invoice = new PayPalInvoiceData();
		// Sets the tax amount.
		// invoice.setTax(new BigDecimal("0.25"));
		// Sets the shipping amount.
		// invoice.setShipping(new BigDecimal("4.50"));

		// PayPalInvoiceItem has several parameters available to it. None of
		// these parameters is required.

		// Sets the PayPalPayment invoice data.
		payment.setInvoiceData(invoice);
		// Sets the merchant name. This is the name of your Application or
		// Company.
		String merchantName="Yasemin Yarnell's Test Store";
		payment.setMerchantName(merchantName);
		// Sets the description of the payment.
		payment.setDescription("Quite a simple payment");
		// Sets the Custom ID. This is any ID that you would like to have
		// associated with the payment.
		payment.setCustomID("8873482296");
		// Sets the Instant Payment Notification url. This url will be hit by
		// the PayPal server upon completion of the payment.
		payment.setIpnUrl("https://www.sandbox.paypal.com/cgi-bin/webscr");
		// Sets the memo. This memo will be part of the notification sent by
		// PayPal to the necessary parties.
		payment.setMemo("Hi! I'm making a memo for a simple payment.");
	
		
		return payment;

	}

	/**
	 * If you choose not to implement the PayPalResultDelegate, then you will
	 * receive the transaction results here. Below is a section of code that is
	 * commented out. This is an example of how to get result information for
	 * the transaction. The resultCode will tell you how the transaction ended
	 * and other information can be pulled from the Intent using getStringExtra.
	 */

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		setContentView(R.layout.message);
	
		// exitApp = (Button) findViewById(R.id.btn_exit);

		info = (TextView) findViewById(R.id.info);
		//extra = (TextView) findViewById(R.id.extra);		
		
		info.setText(resultInfo);
		info.setVisibility(View.VISIBLE);		
	/*	
		extra.setText(resultExtra);
		extra.setVisibility(View.VISIBLE);*/

		if (requestCode != request)
			return;

	}

	
	
}
