package com.md.library.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.md.library.views.RippleView;
import com.md.library.widgets.SimpleDialog;
import com.md.library.widgets.CustomProgressDialog;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final Context context = this;
		RippleView btn = (RippleView)findViewById(R.id.show_dialog);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam";
				SimpleDialog dialog = new SimpleDialog(MainActivity.this, "Title", desc);
				dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						CustomProgressDialog.show(context,"hello loading...");
					}
					
				});
				
				
				dialog.setOnCancelButtonClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
					}
				});
				dialog.show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
