package com.example.testbed;

import com.example.app.MainActivity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TextUiFragment extends Fragment{
	private View mainView;
	private ListView listView ;
	private TextView headerView;
	private int paddingTop;
	private Activity activity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainView = inflater.inflate(R.layout.text_ui_fragment, container, false);
		 initUI();
		return mainView;
	}

	@Override
	public void onAttach(Activity activity) {
		this.activity = activity;
		super.onAttach(activity);
	}
	
	private void initUI() {
		listView = (ListView)mainView.findViewById(R.id.listview);
		BaseAdapter adapter = new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView textView = new TextView(activity);
				textView.setTextSize(20);
				textView.setText("item item item itemitem item item item");
				return textView;
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 10;
			}
		};
		
		headerView = new TextView(activity);
		headerView.setText("header header");
		listView.addHeaderView(headerView);
		listView.setAdapter(adapter);
		Button btnButton = (Button) mainView.findViewById(R.id.button1);
		btnButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				paddingTop += 50;
				headerView.setPadding(0, paddingTop, 0, 0);
				
			}
		});
		
	}
}
