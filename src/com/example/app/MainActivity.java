
	package com.example.app;

	import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.example.testbed.ArrayRotate;
import com.example.testbed.BitShift;
import com.example.testbed.ListTest;
import com.example.testbed.MultiThreadTestbed;
import com.example.testbed.MyView;
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
			RelativeLayout layout = (RelativeLayout) findViewById(R.id.fragment);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(300, 200);
			lp.addRule(RelativeLayout.CENTER_IN_PARENT);
			layout.addView(new MyView(this), lp);
			
		}
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button1:
				new MultiThreadTestbed().Test();
				break;

			default:
				break;
			}
			
		}
	}
