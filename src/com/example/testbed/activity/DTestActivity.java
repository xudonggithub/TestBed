package com.example.testbed.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class DTestActivity extends Activity{
	private String tag = getClass().getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(tag, "-->DTestActivity created");
	}

}
