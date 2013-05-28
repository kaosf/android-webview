package net.kaosfield.wv1;

import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.JsResult;
import android.content.Intent;
import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends Activity {

	private WebView webView = null;

	private ValueCallback<Uri> uploadMessage;

	private final static int FILECHOOSER_RESULTCODE = 1;

	private static String registrationId = "";

	public static void setRegistrationId(String regId) {
		if (!regId.equals("")) {
			registrationId = regId;
		}
		else {
			Log.d("wv1", "in setRegistrationId; regId is empty.");
		}
	}

	private static boolean registrationIdIsRegistered() {
		return !registrationId.equals("");
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == FILECHOOSER_RESULTCODE) {
			if (uploadMessage == null) return;
			Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
			uploadMessage.onReceiveValue(result);
			uploadMessage = null;
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		setRegistrationId(GCMRegistrar.getRegistrationId(this));
		if (!registrationIdIsRegistered()) {
			GCMRegistrar.register(this, "123456789012"); // replace yours
		} else {
			Log.v("wv1", "Already registered");
		}

		webView = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("net.kaosfield.wv1:")) {
					Log.d("wv1", url);
					return true;
				}
				return false;
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				Log.d("wv1", "onPageFinished");
				String argument = "d.e.f";
				view.loadUrl("javascript:alert(window.method(\"" + argument + "\"))");
			}
		});
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
				if (message.equals("net.kaosfield.wv1")) {
					try {
						Log.d("wv1", "url: " + url + ", message: " + message);
						return true;
					} finally {
						result.confirm(); // in order not to alert
					}
				}
				else {
					return false;
				}
			}
			// For Android 3.0+
			@SuppressWarnings("unused")
			public void openFileChooser(ValueCallback<Uri> uploadMsg) {
				uploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("image/*");
				MainActivity.this.startActivityForResult(Intent.createChooser(i,"File Chooser"), FILECHOOSER_RESULTCODE);
			}
			// For Android 3.0+
			@SuppressWarnings("unused")
			public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
				uploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				MainActivity.this.startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
			}
			// For Android 4.1
			@SuppressWarnings("unused")
			public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
				uploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("image/*");
				MainActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), MainActivity.FILECHOOSER_RESULTCODE);
			}
		});
		webView.loadUrl("http://google.com");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Check if the key event was the Back button and if there's history
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		// If it wasn't the Back key or there's no web page history, bubble up to the default
		// system behavior (probably exit the activity)
		return super.onKeyDown(keyCode, event);
	}

}
