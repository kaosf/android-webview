package net.kaosfield.wv1;

import android.webkit.WebView;
import android.webkit.WebViewClient;

class MyWebViewClient extends WebViewClient {

	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		return false;
	}
}
