package com.md.library.demo;

import com.md.library.R;
import com.md.library.utils.Utility;
import com.md.library.widgets.SimpleDialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				SimpleDialog dialog = new SimpleDialog(MainActivity.this, "Title", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam");
				dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Toast.makeText(MainActivity.this, "Accepted", Toast.LENGTH_SHORT).show();
					}
				});
				dialog.setOnCancelButtonClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Toast.makeText(MainActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
					}
				});
				dialog.show();
			}
		});
		
		(new TestA()).test();
	}
	
	
	public class Test {
		private int a = 1;
		
		public Test() {
			Utility.logD("1a is " + a);
		}
		
		public void test() {
			Utility.logD("1a is " + a);
		}
	}
	
	public class TestA extends Test {
		private int b = 1;
		
		public TestA() {
			super();
			Utility.logD("1b is " + b);
		}
		
		@Override
		public void test() {
			super.test();
			Utility.logD("2b is " + b);
		}
	}
}
