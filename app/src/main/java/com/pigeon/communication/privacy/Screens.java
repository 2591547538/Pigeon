package com.pigeon.communication.privacy;

import android.content.Context;

import android.view.Display;
import android.view.WindowManager;


public class Screens {

	@SuppressWarnings("deprecation")

	public static int getScreenHeight(Context context) {

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		return display.getHeight();

	}

	@SuppressWarnings("deprecation")


	public static int getScreenWidth(Context context) {

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		return display.getWidth();

	}





}
