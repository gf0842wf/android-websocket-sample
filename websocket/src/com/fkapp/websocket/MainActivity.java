package com.fkapp.websocket;

import com.fkapp.websocket.R;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private final String TAG = "MainActivity";
	public static String wsUrl = "ws://ip:port/chat"; // TODO: 运行时替换ip port
	public WebSocketConnection wsC = new WebSocketConnection();

	public Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0) {
			}
		}
	};

	private void toastLog(String s) {
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}

	private void start() {
		try {
			wsC.connect(wsUrl, new WebSocketHandler() {
				@Override
				public void onOpen() {
					toastLog("Status: Connected to " + wsUrl);
					wsC.sendTextMessage("Hello, world!");
				}

				@Override
				public void onTextMessage(String payload) {
					toastLog("Got echo: " + payload);
				}

				@Override
				public void onClose(int code, String reason) {
					toastLog("Connection lost.");
				}
			});
		} catch (WebSocketException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		start();

		Button wsStart = (Button) findViewById(R.id.wsStart);
		wsStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				wsC.sendTextMessage("ooxx");
			}
		});
	}

	   @Override
	   protected void onDestroy() {
	       super.onDestroy();
	       if (wsC.isConnected()) {
	    	   wsC.disconnect();
	       }
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
