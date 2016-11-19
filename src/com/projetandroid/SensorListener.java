package com.projetandroid;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class SensorListener implements SensorEventListener{
	//très simple listener de l'accelerometre

	static SensorListener instance;
	GameScene scene;
	
	public static SensorListener getSharedInstance() {
	    if (instance == null)
	        instance = new SensorListener();
	    return instance;
	}
	 
	public SensorListener() {
	    instance = this;
	    scene = (GameScene) BaseActivity.getSharedInstance().mCurrentScene;
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
		    switch (event.sensor.getType()) {
		        case Sensor.TYPE_ACCELEROMETER:
		        	if (Utils.isTablet(null))
		        	{
		        		scene.accelerometerSpeedX = - event.values[0];
		        		scene.accelerometerSpeedY = event.values[1];
		        	}
		        	else
		        	{
		        		scene.accelerometerSpeedX = event.values[1];
		        		scene.accelerometerSpeedY = event.values[0];		        		
		        	}
		            break;
		        default:
		            break;
		    }
		}
		
	}

}
