package com.projetandroid;

import android.content.Context;
import android.content.res.Configuration;

public class Utils {

	static BaseActivity activity;
	
	
	public static boolean isTablet(Context context) {
		//permet de savoir si le device est une tablette ou un télephone
		// il y a surement une fonction déjà faites pour ca mais je ne la trouve pas
		if (context == null)
		{
			activity = BaseActivity.getSharedInstance();
			context = activity.getApplicationContext(); 
		}
	    return (context.getResources().getConfiguration().screenLayout
	            & Configuration.SCREENLAYOUT_SIZE_MASK)
	            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
}
