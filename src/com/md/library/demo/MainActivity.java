package com.md.library.demo;

import com.md.library.R;
import com.md.library.utils.Utility;

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
				Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
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
