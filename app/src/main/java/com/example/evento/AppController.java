package com.example.evento;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

	private static RequestQueue queue;
	private static Context context;

	public static void initialize(Context context) {
		if (AppController.context == null) {
			AppController.context = context.getApplicationContext();
		}
	}

	public static RequestQueue getInstance() {
		if (context == null) {
			throw new IllegalStateException("AppController.initialize() must be called before getInstance()");
		}

		if (queue == null) {
			queue = Volley.newRequestQueue(context);
		}
		return queue;
	}
}
