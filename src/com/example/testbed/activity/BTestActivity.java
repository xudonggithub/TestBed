package com.example.testbed.activity;

import com.example.testbed.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class BTestActivity  extends Activity implements OnClickListener{
	private String tag = getClass().getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activit_base);
		findViewById(R.id.button1).setOnClickListener(this);
		Log.i(tag, "-->BTestActivity created");
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			Intent intent = new Intent();
			intent.setClass(this, CTestActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}

}
