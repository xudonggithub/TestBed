
	package com.example.app;

	import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.testbed.ListTest;
import com.example.testbed.R;
import com.example.testbed.TestByteBuffer;
import com.example.testbed.TextUiFragment;

	public class MainActivity extends Activity
	{
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			 super.onCreate(savedInstanceState);
		     setContentView(R.layout.activity_main);
		     TestByteBuffer.testByteBuffer();
//		     ListTest.testLinkedList();
		     
//		     addFragment();
		}
		
		public void addFragment() {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.add(R.id.replaced_fragment, new TextUiFragment());
			ft.commit();
		}
	}
