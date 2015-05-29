package com.example.testbed;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
	}

	@SuppressLint("NewApi")
	private void initUI() {
		RelativeLayout main = (RelativeLayout)findViewById(R.id.main_layout);
		//test canvas
		Button bt = (Button)findViewById(R.id.button1);
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TestCanvas.testRotateImage();
			}
		});
		
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
