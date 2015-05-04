package com.example.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.*;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity extends ActionBarActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                String type = message.substring(0, message.indexOf("/"));
                String body = message.substring(message.indexOf("/") + 1);

                if (type.equals("Toast")) {
                    try {
                        Toast.makeText(MainActivity.this, body, Toast.LENGTH_SHORT).show();
                        return true;
                    } finally {
                        result.confirm();
                    }
                }

                else if(type.equals("ConfirmDialog")) {
                    try {
                        String[] params = parseForDialog(3, body);

                        if(params == null)
                            return true;

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle(params[0]);
                        builder.setPositiveButton(params[1], null);
                        builder.setMessage(params[2]);

                        AlertDialog alert = builder.create();
                        alert.show();

                        return true;
                    } finally {
                        result.confirm();
                    }
                }

                else if(type.equals("2SelectionsDialog")) {
                    try {
                        String[] params = parseForDialog(4, body);

                        if(params == null)
                            return true;

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle(params[0]);
                        builder.setPositiveButton(params[1], null);
                        builder.setNegativeButton(params[2], null);
                        builder.setMessage(params[3]);

                        AlertDialog alert = builder.create();
                        alert.show();

                        return true;
                    } finally {
                        result.confirm();
                    }
                }

                else if(type.equals("3SelectionsDialog")) {
                    try {
                        String[] params = parseForDialog(5, body);

                        if(params == null)
                            return true;

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle(params[0]);
                        builder.setPositiveButton(params[1], null);
                        builder.setNeutralButton(params[2], null);
                        builder.setNegativeButton(params[3], null);
                        builder.setMessage(params[4]);

                        AlertDialog alert = builder.create();
                        alert.show();

                        return true;
                    } finally {
                        result.confirm();
                    }
                }

                else
                    return false;
            }
        });

        webView.loadUrl("file:///android_asset/index.html");
        //webView.addJavascriptInterface(new JavaScriptInterface(this), "Android");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String[] parseForDialog(int parseNum, String body) {
        String[] params = new String[parseNum];

        for(int i=0; i<parseNum - 1; i++) {
            int index = body.indexOf("/");
            if(index == -1)
                return null;

            params[i] = body.substring(0, index);
            body = body.substring(index + 1);

            if(params[i].equals(""))
                return null;
        }

        params[parseNum - 1] = body.substring(body.indexOf("/") + 1);

        return params;
    }
}
