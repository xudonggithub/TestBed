
	package com.example.app;

	import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.testbed.ListTest;
import com.example.testbed.Permutation;
import com.example.testbed.R;
import com.example.testbed.SemaphoreTestCase;
import com.example.testbed.TestByteBuffer;
import com.example.testbed.TextUiFragment;

	public class MainActivity extends Activity implements OnClickListener
	{
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			 super.onCreate(savedInstanceState);
		     setContentView(R.layout.activity_main);
		     initUI();
//		     TestByteBuffer.testByteBuffer();
//		     ListTest.testLinkedList();
		     
//		     addFragment();
//		     SemaphoreTestCase.Test(null);
		}
		
		public void addFragment() {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.add(R.id.replaced_fragment, new TextUiFragment());
			ft.commit();
		}
		private void initUI() {
			View replayBtn = findViewById(R.id.button1);
			replayBtn.setOnClickListener(this);
		}
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button1:
				Permutation.test();
				break;

			default:
				break;
			}
			
		}
	}
