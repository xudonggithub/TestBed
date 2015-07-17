package com.example.testbed;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
		startRunTimer();
	}

	boolean stop = false;
	@Override
	protected void onPause() {
		stop = true;
		super.onPause();
	}
	private void startRunTimer() {
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				while(!stop) {
//					
//					 
//					 MainActivity.this.runOnUiThread(new Runnable() {
//						
//						@Override
//						public void run() {
//							 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss:SSSZ");
//							 Date date = new Date(System.currentTimeMillis());
//							 System.out.println("cxd,:"+formatter.format(date));
//							 text.setText(formatter.format(date));
//							 text.invalidate();
//						}
//					});
//					}
//			}
//		}).start();
		
		new AsyncTask<String, String, String>() {

			@Override
			protected String doInBackground(String... params) {
				while(!stop) {
//				 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss:SSSZ");
//				 Date date = new Date(System.currentTimeMillis());
				 String time = String.valueOf(System.currentTimeMillis());//formatter.format(date);
				 System.out.println("cxd,:"+time);
				 publishProgress(time);
				}
				return null;
			}
			
			@Override
			protected void onProgressUpdate(String... values) {
				 text.setText(values[0]);
				 text.invalidate();
				super.onProgressUpdate(values);
			}
			
		}.execute(new String[]{null});
		
	}
	
	
	
	private void stopRunTimer() {
		stop = true;
	}
	TextView text ;
	TextView longText;
	@SuppressLint("NewApi")
	private void initUI() {
		RelativeLayout main = (RelativeLayout)findViewById(R.id.main_layout);
		//test canvas
		Button bt = (Button)findViewById(R.id.button1);
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				TestCanvas.testRotateImage();
				stopRunTimer();
			}
		});
		
		text = (TextView)findViewById(R.id.textview1);
		longText = (TextView)findViewById(R.id.longText);
		//test canvase
		/*final Paint paint = new Paint();
		paint.setColor(Color.RED);
		main.addView(TestCanvas.getCustomView(this));*/
		
	/*	// test Fragment
		FragmentTransaction  ft = getFragmentManager().beginTransaction();
		ft.add(R.id.fragment, new TestFragment());
		ft.add(R.id.fragment, new TestFragment2());
		ft.commit();
		
		Button bt = (Button)findViewById(R.id.button1);
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction  ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.fragment, new TestFragment3());
				ft.commit();
			}
		});
		Button bt2 = (Button)findViewById(R.id.button2);
		bt2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction  ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.fragment, new TestFragment4());
				ft.commit();
			}
		});
		//test Fragment end
	 */
		
	}
	
	@Override
	protected void onResume() {
		TestThreadLocal ttl = new TestThreadLocal();
		ttl.init();
		super.onResume();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
