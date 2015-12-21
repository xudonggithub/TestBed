package com.example.testbed.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.testbed.R;

public class CTestActivity extends Activity implements OnClickListener{
	private String tag = getClass().getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atest);
		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.button2).setOnClickListener(this);
		findViewById(R.id.button3).setOnClickListener(this);
			Log.i(tag, "-->CTestActivity created");
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
		{
			Intent intent = new Intent();
			intent.setClass(this, ATestActivity.class);
			startActivity(intent);
		}
			break;
		case R.id.button2:
		{
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			intent.setClass(this, ATestActivity.class);
			startActivity(intent);
		}
			break;
		case R.id.button3:
		{
			
			//use command :adb shell dumpsys activity activities 
			//to see activities in the task
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(this, ATestActivity.class);
			startActivity(intent);
		}
				break;
		default:
			break;
		}
		
	}
	
	
}
