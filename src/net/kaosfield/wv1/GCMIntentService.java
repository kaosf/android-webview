package net.kaosfield.wv1;

import com.google.android.gcm.GCMBaseIntentService;

import android.content.Context;
import android.content.Intent;

import android.util.Log;

public class GCMIntentService extends GCMBaseIntentService {

	@Override
	public void onRegistered(Context context, String regId) {
		Log.d("wv1", "on registered, regId: " + regId);
		MainActivity.setRegistrationId(regId);
	}

	@Override
	public void onUnregistered(Context context, String regId) {
		Log.d("wv1", "on unregistered, regId: " + regId);
	}

	@Override
	public void onMessage(Context context, Intent intent) {
		Log.d("wv1", "on message");
	}

	@Override
	public void onError(Context context, String errorId) {
		Log.d("wv1", "on error, :errorId: " + errorId);
	}

	@Override
	public boolean onRecoverableError(Context context, String errorId) {
		Log.d("wv1", "on recoverable error, errorId: " + errorId);
		return false;
	}

}
